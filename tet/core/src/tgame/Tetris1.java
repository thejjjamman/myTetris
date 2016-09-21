package tgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//sysout cntrol space

public class Tetris1 extends ApplicationAdapter {
	
	public static final int WIDTH=500;
	public static final int HIGHT=650;
	public static final String TITLE = "Tetris";
	
	private GameStateManager gsm;
	SpriteBatch batch;
	
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm=new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		
		
		/*
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		*/
		
	}
}
