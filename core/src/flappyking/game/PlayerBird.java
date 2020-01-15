package flappyking.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class PlayerBird extends Bird {
	private static final long serialVersionUID = 1L;
	private int keyCode;
	
	/**
	 * <h1>PlayerBird Constructor</h1>
	 * @param keyCode
	 */
	public PlayerBird(int keyCode) {
		super();
		init(keyCode);
	}

	public PlayerBird(Color fill, int keyCode) {
		super(fill);
		init(keyCode);
	}

	public PlayerBird(Random random, int keyCode) {
		super(random);
		init(keyCode);
	}
	
	private void init(int keyCode) {
		this.keyCode = keyCode;
	}

	/**
	 * Bird jumps on detected key-press
	 */
	@Override
	public void handleInput() {
		if(Gdx.input.isKeyJustPressed(keyCode)) {
			jump();
		}
	}

}
