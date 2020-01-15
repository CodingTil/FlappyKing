package flappyking.states;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import flappyking.file.FileManager;
import flappyking.game.ComputerBird;
import flappyking.game.Constants;
import flappyking.game.Game;
import flappyking.game.PlayerBird;
import flappyking.neat.Client;
import flappyking.neat.Neat;

/**
 * The MenuState is the Applications Menu. It is the first GameState when the
 * Application starts. From here, all other GameStates are accessible
 * 
 * @author Til Mohr
 */
public class MenuState extends State {

	private Stage stage;
	private Table table;
	private TextButton playButton;
	private TextButton playPlayerButton;
	private TextButton trainButton;
	private TextButton playComputerButton;
	private TextButton exit;

	/**
	 * <h1>MenuState Constructor</h1> Initializes all the attributes and creates the
	 * Menu UI
	 * 
	 * @param gsm    GameStateManager
	 * @param width  Application Width
	 * @param height Application Height
	 */
	public MenuState(GameStateManager gsm) {
		super(gsm);

		stage = new Stage(new ScreenViewport());
		table = new Table(skin);
		table.setFillParent(true);
		stage.addActor(table);

		playButton = new TextButton("Play".toUpperCase(), skin);
		playPlayerButton = new TextButton("Player against Player".toUpperCase(), skin);
		trainButton = new TextButton("Train".toUpperCase(), skin);
		playComputerButton = new TextButton("Play against Computer".toUpperCase(), skin);
		exit = new TextButton("Exit".toUpperCase(), skin);

		addListeners();

		table.add(playButton).pad(10).fillY().align(Align.top).width(playButton.getWidth() * 1.8f)
				.height(playButton.getHeight() * 1.8f);
		table.row().pad(10, 0, 10, 0);
		table.add(playPlayerButton).pad(10).fillY().align(Align.top).width(playPlayerButton.getWidth() * 1.2f)
				.height(playButton.getHeight() * 1.8f);
		table.row();
		table.add(trainButton).pad(10).fillY().align(Align.top).width(trainButton.getWidth() * 1.6f)
				.height(trainButton.getHeight() * 1.8f);
		table.row();
		table.add(playComputerButton).pad(10).fillY().align(Align.top).width(playComputerButton.getWidth() * 1.2f)
				.height(playComputerButton.getHeight() * 1.8f);
		table.row();
		table.add(exit).pad(10).fillY().align(Align.top).width(exit.getWidth() * 1.8f).height(exit.getHeight() * 1.8f);

		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * <h1>User-Input-Handling Mechanism</h1> Handling of the User Input
	 * 
	 * UNUSED Because of use of UI Elements such as Buttons
	 */
	@Override
	public void handleInput() {

	}

	/**
	 * <h1>Update Mechanism</h1> Updates the state: - Checks user input
	 * 
	 * @param dt Unused.
	 */
	@Override
	public void update(float dt) {

	}

	/**
	 * <h1>Render Mechanism</h1> Renders the State: - Renders the UI Elements -
	 * Renders the Background
	 * 
	 * @param sb SpriteBatch to Render Elements on the Screen
	 */
	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0);
		stage.getBatch().end();
		stage.draw();
		sb.end();
	}

	/**
	 * Disposes all unused Textures
	 */
	@Override
	public void dispose() {
		background.dispose();
		stage.dispose();
	}

	/**
	 * Adds the ClickListeners to the Buttons
	 */
	public void addListeners() {
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				gsm.set(new PlayState(gsm, new Game(new PlayerBird(Keys.SPACE), true)));
			};
		});
		playPlayerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				long seed = ThreadLocalRandom.current().nextLong();
				Game game1 = new Game(new PlayerBird(Keys.SPACE), true, new Random(seed));
				Game game2 = new Game(new PlayerBird(Keys.ENTER), true, new Random(seed));
				gsm.set(new PlayState(gsm, game1, game2));
			};
		});
		playComputerButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();

				long seed = ThreadLocalRandom.current().nextLong();
				Game game1 = new Game(new PlayerBird(Keys.SPACE), true, new Random(seed));
				Game game2;

				Client bestClient = FileManager.readFromFile(Constants.LOCATION_BEST_CLIENT);

				if(bestClient == null) {
					System.out.println("File doen't exist yet. Creating random (dumb) AI...");
					Neat neat = new Neat(Constants.INPUT_SIZE, Constants.OUTPUT_SIZE, Constants.CLIENT_AMOUNT);
					bestClient = neat.getClient((int) (ThreadLocalRandom.current().nextDouble() * Constants.CLIENT_AMOUNT));
				}
				game2 = new Game(new ComputerBird(bestClient), true, new Random(seed));
				gsm.set(new PlayState(gsm, game1, game2));
			};
		});
		trainButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				
				gsm.set(new TrainState(gsm, new Neat(Constants.INPUT_SIZE, Constants.OUTPUT_SIZE, Constants.CLIENT_AMOUNT)));
			}
		});
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			};
		});
	}
}
