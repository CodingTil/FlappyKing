package flappyking.game.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import flappyking.game.Constants;
import flappyking.game.FlappyKing;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		FlappyKing flappyking = new FlappyKing();
		DisplayMode displaymode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		config.width = displaymode.width;
		config.height = displaymode.height;
		config.foregroundFPS = displaymode.refreshRate;
		config.title = Constants.TITLE;
		config.fullscreen = true;
		config.allowSoftwareMode = true;
		config.vSyncEnabled = true;
		config.resizable = false;
		new LwjglApplication(flappyking, config);
	}
}
