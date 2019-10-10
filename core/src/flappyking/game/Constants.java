package flappyking.game;

public class Constants {
	//APPLICATION
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;
	public static final int FRAMERATE = 60;
	public static final String TITLE = "Flappy King";
	
	//GAME - General
	public static final float GRAVITY = -32;
	public static final float JUMP_FORCE = 590;
	
	//GAME - Bird
	public static final int BIRD_RADIUS = 25;
	public static final int BIRD_X = 200;
	public static final int BIRD_Y_START = 600;
	
	//GAME - Pipe
	public static final int PIPE_GAP_VERTICAL = 180;
	public static final int PIPE_GAP_HORIZONTAL = 225;
	public static final int PIPE_FLUCTUATION = 500;
	public static final int PIPE_LOWEST_OPENING = 300;
	public static final int PIPE_COUNT = 5;
	public static final float PIPE_VELOCITY = -200;
	
	//GAME - Floor
	public static final int FLOOR_HEIGHT = 206;
	
	//GAME - Score Display
	public static final int SCORE_WIDTH = 100;
	public static final float SCORE_SCALE = 2.5f;
	public static final int SCORE_X = 50;
	public static final int SCORE_Y = 125;
	
	//Game - FPS Display
	public static final float FPS_SCALE = 3f;
	public static final int FPS_X = 50;
	public static final int FPS_Y = 1030;
}
