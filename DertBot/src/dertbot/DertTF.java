package dertbot;

public class DertTF {
 /**
  * @author Xilats Dert
  * @returns A transformation object to be placed in chat.
  * @params A species, a gender, and a description
  */

	private String species;
	private String gender;
	private String description;
	private String array;
	
	
	/*
	 * TODO: 
	 * 		Create a better locking method so we don't need to use a timer to keep users from flooding IRC with TFs.
	 */

	//This constructor forms a TF object: It has a species, gender, a description, and is told which array it belongs to.
	public DertTF(String s, String g, String d, String m){
		
		species = s;
		gender = g;
		description = d;
		array = m;
			
	}
	
	//return the contents of the tf, for use in a description.
	public String getSpecies(){
		return species;
	}


	public String getGender(){
		return gender;
	}


	public String getDescription(){
		return description;
	}
	
	public String getArray(){
		return array;
	}
	
	//get the TF values, and return a DertTf object
	public static DertTF getTF(String line){
		//Splitter value for our CaratSV(Carat Seperated Value)
		//each time we hit the '^' sign in the given lines, it is split into strings.
		String splitter = "\\^";
		
		//Splitter that parses the read in line in the main class, and then chops it up.
		//The values are then assigned, and then returned.
		String[] splitted = line.split(splitter);
		
		String species = splitted[0];
	
		String gender = splitted[1];
		
		String array = splitted[2];
		
		String description = splitted[3];
		
		///Then, we return a new DertTF
		return new DertTF(species, gender, description, array);
	}
	
	//Overridden toString method - used for diagnostic purposes to ensure it is actually loading people.
	@Override
	public String toString() {
		return species + " of gender " + gender + " selected";
	}	
}