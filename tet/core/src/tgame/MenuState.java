package tgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends state{

	protected Texture background;
	protected Texture playbtn;
	
	
	public MenuState(GameStateManager gsmIn) {
		super(gsmIn);
		background = new Texture("teris.png");
		//playbtn = new Texture("playbtnimg.png");
		
	}

	@Override
	public void handleInput() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
//
			gsm.set(new MIKEMODE(gsm));
			this.dispose();//self destruct
		}
		else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			gsm.set(new playing(gsm));
			this.dispose();//self destruct
		}

		
		
		
		
	}

	@Override
	public void update(float dt) {
		this.handleInput();
		
		
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, Tetris1.WIDTH, Tetris1.HIGHT);
		if(playbtn!=null){
		sb.draw(playbtn, (Tetris1.WIDTH/2)-(playbtn.getWidth()/2), (Tetris1.HIGHT/6)-(playbtn.getHeight()/2));
		}
		sb.end();
	}

	@Override
	public void dispose() {

		background.dispose();
		//playbtn.dispose();
		
	}
	

	
	
}
