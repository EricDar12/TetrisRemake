package ca.sheridancollege.darosaer.Logic;
import ca.sheridancollege.darosaer.Logic.Shapes.Blocks;
import ca.sheridancollege.darosaer.Logic.Shapes.Tetriminos;

import java.util.Arrays;

public class GameBoard {

    private final int height = 20;
    private final int width = 10;
    private final Blocks[][] grid;

    public GameBoard() {
        grid = new Blocks[height][width];
    }

    public boolean isOccupied(int x, int y) {
        return grid[y][x] != null;
    }

    public void occupy(int x, int y, Blocks blockType) {
        grid[y][x] = blockType;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Blocks[][] getGrid() {
        return this.grid;
    }

    // Check if pieces overlap at the top
    public boolean isGameOver(Tetriminos newTetrimino, GameBoard board) {
        boolean[][] configuration = newTetrimino.getConfiguration();
        for(int i = 0; i < configuration.length; i++) {
            for (int j = 0; j < configuration[i].length; j++) {
                if (configuration[i][j] && board.isOccupied(newTetrimino.getX() + j, newTetrimino.getY() + i)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clearBoard() {
        for (Blocks[] a : this.grid) {
            Arrays.fill(a, null);
        }
    }
}
