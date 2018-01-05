package wordFrequencyCounter;

/**
 * Isaac McAuley
 * COMP 2631
 * 
 * Class for holding the frequency and locations
 * 	in the word frequency program
 */

import java.util.ArrayList;

public class WordData {
	private String word;
	private int frequency;
	private ArrayList<String> locations = new ArrayList<String>();
	
	public WordData(String word, String location) {
		super();
		this.word = word;
		addLocation(location);
	}
	public String getWord() {
		return word;
	}
	public int getFrequency() {
		return frequency;
	}
	public ArrayList<String> getLocations() {
		return locations;
	}
	
	public void addLocation(String location) {
		locations.add(location);
		frequency++;
	}
	
	public String outputFrequency()
	{
		String output;
		output = word + ',' + frequency;
		return output;
	}
	
	public String outputLocation()
	{
		String output = "\0";

		for(String location : locations)
		{
			output += word + ',' + location + '\n';
		}
		return output;
	}
	
}
