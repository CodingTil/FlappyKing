package flappyking.states;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {
	private Stack<State> states;
	
	/**
	 * <h1>GameStateManager Constructor</h1>
	 * Initializes the attributes
	 */
	public GameStateManager() {
		states = new Stack<State>();
	}
	
	/**
	 * Pushes a State on top of the Stack
	 * @param state - GameState
	 */
	public void push(State state) {
		states.push(state);
	}
	
	/**
	 * Removes the State on the top of the Stack
	 */
	public void pop() {
		states.pop();
	}
	
	/**
	 * Removes the State on the top of the Stack, then Pushes a new GameState to the Stack
	 * @param state GameState
	 */
	public void set(State state) {
		states.pop();
		states.push(state);
	}
	
	/**
	 * <h1>Update Mechanism</h1>
	 * Updates the State on top of the Stack
	 * 
	 * @param dt The time between two frames
	 */
	public void update(float dt) {
		states.peek().handleInput();
		states.peek().update(dt);
	}
	
	/**
	 * <h1>Render Mechanism</h1>
	 * Renders the State on top of the Stack
	 * 
	 * @param sb SpriteBatch to Render Elements on the Screen
	 */
	public void render(SpriteBatch sb) {
		states.peek().render(sb);
	}
	
}
