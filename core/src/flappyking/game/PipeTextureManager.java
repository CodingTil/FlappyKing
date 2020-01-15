package flappyking.game;

import com.badlogic.gdx.graphics.Texture;

public class PipeTextureManager {

	private static final Texture green_pipe_top = new Texture("green_pipe_top.png"),
			green_pipe_bottom = new Texture("green_pipe_bottom.png"), 
			blue_pipe_top = new Texture("blue_pipe_top.png"),
			blue_pipe_bottom = new Texture("blue_pipe_bottom.png"), 
			red_pipe_top = new Texture("red_pipe_top.png"),
			red_pipe_bottom = new Texture("red_pipe_bottom.png"), 
			yellow_pipe_top = new Texture("yellow_pipe_top.png"),
			yellow_pipe_bottom = new Texture("yellow_pipe_bottom.png");

	public static synchronized Texture getGreenPipeTop() {
		return green_pipe_top;
	}

	public static synchronized Texture getGreenPipeBottom() {
		return green_pipe_bottom;
	}

	public static synchronized Texture getBluePipeTop() {
		return blue_pipe_top;
	}

	public static synchronized Texture getBluePipeBottom() {
		return blue_pipe_bottom;
	}

	public static synchronized Texture getRedPipeTop() {
		return red_pipe_top;
	}

	public static synchronized Texture getRedPipeBottom() {
		return red_pipe_bottom;
	}

	public static synchronized Texture getYellowPipeTop() {
		return yellow_pipe_top;
	}

	public static synchronized Texture getYellowPipeBottom() {
		return yellow_pipe_bottom;
	}
	
	
	
}
