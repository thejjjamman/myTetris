package tgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class block {

	
	private Vector3 position;
	private Texture img;
	public boolean isCenter;
	public block(int x,int y, Texture img ,boolean isCenter){
		
		position=new Vector3(x,y,0);
		
		
		this.img=img;
		this.isCenter=isCenter;
	}
	
	

	public void update (){
		//position
		//position.add(0,0,0);
		
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public Texture getImg() {
		return img;
	}

	public void setImg(Texture img) {
		this.img = img;
	}
	
	
	
}
