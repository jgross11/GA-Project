package components;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author jgross11@ycp.edu
 * 
 * Class that represents a generic specimen to be used in a GA 
 */
public class Specimen implements Comparable<Specimen>{
	private int id;
	private Trait[] traits;
	private double fitness;
	
	
	/**
	 * Inits the specimen's fitness value and traits list, and assigns the Specimen its id.
	 * @param id The Specimen's id. 
	 * @param numTraits The number of traits the Specimen will contain. 
	 */
	public Specimen(int id, int numTraits) {
		this.id = id;
		fitness = 0;
		traits = new Trait[numTraits];
		for(int i = 0; i < numTraits; i++) {
			traits[i] = null;
		}
	}
	
	/**
	 * Inits the specimen's fitness value and traits list.
	 * @param numTraits The number of traits this specimen will contain. 
	 */
	public Specimen(int numTraits) {
		this.id = -1;
		fitness = 0;
		traits = new Trait[numTraits];
		for(int i = 0; i < numTraits; i++) {
			traits[i] = null;
		}
	}
	
	/**
	 * @return the ID of this specimen
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id of the Specimen.
	 * @param id The id to assign to this Specimen
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Sets the number of traits this Specimen will contain. 
	 * @param numTraits The number of traits this Specimen will contain. 
	 */
	public void setNumTraits(int numTraits) {
		traits = new Trait[numTraits];
		for(int i = 0; i < numTraits; i++) {
			traits[i] = null;
		}
	}
	
	/**
	 * @return the number of traits this Specimen contains.
	 */
	public int getNumTraits() {
		return traits.length;
	}
	
	/**
	 * Adds a given trait at a given index in the Trait array. 
	 * @param index The index in which to store the given Trait.
	 * @param t The Trait to store. 
	 */
	public void addTraitAtIndex(int index, Trait t) {
		traits[index] = t;
	}
	
	/**
	 * @param index The index whose {@link Trait} will be returned.
	 * @return The {@link Trait} at the given index. 
	 */
	public Trait getTrait(int index) {
		return traits[index];
	}
	
	/**
	 * @return this specimen's {@link Trait} list.
	 */
	public Trait[] getTraitList(){
		return traits;
	}
	
	/**
	 * Formats information of this Specimen's {@link Trait}s in console-friendly string.
	 */
	public String toString() {
		String traitString = "";
		for(Trait t : traits) {
			traitString += t.toString() + "\n";
		}
		return "Specimen #" + id + "\n" + traitString + "Fitness: " + fitness + "\n";
	}
	
	/**
	 * Sets the fitness of this specimen.
	 * @param fitness the fitness value to set. 
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	/**
	 * @return this specimen's fitness value.
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 *  Compares the fitness of the caller and the given {@link Specimen}.
	 *  Returns 1 if caller fitness is greater than other's fitness.
	 *  Returns 0 if caller fitness is equal to other's fitness.
	 *  Returns -1 if caller fitness is less than other's fitness. 
	 * @param other The {@link Specimen} whose fitness is being compared. 
	 */
	public int compareTo(Specimen other) {
		return fitness > other.fitness ? 1 : fitness < other.fitness ? -1 : 0;
	}
	
	/**
	 * Creates a new Specimen that contains approximately 50% of this Specimen's traits and 50% of the given Specimen's traits,
	 * with some random traits being generated depending on the given mutationRate. 
	 * @param other The other Specimen whose traits will be mixed with this one in the child Specimen.
	 * @param mutationRate The rate at which a trait from a child will take a random value, rather than one of its parents. 
	 * @return A Specimen whose traits are comprised approximately 50-50 with this Specimen and the given Specimen. 
	 */
	
	// TODO: ### IMPLEMENT TRAIT MUTATION ###
	public Specimen breed(Specimen other, double mutationRate) {
		Random rand = new Random();
		// create trait index array
		Specimen child = new Specimen(traits.length);
		int numTraits = traits.length;
		// NOTE: this would change if specimen will have more than two parents
		
		// parent one count
		int p1Count = numTraits / 2;
		
		// parent two count
		int p2Count = numTraits / 2;
		
		int mutationChance = (int) (mutationRate * 100);
		
		System.out.println(mutationChance);
		
		int traitIndex = 0;
		while(p1Count > 0 && p2Count > 0) {
			int parentThatGives = rand.nextInt(2);
			
			// generate random number between 1 and 100 inclusive to determine if mutation occurs
			int mutationNumber = rand.nextInt(100)+1;
			if(parentThatGives == 0) {
				child.addTraitAtIndex(traitIndex, (mutationNumber <= mutationChance) ? new Trait(this.traits[traitIndex]) : getTrait(traitIndex));
				p1Count --;
			} 
			else {
				child.addTraitAtIndex(traitIndex, (mutationNumber <= mutationChance) ? new Trait(other.traits[traitIndex]) : other.getTrait(traitIndex));
				p2Count --;
			}
			traitIndex++;
		}
		if(p1Count == 0) {
			for(int i = traitIndex; i < numTraits; i++) {
				child.addTraitAtIndex(i, other.getTrait(i));
			}
		}
		else if(p2Count == 0) {
			for(int i = traitIndex; i < numTraits; i++) {
				child.addTraitAtIndex(i, getTrait(i));
			}
		}
		
		for(int i = 0; i < child.getNumTraits(); i++) {
			System.out.println(child.getTrait(i));
		}
		return child;
	}
}
