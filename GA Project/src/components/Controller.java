package components;

import java.util.ArrayList;

/**
 * @author jgross11@ycp.edu
 *
 * Class that represents a controller of a GA experiment 
 */
public class Controller {
	private FitnessFunction func;
	private ArrayList<Specimen> allSpecimens;
	private Trait[] blueprintTraitList;
	private Specimen[] currentPopulation;
	private int numSpecimensTotal;
	
	/**
	 *  Controller constructor inits current population and all specimens list
	 */
	public Controller() {
		allSpecimens = new ArrayList<Specimen>();
		currentPopulation = new Specimen[0];
		blueprintTraitList = new Trait[0];
		numSpecimensTotal = 0;
	}
	
	/**
	 * Function that sets the size of the experiment's population.
	 * NOTE: Reinits current population array, so only call before experiment begins.
	 * @param size The desired population size.
	 */
	public void setPopulationSize(int size){
		currentPopulation = new Specimen[size];
	}
	
	/**
	 * @return the current population array.
	 */
	public Specimen[] getPopulation() {
		return currentPopulation;
	}
	
	/**
	 * Initializes population array with {@link Specimen} with the "blueprint" {@link Trait}'s found in the blueprintTraitList array.
	 */
	public void initializePopulation() {
		// create specimens for each index in the currentPopulation array
		for(int i = 0; i < currentPopulation.length; i++) {
			Specimen newSpecimen = new Specimen(++numSpecimensTotal);
			
			// generate trait data for each specimen for each trait
			for(int j = 0; j < blueprintTraitList.length; j++) {
				newSpecimen.addTrait(new Trait(blueprintTraitList[j]));
			}
			currentPopulation[i] = newSpecimen;
			allSpecimens.add(newSpecimen);
		}
	}
	
	/**
	 * Sets this Controller's fitness function.
	 * @param f The fitness function to use. 
	 */
	public void setFitnessFunction(FitnessFunction f) {
		func = f;
	}
	
	/**
	 * Sets the number of traits a {@link Specimen} in this experiment has
	 * @param numTraits the desired number of {@link Trait}s.
	 */
	public void setNumTraits(int numTraits) {
		blueprintTraitList = new Trait[numTraits];
	}
	
	/**
	 * Adds a specific {@link Trait} to the blueprint trait array.
	 * @param index The index at which to add the trait.
	 * @param t The trait to add. 
	 */
	public void setTrait(int index, Trait t) {
		blueprintTraitList[index] = t;
	}
	
	/**
	 * Calculates the fitness of every {@link Specimen} in the population array by using the appropriate {@link FitnessFunction}.
	 */
	public void calculateFitnesses() {
		for(Specimen s : currentPopulation) {
			if(s.getFitness() == 0) {
				s.setFitness(func.calculateFitness(s));
			}
		}
	}
	
	/**
	 * Searches the current population to find the {@link Specimen} whose fitness is closest to 1. 
	 * @return The {@link Specimen} whose fitness is closest to 1. 
	 */
	public Specimen findFittestSpecimen() {
		double fittest = currentPopulation[0].getFitness();
		int fittestIndex = 0;
		for(int i = 0; i < currentPopulation.length; i++) {
			double currentFitness = currentPopulation[i].getFitness();
			if(currentFitness > fittest) {
				fittest = currentFitness;
				fittestIndex = i;
			}
		}
		return currentPopulation[fittestIndex];
	}
	
	/**
	 * Prints the information of every {@link Specimen} in the current population to console.
	 */
	public void printCurrentPopulation() {
		System.out.println("### BEGIN PRINTING OF CURRENT POPULATION ###");
		for(Specimen s : currentPopulation) {
			System.out.println(s.toString());
		}
		System.out.println("### END PRINTING OF CURRENT POPULATION ###");
	}
	
	/**
	 * Prints the information of every {@link Specimen} created in the experiment to console.
	 */
	public void printAllSpecimens() {
		System.out.println("### BEGIN PRINTING OF ALL SPECIMENS ###");
		for(Specimen s : allSpecimens) {
			System.out.println(s.toString());
		}
		System.out.println("### END PRINTING OF ALL SPECIMENS ###");
	}
}
