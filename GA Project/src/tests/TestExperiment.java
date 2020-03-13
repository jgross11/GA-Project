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
 * simple test experiment inspired by "Genetic Algorithm for Solving Simple Mathematical Equality Problem"
 * Denny Hermawanto - https://arxiv.org/ftp/arxiv/papers/1308/1308.4675.pdf that finds a close solution to 
 * a + 2b + 3c = 12 where -12 < a, b, c < 12. 
 */
public class TestExperiment {
	
	public static void main(String[] args) {
		
		// create controller to run experiment
		Controller controller = new Controller();
		
		// set population size to 100 specimen
		controller.setPopulationSize(100);
		
		// define and set controller fitness function - here, maximum fitness occurs when a + 2b + 3c - 12 = 0
		controller.setFitnessFunction(new FitnessFunction() {
			public double calculateFitness(Specimen s) {
				// TODO: add shortcut method to Trait class to retrieve value of trait at given index
				double val = Math.abs(s.getTrait(0).getValue() + 2*s.getTrait(1).getValue() + 3*s.getTrait(2).getValue() - 12);
				return 1.0 / (1.0 + val);
			}
		});
		
		// create trait array containing blueprint trait data
		// in this case, we have 3 traits: a-value, b-value, and c-value.
		// TODO: eliminate wasted generation of values for blueprint traits
		
		controller.setNumTraits(3);
		controller.setTrait(0, new Trait("a-val", -12, 12));
		controller.setTrait(1, new Trait("b-val", -12, 12));
		controller.setTrait(2, new Trait("c-val", -12, 12));
		
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
		controller.setMutationRate(0.15);
		
		// begin 'evolution' loop
		while(!controller.areEndConditionsMet()) {
			controller.createNextGeneration(Controller.BREEDING_TRUNCATION);
			controller.printCurrentPopulation();
		}
	}
}
