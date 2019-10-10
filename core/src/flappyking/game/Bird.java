package flappyking.game;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public abstract class Bird extends Circle {
	private static final long serialVersionUID = 1L;

	protected Vector2 velocity;
	protected Color fill;
	
	/**
	 * <h1>Bird Constructor</h1>
	 * Color is based on ThreadLocalRandom
	 */
	public Bird() {
		velocity = new Vector2();
		set(new Vector2(Constants.BIRD_X, Constants.BIRD_Y_START), Constants.BIRD_RADIUS);
		this.fill = new Color(ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat(), ThreadLocalRandom.current().nextFloat(), 1);
	}
	
	/**
	 * <h1>Bird Constructor</h1>
	 * 
	 * @param random for setting a random Color based on the random parameter
	 */
	public Bird(Random random) {
		this();
		this.fill = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
	}

	/**
	 * <h1>Bird Constructor</h1>
	 * 
	 * @param fill Color of the Birds symbol
	 */
	public Bird(Color fill) {
		this();
		this.fill = fill;
	}
	
	public abstract void handleInput();
	
	/**
	 * <h1>Update Mechanism</h1>
	 * Updates the Bird:
	 * - Adds Gravity to the birds velocity scaled by dt
	 * - Updates the location based on the velocity
	 * 
	 * @param dt The time between two frames
	 * @return
	 */
	public void update(float dt) {
		velocity.add(0, Constants.GRAVITY);
		velocity.scl(dt);
		setPosition(getLocation().add(0, velocity.y));
		velocity.scl(1 / dt);
	}
	
	/**
	 * Adds a jump force to the birds velocity
	 */
	public void jump() {
		velocity.y = Constants.JUMP_FORCE;
	}
	
	/**
	 * @return Returns a vector of the birds location
	 */
	public Vector2 getLocation() {
		return new Vector2(x, y);
	}
	
	/**
	 * @return Returns a vector of the birds velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	
	/**
	 * @return Returns the birds color
	 */
	public Color getFill() {
		return fill;
	}
}
