package flappyking.neat;

import flappyking.neat.calculations.Calculator;
import flappyking.neat.genome.Genome;

public class Client implements Cloneable {

    transient private Calculator calculator;

    private Genome genome;
    private double score;
    transient private Species species;

    public void generate_calculator(){
        this.calculator = new Calculator(genome);
    }

    public double[] calculate(double... input){
        if(this.calculator == null) generate_calculator();
        return this.calculator.calculate(input);
    }

    public double distance(Client other) {
        return this.getGenome().distance(other.getGenome());
    }

    public void mutate() {
        getGenome().mutate();
    }

    public Calculator getCalculator() {
        return calculator;
    }
    
    public void setCalculator(Calculator calc) {
    	this.calculator = calc;
    }
    
    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
    	return super.clone();
    }
}
