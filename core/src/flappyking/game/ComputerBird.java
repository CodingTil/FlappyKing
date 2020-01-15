package flappyking.game;

import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;

import flappyking.neat.Client;

public class ComputerBird extends Bird {
	private static final long serialVersionUID = 1L;
	
	private Client client;

	public ComputerBird(Client client) {
		super();
		this.client = client;
	}

	public ComputerBird(Color fill, Client client) {
		super(fill);
		this.client = client;
	}

	public ComputerBird(Random random, Client client) {
		super(random);
		this.client = client;
	}

	@Override 
	public void handleInput() {
		//Unused so far
	}
	
	@Override
	public void update(float dt, double[] neuralNetworkInput) {
		super.update(dt, neuralNetworkInput);
		
		double[] input = Arrays.copyOf(neuralNetworkInput, neuralNetworkInput.length + 2);
		input[input.length - 2] = Constants.map(y, Constants.FLOOR_HEIGHT, Constants.HEIGHT, 0, 1);
		input[input.length - 1] = velocity.y * dt;
		//System.out.println(Arrays.toString(input));
		double[] output = client.calculate(input);
		if(output[0] > output[1]) {
			jump();
		}
	}
	
	public Client getClient() {
		return client;
	}
}
