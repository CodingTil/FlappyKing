package flappyking.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class PlayerBird extends Bird {
	private static final long serialVersionUID = 1L;
	private final int keyCode;
	
	public PlayerBird(int keyCode) {
		super();
		this.keyCode = keyCode;
	}

	public PlayerBird(Color fill, int keyCode) {
		super(fill);
		this.keyCode = keyCode;
	}

	public PlayerBird(Random random, int keyCode) {
		super(random);
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
