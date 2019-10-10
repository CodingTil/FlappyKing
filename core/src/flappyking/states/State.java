package flappyking.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Abstract class which sets the foundation of a GameState
 * @author Til Mohr
 */
public abstract class State {
	protected OrthographicCamera cam;
	protected Vector2 mouse;
	protected GameStateManager gsm;
	protected Skin skin;
	protected Texture background;
	
	protected int width, height;
	
	/**
	 * <h1>State Constructor</h1>
	 * Initializes all attributes
	 * 
	 * @param gsm GameStateManager
	 * @param width Application Width
	 * @param height Application Height
	 */
	public State(GameStateManager gsm, int width, int height) {
		this.gsm = gsm;
		this.width = width;
		this.height = height;
		cam = new OrthographicCamera();
		mouse = new Vector2();
		skin = new Skin(Gdx.files.internal("skins/flat-earth/skin/flat-earth-ui.json"));
		background = new Texture("background.jpg");
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
	public abstract void dispose();
}
