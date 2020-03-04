package components;

import java.util.ArrayList;

/**
 * @author jgross11@ycp.edu
 * 
 * Class that represents a generic specimen to be used in a GA 
 */
public class Specimen implements Comparable<Specimen>{
	private int id;
	private ArrayList<Trait> traits;
	private double fitness;
	
	/**
	 * Inits the specimen's fitness value and traits list.
	 * @param id The ID used to identify this specimen.
	 */
	public Specimen(int id) {
		this.id = id;
		fitness = 0;
		traits = new ArrayList<Trait>();
	}
	
	/**
	 * 
	 * @return the ID of this specimen
	 */
	public int getName() {
		return id;
	}
	
	/**
	 * Adds a specific {@link Trait} to this specimen's trait list
	 * @param t The {@link Trait} to add. 
	 */
	public void addTrait(Trait t) {
		traits.add(t);
	}
	
	/**
	 * @param index The index whose {@link Trait} will be returned.
	 * @return The {@link Trait} at the given index. 
	 */
	public Trait getTrait(int index) {
		return traits.get(index);
	}
	
	/**
	 * @return this specimen's {@link Trait} list.
	 */
	public ArrayList<Trait> getTraitList(){
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
}
