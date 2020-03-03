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
	private Specimen[] currentPopulation;
	
	/**
	 *  Controller constructor inits current population and all specimens list
	 */
	public Controller() {
		allSpecimens = new ArrayList<Specimen>();
		currentPopulation = new Specimen[0];
	}
	
	/**
	 * Function that sets the size of the experiment's population.
	 * NOTE: Reinits current population array, so only call before experiment begins.
	 * @param size The desired population size.
	 */
	public void setPopulationSize(int size){
		currentPopulation = new Specimen[size];
	}
	
	
}
