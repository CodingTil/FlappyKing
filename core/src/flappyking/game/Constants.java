package flappyking.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

public class Constants {
	// APPLICATION
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int FRAMERATE = 60;
	public static final String TITLE = "Flappy King";
	public static final double FPS_60_DT = 1d / 60d;

	// GAME - General
	public static final float GRAVITY = -32;
	public static final float JUMP_FORCE = 590;

	// GAME - Bird
	public static final int BIRD_RADIUS = 25;
	public static final int BIRD_X = 200;
	public static final int BIRD_Y_START = 600;

	// GAME - Pipe
	public static final int PIPE_GAP_VERTICAL = 180;
	public static final int PIPE_GAP_HORIZONTAL = 225;
	public static final int PIPE_WIDTH = 157;
	public static final int PIPE_HEIGHT = 666;
	public static final int PIPE_FLUCTUATION = 500;
	public static final int PIPE_LOWEST_OPENING = 300;
	public static final int PIPE_COUNT = 5;
	public static final float PIPE_VELOCITY = -200;

	// GAME - Floor
	public static final int FLOOR_HEIGHT = 206;

	// GAME - Score Display
	public static final int SCORE_WIDTH = 100;
	public static final float SCORE_SCALE = 2.5f;
	public static final int SCORE_X = 50;
	public static final int SCORE_Y = 125;

	// GAME - FPS Display
	public static final float FPS_SCALE = 3f;
	public static final int FPS_X = 50;
	public static final int FPS_Y = 1030;

	// NEAT
	public static final int INPUT_SIZE = 4;
	public static final int OUTPUT_SIZE = 2;
	public static final int CLIENT_AMOUNT = 1000;

	// FILE
	public static final String LOCATION_BEST_CLIENT = "Best_Client.json";

	public static double map(double value, double bottom, double top, double oldBottom, double oldTop) {
		return ((value - bottom) / (top - bottom)) * (oldTop - oldBottom) + oldBottom;
	}

	public final static String getPrefix() {
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			String os = System.getProperty("os.name").toLowerCase();
			if (os.indexOf("win") >= 0) {
				return System.getenv("APPDATA") + "/FlappyKing/";
			}
		}
		return ".FlappyKing/";
	}
}
