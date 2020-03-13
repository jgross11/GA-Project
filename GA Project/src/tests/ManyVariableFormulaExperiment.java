package tests;

import java.util.Arrays;
import java.util.Comparator;

import components.Controller;
import components.FitnessFunction;
import components.Specimen;
import components.Trait;

	
/**
 * @author jgross11@ycp.edu
 *
 * advanced test experiment inspired by "Genetic Algorithm for Solving Simple Mathematical Equality Problem"
 * Denny Hermawanto - https://arxiv.org/ftp/arxiv/papers/1308/1308.4675.pdf that finds a close solution to 
 * (a + 2b - 3c + 4d - 5e + 6f - 7g + 8h) / -9i = 4
 */
public class ManyVariableFormulaExperiment {
	
	public static void main(String[] args) {
		
		// create controller to run experiment
		Controller controller = new Controller();
		
		// set population size to 100 specimen
		controller.setPopulationSize(100);
		
		// define and set controller fitness function - here, maximum fitness occurs when 
		// (a + 2b - 3c + 4d - 5e + 6f - 7g + 8h) / -9i = 4
		controller.setFitnessFunction(new FitnessFunction() {
			public double calculateFitness(Specimen s) {
				// TODO: add shortcut method to Trait class to retrieve value of trait at given index
				double num 	= s.getTrait(0).getValue() + 2*s.getTrait(1).getValue() - 3*s.getTrait(2).getValue()
							+ 4*s.getTrait(3).getValue() - 5*s.getTrait(4).getValue() + 6*s.getTrait(5).getValue()
							- 7*s.getTrait(6).getValue() + 8*s.getTrait(7).getValue();
				double denom = -9*s.getTrait(8).getValue();
				double val = Math.abs( (num / denom) - 4);
				return 1.0 / (1.0 + val);
			}
		});
		
		// create trait array containing blueprint trait data
		// in this case, we have 9 traits: a-value, b-value, and c-value, ... h-value, i-value.
		// TODO: eliminate wasted generation of values for blueprint traits
		 
		controller.setNumTraits(9);
		controller.setTrait(0, new Trait("a-val", Integer.MIN_VALUE / 100, Integer.MAX_VALUE / 100));
		controller.setTrait(1, new Trait("b-val", Integer.MIN_VALUE / 100, Integer.MAX_VALUE / 100));
		controller.setTrait(2, new Trait("c-val", Integer.MIN_VALUE / 100, Integer.MAX_VALUE / 100));
		controller.setTrait(3, new Trait("d-val", Integer.MIN_VALUE / 100, Integer.MAX_VALUE / 100));
		controller.setTrait(4, new Trait("e-val", Integer.MIN_VALUE / 100, Integer.MAX_VALUE / 100));
		controller.setTrait(5, new Trait("f-val", Integer.MIN_VALUE / 100, Integer.MAX_VALUE / 100));
		controller.setTrait(6, new Trait("g-val", Integer.MIN_VALUE / 100, Integer.MAX_VALUE / 100));
		controller.setTrait(7, new Trait("h-val", Integer.MIN_VALUE / 100, Integer.MAX_VALUE / 100));
		controller.setTrait(8, new Trait("i-val", Integer.MIN_VALUE / 100, Integer.MAX_VALUE / 100));
		
		// create initial population and calculate fitnesses.
		controller.initializePopulation();
		
		// sort population by fitness
		controller.printCurrentPopulation();
		
		// set max generations count - will run until fitness of 1 is achieved by default
		// controller.setMaxGenerationsCount(10);
		
		// set fitness threshold
		controller.setFitnessThreshold(0.999);
		
		System.out.println("### Fittest Specimen ###\n" + controller.findFittestSpecimen().toString());
		
		// set truncation threshold
		controller.setTruncationConstant(0.10);
		
		// set mutation rate
		controller.setMutationRate(0.25);
		
		// begin 'evolution' loop
		while(!controller.areEndConditionsMet()) {
			controller.createNextGeneration(Controller.BREEDING_TRUNCATION);
			controller.printCurrentPopulation();
		}
	}
}
