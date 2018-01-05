package wordFrequencyCounter;

/**
 * Isaac McAuley
 * COMP 2631
 * 
 * Word Frequency Counter
 * Takes in an input file, minimum word length, minimum frequency
 * Exports two CSV files, one for word frequency, one for location
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.nodes.Document;
import tree.BinarySearchTree;
import comparisonObjects.StringNonCaseSensitiveCompare;
import userCommunication.*;

public class WordFrequencyCounterProgram {

	public static void main(String[] args) {		
			StringNonCaseSensitiveCompare cmp = new StringNonCaseSensitiveCompare();
			BinarySearchTree<String, WordData> tree = new BinarySearchTree<String, WordData>(cmp);
			
			FileHandeler file = new FileHandeler();
			ConsoleInteraction inter = new ConsoleInteraction();			
			
			String inputFile = inter.getInput_String("Enter input file: ");
			int minimumLength = inter.getInput_Int("Enter minimum word lenngth: ");
			int minimumFrequency = inter.getInput_Int("Enter minimum frequency: ");
			String outputFile = inter.getInput_String("Enter output filename (no .csv): ");
			
			try {
				Scanner scan = new Scanner(new File(inputFile));
				Document doc;
			  	ArrayList<String> text = new ArrayList<String>();
			  	String location = new String();
			  	boolean isLocal;
				
				while(scan.hasNextLine())
				{
					if(scan.findInLine(".").charAt(0) == 'F')
						isLocal = true;
					else 
						isLocal = false;
					
					location = scan.nextLine();
					location = location.substring(1);
					
					if(isLocal)
						doc = file.openLocal(location);
					else
						doc = file.openWeb(location);
					
					text = file.parseDoc(doc, minimumLength);
					
					for(String word : text)
				  	{
				  		if(tree.containskey(word))
				  		{
				  			WordData data = tree.find(word);
				  			data.addLocation(location);
				  		}
				  		else
				  		{
				  			WordData data = new WordData(word, location);
				  			tree.add(word, data);
				  		}
				  			
				  	}
					
				}
				
				file.exportFiles(tree, outputFile, minimumFrequency);
			  	
			  	scan.close();
			} 
			catch (FileNotFoundException e) 
			{
				inter.print("Error: file does not exist");
			}
		  	
			

		  	
		  
	}

}
