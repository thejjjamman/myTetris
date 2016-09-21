package tgame;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {

	
	private Stack<state> states;
	public GameStateManager(){
		states = new  Stack<state>();
		
	
	}
	
	public void push(state st){
		states.push(st);
	}
	
	public void pop(){
		states.pop();
	}
	
	public void set(state st){
		states.pop();
		states.push(st);
		
	}
	
	public void update(float dt){
		//dt change in time from 2 renders
	states.peek().update(dt);
	
	}
	public void render(SpriteBatch SB){
		
		states.peek().render(SB);
	}
	
}
