package tgame;

import com.badlogic.gdx.graphics.Texture;

public class gameOverMenueState extends MenuState{

	public gameOverMenueState(GameStateManager gsmIn) {
		super(gsmIn);
		
		background = new Texture("teris.png");
		playbtn = new Texture("playAgain.png");
		
		// TODO Auto-generated constructor stub
	}

}
