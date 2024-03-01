package ca.sheridancollege.darosaer.Logic;
import ca.sheridancollege.darosaer.Logic.Shapes.Blocks;
import ca.sheridancollege.darosaer.Logic.Shapes.Tetriminos;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// Responsible for rending the board and Tetriminos
public class TetriminoRenderer {

    private final SpriteBatch spriteBatch;
    private final GameBoard board;
    private int cellWidth;
    private int cellHeight;

    public TetriminoRenderer(SpriteBatch spriteBatch, GameBoard board, int cellWidth, int cellHeight) {
        this.spriteBatch = spriteBatch;
        this.board = board;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    // Render falling pieces
    public void renderFallingTetrimino(Tetriminos tetrimino, int startX, int startY) {
        boolean[][] configuration = tetrimino.getConfiguration();
        Texture texture = tetrimino.getBlock().getTexture();
        for (int x = 0; x < configuration.length; x++) {
            for (int y = 0; y < configuration[x].length; y++) {
                if (configuration[x][y]) {
                    spriteBatch.draw(texture, startX + ((tetrimino.getX() + x) * cellWidth), startY + (board.getHeight() - (tetrimino.getY() + y) - 1) * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    }

    // Render entire board and assign correct texture
    public void renderBoard(int startX, int startY) {
        Blocks[][] grid = board.getGrid();
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Blocks blockType = grid[y][x];
                if (blockType != null) { // Directly check if the cell is occupied by a block.
                    Texture texture = blockType.getTexture();
                    spriteBatch.draw(texture, startX + (x * cellWidth), startY + (board.getHeight() - y - 1) * cellHeight, cellWidth, cellHeight);
                }
            }
        }
    }

    // Render outline of game board
    public void renderBoardOutline(ShapeRenderer shapeRenderer, int windowWidth, int windowHeight) {
        // Actual dimensions of the game board in pixels.
        int boardWidthPixels = board.getWidth() * cellWidth;
        int boardHeightPixels = board.getHeight() * cellHeight;

        int startX = windowWidth / 3;

        // Draw border around the actual game board.
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(startX, 0, boardWidthPixels, boardHeightPixels);
        shapeRenderer.end();
    }


    // Render background texture
    public void renderBackgroundTexture(SpriteBatch spriteBatch, Texture backgroundTexture) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    // public void renderDropPreview(); Figure out how to render drop preview

    public void updateCellSize(int width, int height) {
        cellWidth = width;
        cellHeight = height;
    }
}
