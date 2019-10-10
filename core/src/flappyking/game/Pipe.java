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

	public Pipe(float x, Random random) {
		this.random = random;
		String color = pickColor();
		topPipe = new Texture(color + "_pipe_top.png");
		bottomPipe = new Texture(color + "_pipe_bottom.png");

		locationTopPipe = new Vector2();
		locationBottomPipe = new Vector2();
		velocity = new Vector2(Constants.PIPE_VELOCITY, 0);
		hitboxTopPipe = new Rectangle();
		hitboxBottomPipe = new Rectangle(); 

		hitboxTopPipe.setSize(topPipe.getWidth(), topPipe.getHeight());
		hitboxBottomPipe.setSize(bottomPipe.getWidth(), bottomPipe.getHeight());

		reposition(x + topPipe.getWidth());
	}

	/**
	 * <h1>Update Mechanism</h1> 
	 * Updates the Pipes
	 * 
	 * @param dt The time between two frames
	 */
	public void update(float dt) {
		velocity.scl(dt);
		System.out.println();

		locationTopPipe.add(velocity);
		locationBottomPipe.add(velocity);
		hitboxTopPipe.setPosition(locationTopPipe);
		hitboxBottomPipe.setPosition(locationBottomPipe);

		velocity.scl(1 / dt);
	}

	/**
	 * Disposes all unused Textures
	 */
	public void dispose() {
		topPipe.dispose();
		bottomPipe.dispose();
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
	 * <h1>Collision Detection Algorithm</h1> 
	 * Checks, if the bird collides with one of the pipes
	 * 
	 * @param bird Games Bird
	 * @return Returns true, if a Collision could be detected, false if not.
	 */
	public boolean collides(Bird bird) {
		return Intersector.overlaps(bird, hitboxTopPipe) || Intersector.overlaps(bird, hitboxBottomPipe);
	}

	public String pickColor() {
		int i = random.nextInt(4);
		switch (i) {
		case 0:
			return "green";
		case 1:
			return "red";
		case 2:
			return "blue";
		case 3:
			return "yellow";
		default:
			return "green";
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
