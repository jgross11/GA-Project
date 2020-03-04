package components;

import java.util.Random;

/**
 * @author jgross11@ycp.edu
 *
 * Class that represents a specific trait that a specimen contains. 
 */
public class Trait {
	private String name;
	private double minValue;
	private double maxValue;
	private double value;
	//TODO: add separate constructor for "blueprint" traits that do not contain a value.. 
	public Trait(String name, double minValue, double maxValue) {
		this.name = name;
		this.minValue = minValue;
		this.maxValue = maxValue;
		// TODO determine way to make this exclusive to both val = min and val = max cases
		this.value = minValue + (new Random()).nextDouble()*(maxValue-minValue+0.0000001);
	}
	
	/**
	 * This constructor should typically be used for the initialization of the population,
	 * when the blueprint trait is passed to be used as a reference.
	 * @param t This should be the blueprint trait whose information will be used as a reference. 
	 */
	public Trait(Trait t) {
		this.name = t.name;
		this.minValue = t.minValue;
		this.maxValue = t.maxValue;
		this.value = minValue + (new Random()).nextDouble()*(maxValue-minValue+0.0000001);
	}
	
	/**
	 * Formats trait information in a console friendly format. 
	 */
	public String toString() {
		return "Trait name: " + name + " | minValue: " + minValue + " | maxValue: " + maxValue + " | value: " + value;
	}
	
	/**
	 * @return the numeric value of this trait. 
	 */
	public double getValue() {
		return value;
	}
}
