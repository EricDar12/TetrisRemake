package ca.sheridancollege.darosaer.Screens;
import ca.sheridancollege.darosaer.Logic.GameBoard;
import ca.sheridancollege.darosaer.Logic.Shapes.Blocks;
import ca.sheridancollege.darosaer.Logic.Shapes.Tetriminos;
import ca.sheridancollege.darosaer.Logic.TetriminoRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen {

    private Tetriminos currentTetrimino;
    private TetriminoRenderer tetriminoRenderer;
    private final GameBoard board;
    private SpriteBatch spriteBatch;
    private int cellWidth;
    private int cellHeight;
    private final ShapeRenderer shapeRenderer;
    private final Texture backgroundTexture;
    private final int windowWidth;
    private final int windowHeight;
    float timeSinceLastDrop;

    public GameScreen() { // No Args Const
        board = new GameBoard();
        windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
        cellWidth = windowWidth / board.getWidth();
        cellHeight = windowHeight / board.getHeight();
        shapeRenderer = new ShapeRenderer();
        currentTetrimino = new Tetriminos(Blocks.getRandomBlock());
        backgroundTexture = new Texture(Gdx.files.internal("backgroundTexture.png"));
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {

        // Begin active rendering
        spriteBatch.begin();
        tetriminoRenderer = new TetriminoRenderer(spriteBatch, board, cellWidth, cellHeight);

        // Render the background texture
        tetriminoRenderer.renderBackgroundTexture(spriteBatch, backgroundTexture);

        int startX = windowWidth / 3;
        int startY = 0;

        // Render Static Pieces
        tetriminoRenderer.renderBoard(startX, startY);

        // Render Falling Pieces
        tetriminoRenderer.renderFallingTetrimino(currentTetrimino, startX, startY);

        // Configuration of current piece
        boolean[][] configuration = currentTetrimino.getConfiguration();

        // Handle Tetrimino Falling and Placing
        timeSinceLastDrop += delta;

        // Tetrimino falls every n second.
        float dropInterval = .25f;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dropInterval *= .5f;
        }

        // Handle Tetrimino Falling
        if (timeSinceLastDrop >= dropInterval) {
            if (currentTetrimino.canMove(currentTetrimino.getConfiguration(), currentTetrimino.getX(), currentTetrimino.getY() + 1, board)) {
                currentTetrimino.fall();
            } else {
                // Place Tetrimino and check for line clears
                Tetriminos.checkAndOccupy(configuration, currentTetrimino, board);
                Tetriminos.clearTetriminos(board.getGrid());
                // Load next Tetrimino
                Tetriminos newTetriminos = new Tetriminos(Blocks.getRandomBlock());
                // Check if new Tetrimino causes game over
                if (board.isGameOver(newTetriminos, board)) {
                    board.clearBoard();
                    // Implement more Game Over logic
                } else {
                    currentTetrimino = newTetriminos;
                }
            }
            timeSinceLastDrop = 0;
        }

        // Handle Tetrimino movement
        currentTetrimino.moveRight(currentTetrimino, board);
        currentTetrimino.moveLeft(currentTetrimino, board);

        // Handle Tetrimino Rotation
        currentTetrimino.inputRotation(currentTetrimino, board);

        // End of active rendering
        spriteBatch.end();

        // Display border around play area
        tetriminoRenderer.renderBoardOutline(shapeRenderer, windowWidth, windowHeight);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {
        int maxCellWidth = width / board.getWidth();
        int maxCellHeight = height / board.getHeight();
        int cellSize = Math.min(maxCellWidth, maxCellHeight);

        cellWidth = cellSize;
        cellHeight = cellSize;

        if (tetriminoRenderer != null) {
            tetriminoRenderer.updateCellSize(cellWidth, cellHeight);
        }
    }


    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        if (spriteBatch != null) spriteBatch.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}
