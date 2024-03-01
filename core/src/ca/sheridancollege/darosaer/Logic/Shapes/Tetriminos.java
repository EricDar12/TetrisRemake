package ca.sheridancollege.darosaer.Logic.Shapes;

import ca.sheridancollege.darosaer.Logic.GameBoard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Arrays;

public class Tetriminos {

    private final Blocks block;

    private int x, y; // Control x y coordinates

    private boolean[][] configuration;

    public Tetriminos(Blocks block) {
        this.block = block;
        this.configuration = block.getBlock();
        this.x = 3; // Center board
        this.y = 0;
    }

    // Rotate Tetrimino
    public void rotate() { // Rotate 2D Matrix
        final int m = configuration.length;
        final int n = configuration[0].length;
        boolean[][] rotated = new boolean[n][m];

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                rotated[c][m - 1 - r] = configuration[r][c];
            }
        }
        configuration = rotated;
    }

    public void inputRotation(Tetriminos currentTetrimino, GameBoard board) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            int newX = currentTetrimino.getX();
            int newY = currentTetrimino.getY();
            boolean[][] simulatedConfig = currentTetrimino.simulateRotation();
            // Simulate rotation check if outside bounds
            if (currentTetrimino.canMove(simulatedConfig, newX, newY, board)) {
                currentTetrimino.rotate();
            }
        }
    }

    // Simulate rotation of Tetrimino
    public boolean[][] simulateRotation() { // Rotate 2D Matrix
        final int m = configuration.length;
        final int n = configuration[0].length;
        boolean[][] rotated = new boolean[n][m];

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                rotated[c][m - 1 - r] = configuration[r][c];
            }
        }
        return rotated;
    }

    // Clear full lines
    public static void clearTetriminos(Blocks[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            boolean fullLine = true;
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == null) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                for (int moveRow = row; moveRow > 0; moveRow--) {
                    grid[moveRow] = Arrays.copyOf(grid[moveRow - 1], grid[moveRow].length);
                }
                Arrays.fill(grid[0], null);
                row--;
            }
        }
    }

    // Collision detection
    public boolean canMove(boolean[][] config, int newX, int newY, GameBoard board) {
        for (int row = 0; row < config.length; row++) {
            for (int col = 0; col < config[row].length; col++) {
                if (config[col][row]) { // If part of Tetrimino
                    int boardX = newX + col;
                    int boardY = newY + row;
                    // Horizontal collision
                    if (boardX < 0 || boardX >= board.getWidth()) {
                        return false;
                    } // Vertical collision
                    if (boardY >= board.getHeight()) {
                        return false;
                    } // Collision with other Tetriminos
                    if (boardY >= 0 && board.isOccupied(boardX, boardY)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Integrate Tetrimino with board when placed
    public static void checkAndOccupy(boolean[][] configuration, Tetriminos currentTetrimino, GameBoard board) {
        for (int x = 0; x < configuration.length; x++) {
            for (int y = 0; y < configuration[x].length; y++) {
                if (configuration[x][y]) {
                    int boardX = currentTetrimino.getX() + x;
                    int boardY = currentTetrimino.getY() + y;
                    board.occupy(boardX, boardY, currentTetrimino.getBlock());
                }
            }
        }
    }

    // Handle right horizontal movement
    public void moveRight(Tetriminos currentTetrimino, GameBoard board) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            int newX = currentTetrimino.getX() + 1;
            int newY = currentTetrimino.getY(); // No change in Y

            if (currentTetrimino.canMove(configuration, newX, newY, board)) {
                this.x += 1;
            }
        }
    }

    // Handle left horizontal movement
    public void moveLeft(Tetriminos currentTetrimino, GameBoard board) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            int newX = currentTetrimino.getX() - 1;
            int newY = currentTetrimino.getY(); // No change in Y

            if (currentTetrimino.canMove(configuration, newX, newY, board)) {
                this.x -= 1;
            }
        }
    }

    // Instantly drop Tetrimino
    public void drop(Tetriminos currentTetrimino, GameBoard board) {
        // Figure this out
    }

    // Make piece passively fall
    public void fall() {
        this.y += 1;
    }

    public Blocks getBlock() {
        return block;
    }

    public boolean[][] getConfiguration() {
        return configuration;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
