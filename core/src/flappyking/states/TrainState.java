package flappyking.states;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;

import flappyking.file.FileManager;
import flappyking.game.ComputerBird;
import flappyking.game.Constants;
import flappyking.game.Game;
import flappyking.neat.Client;
import flappyking.neat.Neat;

public class TrainState extends PlayState {

	Neat neat;
	Client bestClient;
	int generation;
	
	TrainingThread trainingThread;

	public TrainState(GameStateManager gsm, Neat neat) {
		super(gsm, new Game[0]);

		this.neat = neat;
		try {
			this.bestClient = (Client) neat.getClient(0).clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		createBestClientGame();

		startTrainingThread();
	}
	
	private void startTrainingThread() {
		this.trainingThread = new TrainingThread(this.neat);
		Thread thread = new Thread(trainingThread, "TrainingThread");
		thread.start();
	}

	private void createBestClientGame() {
		games.clear();
		games.add(new Game(new ComputerBird(bestClient), true));
		
		anyGameRunning = true;
	}
	
	private void calculateBestClient() {
		int indexBestClient = 0;
		for(int i = 0; i < neat.getClients().size(); i++) {
			if(neat.getClient(indexBestClient).getScore() < neat.getClient(i).getScore()) {
				indexBestClient = i;
			}
		}
		try {
			bestClient = (Client) neat.getClient(indexBestClient).clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(float dt) {
		super.update(dt);

		if (!anyGameRunning) {
			calculateBestClient();
			createBestClientGame();
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		trainingThread.stop();
		
		calculateBestClient();
		FileManager.saveFile(bestClient, Constants.LOCATION_BEST_CLIENT);
	}
	
	class TrainingThread implements Runnable {
		
		Neat neat;
		
		ArrayList<Game> games = new ArrayList<>();
		
		boolean training;
		boolean anyGameRunning;
		
		double mostDistanceTraveledInGeneration;
		
		public TrainingThread(Neat neat) {
			this.neat = neat;
			this.training = true;
		}
		
		private void rateClient(Game game) {
			double distanceTraveled = game.getDistanceTraveled();
			((ComputerBird) game.getBird()).getClient().setScore(distanceTraveled);
			if(distanceTraveled > this.mostDistanceTraveledInGeneration) this.mostDistanceTraveledInGeneration = distanceTraveled;
		}
		
		private void evolve() {
			mostDistanceTraveledInGeneration = 0;
			for(Game game : this.games) {
				this.rateClient(game);
			}
			
			this.neat.evolve();
			
			Gdx.app.log("NEAT-TRAINING", "Generation: " + generation + " || Most Distance Traveled in this Generation: " + mostDistanceTraveledInGeneration);
			
			generation++; //NOT this.
		}
		
		private void createGames() {
			long seed = ThreadLocalRandom.current().nextLong();
			
			this.games.clear();
			
			for(Client client : this.neat.getClients().getData()) {
				this.games.add(new Game(new ComputerBird(client), false, new Random(seed)));
			}
			
			this.anyGameRunning = true;
		}
		
		private void updateGames() {
			this.anyGameRunning = false;
			for(Game game : this.games) {
				if(game.isPlaying()) {
					game.update((float) Constants.FPS_60_DT);
					this.anyGameRunning = true;
				}
			}
		}
		
		@Override
		public void run() {
			while(this.training) {
				if(this.anyGameRunning) {
					this.updateGames();
				}else {
					this.evolve();
					this.createGames();
				}
			}
		}
		
		public void stop() {
			this.training = false;
		}
		
	}

}
