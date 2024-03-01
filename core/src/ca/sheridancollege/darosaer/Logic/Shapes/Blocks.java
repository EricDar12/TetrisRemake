package ca.sheridancollege.darosaer.Logic.Shapes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.Random;

public enum Blocks {

    I(new boolean[][]{
            {false, false, false, false},
            {true, true, true, true},
            {false, false, false, false},
            {false, false, false, false},
    }, "Tetriminos/I.png"),
    J(new boolean[][]{
            {true, false, false},
            {true, true, true},
            {false, false, false}
    }, "Tetriminos/J.png"),
    L(new boolean[][]{
            {false, false, true},
            {true, true, true},
            {false, false, false}
    }, "Tetriminos/L.png"),
    O(new boolean[][]{
            {true, true},
            {true, true}
    }, "Tetriminos/O.png"),
    S(new boolean[][]{
            {false, true, true},
            {true, true, false},
            {false, false, false}
    }, "Tetriminos/S.png"),
    Z(new boolean[][]{
            {true, true, false},
            {false, true, true},
            {false, false, false}
    }, "Tetriminos/Z.png"),
    T(new boolean[][]{
            {false, true, false},
            {true, true, true},
            {false, false, false}
    }, "Tetriminos/T.png");

    private final boolean[][] block;
    private final Texture texture;

    Blocks(boolean[][] block, String textureFileName) {
        this.block = block;
        this.texture = new Texture(Gdx.files.internal(textureFileName));
    }

    public boolean[][] getBlock() {
        return this.block;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public static Blocks getRandomBlock() {
        Blocks[] blocks = Blocks.values();
        Random r1 = new Random();
        return blocks[r1.nextInt(blocks.length)];
    }
}
