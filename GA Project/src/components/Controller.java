package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author jgross11@ycp.edu
 *
 * Class that represents a controller of a GA experiment 
 */


public class Controller {
	// constant that enables roulette wheel breeding
	public final static int BREEDING_ROULETTE = 0;
	
	// constant that enables tournament breeding
	public final static int BREEDING_TOURNAMENT = 1;
	
	// constant that enables truncation breeding
	public final static int BREEDING_TRUNCATION = 2;
	
	// constant that enables elitist breeding
	public final static int BREEDING_ELITIST = 3;
	private FitnessFunction func;
	private LinkedList<Specimen> allSpecimens;
	private Trait[] blueprintTraitList;
	private Specimen[] currentPopulation;
	private int numSpecimensTotal;
	private long generationCount;
	private long maxNumGenerations;
	private double fitnessThreshold;
	private double truncationConstant;
	private Specimen currentFittestSpecimen;
	private double mutationRate;
	
	/**
	 *  Controller constructor inits current population and all specimens list
	 */
	public Controller() {
		allSpecimens = new LinkedList<Specimen>();
		currentPopulation = new Specimen[0];
		blueprintTraitList = new Trait[0];
		numSpecimensTotal = 0;
		generationCount = 1;
		maxNumGenerations = Long.MAX_VALUE;
		fitnessThreshold = 1.0;
		currentFittestSpecimen = null;
		truncationConstant = 0;
		mutationRate = 0.0;
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
	 * Initializes population array with {@link Specimen} with the "blueprint" {@link Trait}'s found in the blueprintTraitList array and calculates individual species' fitness, normalized fitness, and .
	 */
	public void initializePopulation() {
		// create specimens for each index in the currentPopulation array
		for(int i = 0; i < currentPopulation.length; i++) {
			Specimen newSpecimen = new Specimen(++numSpecimensTotal, blueprintTraitList.length);
			
			// generate trait data for each specimen for each trait
			for(int j = 0; j < blueprintTraitList.length; j++) {
				newSpecimen.addTraitAtIndex(j, new Trait(blueprintTraitList[j]));
			}
			currentPopulation[i] = newSpecimen;
			allSpecimens.add(0, newSpecimen);
		}
		calculatePopulationFitnesses();
		Arrays.sort(currentPopulation);
	}
	
	/**
	 * Sets this Controller's fitness function.
	 * @param f The fitness function to use. 
	 */
	public void setFitnessFunction(FitnessFunction f) {
		func = f;
	}
	
	/**
	 * Sets the mutation rate of the experiment. 
	 * @param mr The mutation rate to set. 
	 */
	public void setMutationRate(double mr) {
		mutationRate = mr;
	}
	
	/**
	 * @return This experiment's mutation rate. 
	 */
	public double getMutationRate() {
		return mutationRate;
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
	public void calculatePopulationFitnesses() {
		for(Specimen s : currentPopulation) {
			if(s.getFitness() == 0) {
				calculateFitness(s);
			}
		}
	}
	
	/**
	 * Calculates the fitness of a given {@link Specimen}
	 * @param s the {@link Specimen} whose fitness will be calculated.
	 */
	public void calculateFitness(Specimen s) {
		s.setFitness(func.calculateFitness(s));
	}
	
	/**
	 * @return The current most fit {@link Specimen} NOTE: does not calculate fittest, call {@link #findFittestSpecimen() to determine the fittest {@link Specimen}.}
	 */
	public Specimen getFittestSpecimen() {
		return currentFittestSpecimen;
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
		currentFittestSpecimen = currentPopulation[fittestIndex];
		return currentFittestSpecimen;
	}
	
	/**
	 * Prints the information of every {@link Specimen} in the current population to console.
	 */
	public void printCurrentPopulation() {
		System.out.println("### BEGIN PRINTING OF CURRENT POPULATION AT GENERATION " + generationCount + " ###\n");
		for(Specimen s : currentPopulation) {
			System.out.println(s.toString());
		}
		System.out.println("### END PRINTING OF CURRENT POPULATION AT GENERATION " + generationCount + " ###\n");
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

	/**
	 * Sets the maximum number of generations the experiment will run.
	 * @param maxGens the maximum number of generations to run.
	 */
	public void setMaxGenerationsCount(long maxGens) {
		maxNumGenerations = maxGens;
	}

	/**
	 * Sets the minimum fitness value a solution must reach before the experiment ends.
	 * @param fitThresh The minimum fitness value a successful solution must have. 
	 */
	public void setFitnessThreshold(double fitThresh) {
		fitnessThreshold = fitThresh;
	}
	
	/**
	 * Used to set the truncation constant that {@link #BREEDING_TRUNCATION} will use to determine 
	 * what percentage of the population will be used to breed the next generation. 
	 * @param trunConst The truncation constant to set. 
	 */
	public void setTruncationConstant(double trunConst) {
		truncationConstant = trunConst;
	}

	/**
	 * Determines if the experiment should end based on maximum fitness achieved or the number of generations ran. 
	 * @return True if at least one end condition is met. 
	 */
	public boolean areEndConditionsMet() {
		// end experiment when current fittest specimen's fitness exceeds fitness threshold or max # of generations have been generated
		return ((currentFittestSpecimen != null && currentFittestSpecimen.getFitness() >= fitnessThreshold) || generationCount >= maxNumGenerations);
	}

	/**
	 * Generates the next generation based off the current array using a given breeding method. 
	 * @param breedingMethod The method of breeding used to create the next generation. 
	 */
	public void createNextGeneration(int breedingMethod) {
		// create array that holds new generation
		Specimen[] newGeneration = new Specimen[currentPopulation.length];
		Random rand = new Random();
		
		// populate next generation depending on desired breeding method
		switch(breedingMethod) {
			case BREEDING_ROULETTE:
				
			break;
			
			case BREEDING_TOURNAMENT:
				
			break;
			
			case BREEDING_TRUNCATION: 
				if(truncationConstant <= 0 || truncationConstant > 1) {
					System.err.println(" ### WARNING: CONDITION 0 <= truncationConstant < 1 NOT MET: MUST USE setTruncationConstant() BEFORE BREEDING WITH THIS METHOD");
					newGeneration = currentPopulation;
					break;
				}
				
				else {
					// calculate lowest usable index
					// TODO think about changing sort s.t. pop[0] contains most fit and pop[end] contains least fit specimen
					int lowestIndex = (int) Math.round(currentPopulation.length * truncationConstant);
					
					printCurrentPopulation();
					for(int i = 0; i < currentPopulation.length; i++) {
						int p1Index = rand.nextInt(lowestIndex);
						int p2Index = rand.nextInt(lowestIndex);
						
						// fix case where p1 == p2, occurs with probability 1 / lowestIndex
						if(p2Index == p1Index) {
							p2Index = (p2Index + 1 + rand.nextInt(lowestIndex)) % lowestIndex;
						}
						
						// breed p1 Specimen with p2 Specimen and store in next generation
						Specimen child = currentPopulation[currentPopulation.length - p1Index - 1].breed(currentPopulation[currentPopulation.length - p2Index - 1], mutationRate);
						child.setId(++numSpecimensTotal);
						calculateFitness(child);
						System.out.println("### BREEDING ### \n" + currentPopulation[p1Index].toString() + "\n" + 
						"bred with \n" + currentPopulation[p2Index].toString() + "\n" +
						"yields\n" + child.toString() + "\n### END BREEDING ### \n");
						newGeneration[i] = child;
					}
				}
			break;
			
			case BREEDING_ELITIST:
				
			break;
			
		}
		
		// add next generation to allSpecimens list
		for(Specimen s : newGeneration) {
			allSpecimens.add(0, s);
		}
		
		// sort next gen by fitness
		Arrays.sort(newGeneration);
		
		// reassign most fit individual if necessary
		if(newGeneration[newGeneration.length - 1].getFitness() > currentFittestSpecimen.getFitness()) {
			currentFittestSpecimen = newGeneration[newGeneration.length - 1];
		}
		
		// make next generation the current population
		currentPopulation = newGeneration;
		
		// increment generation count
		generationCount++;
	}
}
