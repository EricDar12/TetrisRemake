package ca.sheridancollege.darosaer;

import ca.sheridancollege.darosaer.Screens.GameScreen;
import com.badlogic.gdx.Game;

public class TetrisGame extends Game {

    @Override
    public void create() {
        this.setScreen(new GameScreen());
    }

}
