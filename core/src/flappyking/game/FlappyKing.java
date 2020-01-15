package flappyking.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import flappyking.states.GameStateManager;
import flappyking.states.MenuState;

public class FlappyKing extends ApplicationAdapter {
	private GameStateManager gsm;
	private SpriteBatch sb;
	
	/**
	 * <h1>Method creating the application</h1>
	 * - Initializes the GameStateManager and SpriteBatch
	 * - Sets the first GameState -> MenuState
	 */
	@Override
	public void create() {
		gsm = new GameStateManager();
		sb = new SpriteBatch();
		
		gsm.push(new MenuState(gsm));
		
		Gdx.gl20.glClearColor(1, 0, 0, 1);
	}
	
	/**
	 * <h1>GameLoop</h1>
	 * - Clears the screen
	 * - Updates the current State
	 * - Renders the current State by passing a SpriteBatch
	 */
	@Override
	public void render() {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}
	
	/**
	 * Disposes the SpriteBatch
	 */
	@Override
	public void dispose() {
		sb.dispose();
	}
}
