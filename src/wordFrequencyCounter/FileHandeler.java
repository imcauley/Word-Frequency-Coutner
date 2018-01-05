package wordFrequencyCounter;

/**
 * Isaac McAuley
 * COMP 2631
 * 
 * Various function for dealing with files and text
 * 	for the word frequency program
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import tree.BinarySearchTree;
import tree.treeIterators.BinaryTreeIterator;

public class FileHandeler
{   
	public Document openWeb(String url)
	{
		try
		{
			return Jsoup.connect(url).get();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return new Document(url);
		}
	}

	public Document openLocal(String url)
	{
		File input = new File(url);
		try 
		{
			return Jsoup.parse(input, "UTF-8", "");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return new Document(url);
		}
	}

	public ArrayList<String> parseDoc(Document doc, int minimumLength)
	{
		Element body = doc.body();
		ArrayList<String> text = new ArrayList<String>();
		getText(body, text, minimumLength);

		return text;
	}

	private void getText(Element root, ArrayList<String> words, int minimumLength)
	{
		if(root.hasText())
		{
			String[] text = root.text().replaceAll("[^a-z\\sA-Z\\s0-9]","").split(" ");
			for(String word : text)
			{
				if(!word.isEmpty() && word.length() >= minimumLength)
				{
					words.add(word.toLowerCase());
				}
			}
		}
		else
		{
			for(Element child : root.children())
			{
				getText(child, words, minimumLength);
			}
		}
	}
	
	

	
	public void exportFiles(BinarySearchTree<String, WordData> tree, String filename, int minimum)
	{
		BinaryTreeIterator<String, WordData> iterator = tree.getTraversalIterator(1);
		
		try
        {
            PrintStream foutput = new PrintStream(new FileOutputStream(filename + "Frequency.csv"));
            PrintStream loutput = new PrintStream(new FileOutputStream(filename + "Locations.csv"));
            
            while(iterator.hasNext())
    			{	
            		if(iterator.getCurrentData().getFrequency() >= minimum)
            		{
        				foutput.println(iterator.getCurrentData().outputFrequency());
        				loutput.println(iterator.getCurrentData().outputLocation());
            		}
    				
    				iterator.next();
    			}
            foutput.close();
            loutput.close();
        }
        catch(Exception ex) 
        { 
           
        }
	}
	
}
