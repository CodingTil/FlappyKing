package flappyking.file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import flappyking.game.Constants;
import flappyking.neat.Client;

public class FileManager {
	
	public static void saveFile(Client client, String path) {
		Json json = new Json();
		String content = json.prettyPrint(client);
		FileHandle folder = Gdx.files.absolute(Constants.getPrefix());
		FileHandle file = Gdx.files.absolute(Constants.getPrefix() + path);
		if(!folder.exists()) folder.mkdirs();
		file.writeString(content, false);
	}
	
	public static Client readFromFile(String path) {
		FileHandle folder = Gdx.files.absolute(Constants.getPrefix());
		FileHandle file = Gdx.files.absolute(Constants.getPrefix() + path);
		if(!folder.exists()) folder.mkdirs();
		if(!file.exists()) return null;
		String content = file.readString();
		Json json = new Json();
		Client client = json.fromJson(Client.class, content);
		return client;
	}
	
}
