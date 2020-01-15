package flappyking.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

import flappyking.game.Bird;
import flappyking.game.Constants;
import flappyking.game.Game;
import flappyking.game.Pipe;

/**
 * The Play State. Here, the game will be played.
 * @author Til Mohr
 */
public class PlayState extends State {
	private Texture floor;
	protected Array<Game> games;
	private ShapeRenderer shapeRenderer;
	private BitmapFont textRenderer;
	
	//Works, but doesn't look good as code. Improve later on.
	private Bird tempBird;
	protected boolean anyGameRunning;
	protected int indexLastGameRunning;
	
	/**
	 * <h1>PlayState Constructor</h1>
	 * Initializes all the attributes and creates the Menu UI
	 * 
	 * @param gsm GameStateManager
	 * @param width Application Width
	 * @param height Application Height
	 */
	public PlayState(GameStateManager gsm, Game...games) {
		super(gsm);
		
		floor = new Texture("floor.png");
		shapeRenderer = new ShapeRenderer();
		textRenderer = new BitmapFont();
		this.games = new Array<Game>(games);
		
		textRenderer.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		anyGameRunning = true;
	}

	/**
	 * <h1>User-Input-Handling Mechanism</h1>
	 * Handling of the User Input
	 */
	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			//TODO save the games
			dispose();
			gsm.set(new MenuState(gsm));
		}
	}

	/**
	 * <h1>Update Mechanism</h1>
	 * Updates the state:
	 * - Checks user input
	 * - Updates every running game
	 * - If no game is running, the application returns to the MainMenu
	 * 
	 * @param dt Unused.
	 */
	@Override
	public void update(float dt) {
		if(anyGameRunning) {
			anyGameRunning = false;
			for(Game game : games) {
				if(game.isPlaying()) {
					game.handleInput();
					game.update(dt);
					anyGameRunning = true;
					indexLastGameRunning = games.indexOf(game, true);
				}
			}
		}
	}
	
	/**
	 * <h1>Render Mechanism</h1>
	 * Renders the Game(s)
	 * 
	 * @param sb SpriteBatch to Render Elements on the Screen
	 */
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		
		//Rendering Background
		sb.draw(background, 0, 0);
		
		//Rendering the Pipes
		for(Game game : games) {
			if(game.isBeingRendered() && games.indexOf(game, true) == indexLastGameRunning) {
		    	for(Pipe pipe : game.getPipes()) {
		    		sb.draw(pipe.getBottomPipe(), pipe.getLocationBottomPipe().x, pipe.getLocationBottomPipe().y);
		    		sb.draw(pipe.getTopPipe(), pipe.getLocationTopPipe().x, pipe.getLocationTopPipe().y);
		    	}
		    }
		}
		
		//Rendering Floor over Pipes
		sb.draw(floor, 0, 0);
		
		//Rendering the Scores
		int iterator = 0;
		textRenderer.getData().setScale(Constants.SCORE_SCALE);
		for(Game game : games) {
			textRenderer.setColor(game.getBird().getFill());
			textRenderer.draw(sb, game.getScore() + "", Constants.SCORE_X + iterator * Constants.SCORE_WIDTH, Constants.SCORE_Y);
			
			iterator++;
		}
		
		//Rendering the FPS
		textRenderer.getData().setScale(Constants.FPS_SCALE);
		textRenderer.setColor(Color.GREEN);
		textRenderer.draw(sb, Float.toString(Gdx.graphics.getFramesPerSecond()), Constants.FPS_X, Constants.FPS_Y);
		
		sb.end();
		
		//Rendering the Birds
	    shapeRenderer.begin(ShapeType.Filled);
		for(Game game : games) {
			tempBird = game.getBird();
		    shapeRenderer.setColor(tempBird.getFill());
		    shapeRenderer.circle(tempBird.getLocation().x, tempBird.getLocation().y, Constants.BIRD_RADIUS);
		}
	    shapeRenderer.end();
	}
	
	/**
	 * Disposes all unused Textures
	 */
	@Override
	public void dispose() {
		background.dispose();
		floor.dispose();
		shapeRenderer.dispose();
		textRenderer.dispose();
	}
}
