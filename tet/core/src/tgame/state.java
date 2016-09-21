package tgame;

//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class state {

	//protected OrthographicCamera cam;
	protected Vector3 mouse;
	
	protected GameStateManager gsm;
	
	protected state(GameStateManager gsmIn){
		
		this.gsm = gsmIn;
	//	cam = new OrthographicCamera();
		mouse = new Vector3();
		
	}
	
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
	public abstract void dispose();
	
	
}
