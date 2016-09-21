package tgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import tgame.Tetris1;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=Tetris1.WIDTH;
		config.height= Tetris1.HIGHT;
		config.title=Tetris1.TITLE;
		config.resizable=false;	
		new LwjglApplication(new Tetris1(), config);
	}
}
