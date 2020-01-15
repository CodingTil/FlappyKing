package flappyking.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pipe {
	private Texture topPipe, bottomPipe;
	private Vector2 locationTopPipe, locationBottomPipe;
	private Vector2 velocity;
	private Rectangle hitboxTopPipe, hitboxBottomPipe;
	private Random random;

	/**
	 * <h1>Pipe Constructor</h1> Initializes all attributes
	 * 
	 * @param x
	 * @param random
	 */
	public Pipe(float x, boolean beingRendered, Random random) {
		this.random = random;
		if(beingRendered) pickColor();

		locationTopPipe = new Vector2();
		locationBottomPipe = new Vector2();
		velocity = new Vector2(Constants.PIPE_VELOCITY, 0);
		hitboxTopPipe = new Rectangle();
		hitboxBottomPipe = new Rectangle();

		hitboxTopPipe.setSize(Constants.PIPE_WIDTH, Constants.PIPE_HEIGHT);
		hitboxBottomPipe.setSize(Constants.PIPE_WIDTH, Constants.PIPE_HEIGHT);
		
		reposition(x);
	}

	/**
	 * <h1>Update Mechanism</h1> Updates the Pipes
	 * 
	 * @param dt The time between two frames
	 */
	public void update(float dt) {
		velocity.scl(dt);

		locationTopPipe.add(velocity);
		locationBottomPipe.add(velocity);
		hitboxTopPipe.setPosition(locationTopPipe);
		hitboxBottomPipe.setPosition(locationBottomPipe);

		velocity.scl(1 / dt);
	}

	/**
	 * Repositions the pair of pipes
	 * 
	 * @param x Position on the x-axis
	 */
	public void reposition(float x) {
		locationTopPipe.set(new Vector2(x + hitboxTopPipe.getWidth(), random.nextInt(Constants.PIPE_FLUCTUATION)
				+ Constants.PIPE_GAP_VERTICAL + Constants.PIPE_LOWEST_OPENING));
		locationBottomPipe.set(new Vector2(x + hitboxBottomPipe.getWidth(),
				locationTopPipe.y - Constants.PIPE_GAP_VERTICAL - hitboxBottomPipe.getHeight()));
		hitboxTopPipe.setPosition(locationTopPipe);
		hitboxBottomPipe.setPosition(locationBottomPipe);
	}

	/**
	 * <h1>Collision Detection Algorithm</h1> Checks, if the bird collides with one
	 * of the pipes
	 * 
	 * @param bird Games Bird
	 * @return Returns true, if a Collision could be detected, false if not.
	 */
	public boolean collides(Bird bird) {
		return Intersector.overlaps(bird, hitboxTopPipe) || Intersector.overlaps(bird, hitboxBottomPipe);
	}

	public void pickColor() {
		int i = random.nextInt(4);
		switch (i) {
		case 0:
			topPipe = PipeTextureManager.getGreenPipeTop();
			bottomPipe = PipeTextureManager.getGreenPipeBottom();
			break;
		case 1:
			topPipe = PipeTextureManager.getRedPipeTop();
			bottomPipe = PipeTextureManager.getRedPipeBottom();
			break;
		case 2:
			topPipe = PipeTextureManager.getBluePipeTop();
			bottomPipe = PipeTextureManager.getBluePipeBottom();
			break;
		case 3:
			topPipe = PipeTextureManager.getYellowPipeTop();
			bottomPipe = PipeTextureManager.getYellowPipeBottom();
			break;
		default:
			topPipe = PipeTextureManager.getGreenPipeTop();
			bottomPipe = PipeTextureManager.getGreenPipeBottom();
			break;
		}
	}

	public Texture getTopPipe() {
		return topPipe;
	}

	public Texture getBottomPipe() {
		return bottomPipe;
	}

	public Vector2 getLocationTopPipe() {
		return locationTopPipe;
	}

	public Vector2 getLocationBottomPipe() {
		return locationBottomPipe;
	}

	public Rectangle getHitboxTopPipe() {
		return hitboxTopPipe;
	}

	public Rectangle getHitboxBottomPipe() {
		return hitboxBottomPipe;
	}
}
