package flappyking.game;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.utils.Array;

public class Game {
	private Bird bird;
	private Array<Pipe> pipes;
	private Random random;

	private int score;
	private boolean justScoredFirstPipe;
	private boolean playing;

	private int width;
	
	/**
	 * <h1>Game Constructor</h1>
	 * All attributes will be initialized here and the Game will start.
	 * The random attribute will be initialized with ThreadLocalRandom.
	 * 
	 * @param width Width of the Game
	 */
	public Game(Bird bird, int width) {
		pipes = new Array<Pipe>();
		score = 0;
		justScoredFirstPipe = false;
		playing = true;
		this.random = ThreadLocalRandom.current();
		this.bird = bird;
		this.width = width;
	}
	
	/**
	 * <h1>Game Constructor</h1>
	 * All attributes will be initialized here and the Game will start.
	 * 
	 * @param width Width of the Game
	 * @param random Used to create identical games. Default: ThreadLocalRandom
	 */
	public Game(Bird bird, int width, Random random) {
		this(bird, width);
		this.random = random;
	}
	
	/**
	 * Passes the input to the bird instance
	 */
	public void handleInput() {
		bird.handleInput();
	}
	
	/**
	 * <h1>Update Mechanism</h1>
	 * Updates the game:
	 * - Updates the Bird
	 * - Updates the Pipes
	 * - Detects Collisions -> if detected, the game ends
	 * - Detects Scores -> if detected, the score increases by 1
	 * - Checks, if any pair of pipes can be reused
	 * 
	 * @param dt The time between two frames
	 */
	public void update(float dt) {
		generatePipes();
		
		bird.update(dt);
		for(Pipe pipe : pipes) {
			pipe.update(dt);
		}
		if (detectCollision())
			playing = false;
		if (detectScore())
			score++;
	}
	
	/**
	 * Disposes all unused Textures
	 */
	public void dispose() {
		for(Pipe pipe : pipes) {
			pipe.dispose();
		}
	}
	
	/**
	 * <h1>Collision Detection Algorithm</h1>
	 * Checks Collisions with the Pipes and the Floor
	 * 
	 * @return Returns true, if a Collision could be detected, false if not.
	 */
	private boolean detectCollision() {
		// Ground Detection
		if (bird.y - bird.radius <= Constants.FLOOR_HEIGHT) {
			return true;
		}

		// Pipe Detecting
		for (Pipe pipe : pipes) {
			if (pipe.collides(bird))
				return true;
		}

		// No Collision could be detected
		return false;
	}
	
	/**
	 * <h1>Score Detection Algorithm</h1>
	 * Checks, if the bird has flew through a pair of pipes.
	 * 
	 * @return Returns true, if the bird has scored a point.
	 */
	private boolean detectScore() {
		Pipe firstPipe = pipes.get(0);
		if(!justScoredFirstPipe && bird.x > firstPipe.getLocationTopPipe().x + firstPipe.getHitboxTopPipe().width) {
			justScoredFirstPipe = true;
			return true;
		}
		return false;
	}
	
	/**
	 * <h1>Pipe Generation Algorithm</h1>
	 * Generates pair(s) of pipes.
	 * 
	 * <h2>On Game Start:</h2>
	 * Generates the amount of pipe-pairs defined in Constants.
	 * 
	 * <h2>Mid Game:</h2>
	 * Reuses the unused pair of pipes by repositioning them.
	 */
	private void generatePipes() {
		if (pipes.isEmpty()) {
			
			// Game Start
			for (int i = 0; i < Constants.PIPE_COUNT; i++) {
				if(i == 0) {
					pipes.add(new Pipe(width, random));
				}else {
					pipes.add(new Pipe(width + i * (Constants.PIPE_GAP_HORIZONTAL + pipes.get(0).getTopPipe().getWidth()), random));
				}
			}
		} else {
			
			// Mid Game
			Pipe firstPipe = pipes.get(0);
			if (firstPipe.getLocationTopPipe().x + firstPipe.getTopPipe().getWidth() < 0) {
				justScoredFirstPipe = false;
				Pipe lastPipe = pipes.get(pipes.size - 1);
				// Repositioning the Pipe and putting it to the end of the Array
				pipes.removeIndex(0);
				firstPipe.reposition(lastPipe.getLocationTopPipe().x + lastPipe.getTopPipe().getWidth() + Constants.PIPE_GAP_HORIZONTAL);
				pipes.add(firstPipe);
			}
		}
	}
	
	/**
	 * @return Returns the bird instance of the game
	 */
	public Bird getBird() {
		return bird;
	}
	
	/**
	 * @return Returns a list of all the pair of pipes in the game
	 */
	public Array<Pipe> getPipes() {
		return pipes;
	}
	
	/**
	 * @return Returns whether the game is running
	 */
	public boolean isPlaying() {
		return playing;
	}

	/**
	 * @return Returns the current score
	 */
	public int getScore() {
		return score;
	}
}
