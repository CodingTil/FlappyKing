package flappyking.game;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.utils.Array;

public class Game {
	private Bird bird;
	private Array<Pipe> pipes;
	private Random random;

	private int score;
	private boolean justScoredPipe;
	private int indexNextPipe;
	private double distanceTraveled;
	private boolean playing;
	private boolean beingRendered;
	
	/**
	 * <h1>Game Constructor</h1>
	 * All attributes will be initialized here and the Game will start.
	 * The random attribute will be initialized with ThreadLocalRandom.
	 * 
	 * @param width Width of the Game
	 */
	public Game(Bird bird, boolean beingRendered) {
		pipes = new Array<Pipe>();
		score = 0;
		justScoredPipe = false;
		indexNextPipe = 0;
		playing = true;
		this.beingRendered = beingRendered;
		this.random = ThreadLocalRandom.current();
		this.bird = bird;
	}
	
	/**
	 * <h1>Game Constructor</h1>
	 * All attributes will be initialized here and the Game will start.
	 * 
	 * @param width Width of the Game
	 * @param random Used to create identical games. Default: ThreadLocalRandom
	 */
	public Game(Bird bird, boolean beingRendered, Random random) {
		this(bird, beingRendered);
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
		indexNextPipe = getIndexofNextPipe();
		distanceTraveled += (double) dt * (- Constants.PIPE_VELOCITY);
		
		double[] input = new double[]{Constants.map(pipes.get(indexNextPipe).getLocationTopPipe().x - bird.x, 0, (score == 0 ? Constants.WIDTH : Constants.PIPE_GAP_HORIZONTAL) + Constants.PIPE_WIDTH, 0, 1), Constants.map((pipes.get(indexNextPipe).getLocationTopPipe().y - Constants.PIPE_GAP_VERTICAL * 0.5), Constants.PIPE_LOWEST_OPENING + Constants.PIPE_GAP_VERTICAL * 0.5, Constants.PIPE_LOWEST_OPENING + Constants.PIPE_FLUCTUATION + Constants.PIPE_GAP_VERTICAL * 0.5, 0, 1)};
		bird.update(dt, input);
		for(Pipe pipe : pipes) {
			pipe.update(dt);
		}
		if (detectCollision())
			playing = false;
		if (detectScore())
			score++;
	}
	
	/**
	 * <h1>Collision Detection Algorithm</h1>
	 * Checks Collisions with the Pipes, Floor and Sky
	 * 
	 * @return Returns true, if a Collision could be detected, false if not.
	 */
	private boolean detectCollision() {
		// Ground Detection
		if (bird.y - bird.radius <= Constants.FLOOR_HEIGHT) {
			return true;
		}
		
		// Sky Detection
		if(bird.y - bird.radius > Constants.HEIGHT) {
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
		if(!justScoredPipe && bird.x > firstPipe.getLocationTopPipe().x + Constants.PIPE_WIDTH) {
			justScoredPipe = true;
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
					pipes.add(new Pipe(Constants.WIDTH, beingRendered, random));
				}else {
					pipes.add(new Pipe(pipes.get(i - 1).getLocationTopPipe().x + Constants.PIPE_WIDTH + Constants.PIPE_GAP_HORIZONTAL, beingRendered, random));
				}
			}
		} else {
			
			// Mid Game
			Pipe firstPipe = pipes.first();
			if (firstPipe.getLocationTopPipe().x + Constants.PIPE_WIDTH < 0) {
				justScoredPipe = false;
				Pipe lastPipe = pipes.get(pipes.size - 1);
				// Repositioning the Pipe and putting it to the end of the Array
				pipes.removeIndex(0);
				firstPipe.reposition(lastPipe.getLocationTopPipe().x + Constants.PIPE_WIDTH + Constants.PIPE_GAP_HORIZONTAL);
				pipes.add(firstPipe);
			}
		}
	}
	
	private int getIndexofNextPipe() {
		if(justScoredPipe) {
			return 1;
		}else {
			return 0;
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
	
	public Pipe getNextPipe() {
		return pipes.get(indexNextPipe);
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
	
	public Random getRandom() {
		return random;
	}
	
	public double getDistanceTraveled() {
		return distanceTraveled;
	}
	
	public boolean isBeingRendered() {
		return beingRendered;
	}
}
