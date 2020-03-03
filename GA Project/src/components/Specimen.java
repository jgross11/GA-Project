package components;

import java.util.ArrayList;

/**
 * @author jgross11@ycp.edu
 * 
 * Class that represents a generic specimen to be used in a GA 
 */
public class Specimen {
	private String name;
	private ArrayList<Trait> traits;
	public Specimen(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
