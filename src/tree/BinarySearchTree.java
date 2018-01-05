package tree;
/**
 * Basic Generic Binary Search Tree class.
 * 
 * Modified by Isaac McAuley
 * 
 *  A Binary Search Tree is a node-based binary tree data structure which has the following properties:
 *         (1) The left subtree of a node contains only nodes with data that is less than the current node.
 *         (2) The right subtree of a node contains only nodes with data that is greater than the current node's.
 *         (3) The left and right subtree each must also be a binary search trees.
 *         (4) There must be no duplicate nodes.
 *         
 * @see http://en.wikipedia.org/wiki/Binary_search_tree
 * 
 * @author JKidney
 * @version 1 
 * 
 *      Created: Oct 17, 2013
 * Last Updated: Oct 17, 2013 - creation (jkidney)
 *               Oct 23, 2014 - updated to new version
 */
import java.util.Comparator;

import tree.treeIterators.*;


/*
 *  keytype = the type of the key that is used to store and retrieve data in the binary search tree
 * datatype = the type of the data being stored in the binary search tree
 */
public class BinarySearchTree<keytype, datatype>  
{
	private BinaryNode<keytype, datatype> root; // will always keep a reference to the root node of the tree
	private Comparator<keytype> comparator; // used to compare keys when adding and searching
	private int size;
	
	/**
	 * Sets up the basics of the binary search tree
	 * @param comparator used to compare keys when adding and search the tree
	 */
	public BinarySearchTree(Comparator<keytype> comparator)
	{
		root = null;
		this.comparator = comparator;
		size = 0;
	}

	/**
	 * gives back the size of the list
	 * @return the number of nodes in the tree
	 */
	public int getSize() 
	{ 
		return size;
	}


	//CONSTANTS used when selecting a specific Traversal iterator 
	public static final int   PRE_TRAV  = 0; // PreOrder traversal
	public static final int    IN_TRAV  = 1; // InOrder traversal
	public static final int  POST_TRAV  = 2; // PostOrder traversal
	public static final int LEVEL_TRAV  = 3; // LevelOrder traversal


	//constants used for the print
	private static final int PRINT_NODE = 0; // print out the entire node
	private static final int PRINT_KEY = 1; // print out just the key
	private static final int PRINT_DATA = 2; // print out just the data
	
	private static final char ROOT = 'T';
	private static final char LEFT = 'L';
	private static final char RIGHT = 'R';

	/**
	 * Creates an iterator that can be used to do a specific traversal of a tree
	 * based upon the given type. Defaults to Level order if incorrect value is given
	 * @param type must be one of the constants provided by the class (PRE_TRAV,IN_TRAV,POST_TRAV,LEVEL_TRAV)
	 * @return the desired traversal iterator
	 */
	public BinaryTreeIterator<keytype, datatype> getTraversalIterator(int type)
	{
		//DO NOT CHANGE THIS METHOD
		BinaryTreeIterator<keytype, datatype> iter = null;

		switch(type)
		{
		case  PRE_TRAV: iter = new PreOrderTreeIterator<keytype, datatype>(root); break;
		case   IN_TRAV: iter = new InOrderTreeIterator<keytype, datatype>(root); break;
		case POST_TRAV: iter = new PostOrderTreeIterator<keytype, datatype>(root); break;
		default:        iter = new LevelOrderTreeIterator<keytype, datatype>(root); break;  
		}

		return iter;
	}

	/**
	 * Adds the new data element to the binary search tree
	 *  
	 * Insertion begins as a search would begin (see the find method); if the key is not 
	 * equal to that of the root's key, we search the left or right subtrees as before. 
	 * Eventually, we will reach an external node and add the data as its right or left child, 
	 * depending on the node's key. In other words, we examine the root and recursively 
	 * (or iteratively) insert the new node to the left subtree if its key is less than 
	 * that of the root's key, or the right subtree if its key is greater than the root's key.
	 * if the key matches any keys in the tree we do not add it.
	 * 
	 * @see http://en.wikipedia.org/wiki/Binary_search_tree#Insertion
	 * @param key the key to use for comparisons on where to store the data in the tree
	 * @param data the new data to try to store in the tree
	 */
	public void add(keytype key, datatype data)
	{
		BinaryNode<keytype, datatype> node = new BinaryNode<keytype, datatype>(key, data);
		
		if(size == 0)
		{
			root = node;
		}
		else
			addAtNode(root, node);
		size++;
	}

	private void addAtNode(BinaryNode<keytype, datatype> root, BinaryNode<keytype, datatype> node)
	{
		if(comparator.compare(root.getKey(), node.getKey()) == 1)
		{
			if(!root.hasLeftChild())
				root.setLeftChild(node);
			else
				addAtNode(root.getLeftChild(), node);
		}
		else
		{
			if(!root.hasRightChild())
				root.setRightChild(node);
			else
				addAtNode(root.getRightChild(), node);
		}
	}	
	
	/**
	 * Determines if their is a node in the tree with the given key or not
	 * @param key the key to search for
	 * @return true if a node does exists , false otherwise
	 */
	public boolean containskey(keytype key)
	{
		boolean doesContain = false;
		BinaryTreeIterator<keytype, datatype> iterator = getTraversalIterator(LEVEL_TRAV);
		while(iterator.hasNext() && !doesContain)
		{
			if(comparator.compare(iterator.getCurrentKey(), key) == 0)
				doesContain = true;
			else
				iterator.next();
		}
		return doesContain;
	}

	/**
	 * Searches the tree for the given key, returns the fata from the first node that it finds
	 * with a matching key or null if not found.
	 *  
	 * We begin by examining the root node. If the tree is null, the data we are searching 
	 * for does not exist in the tree. Otherwise, if the data equals that of the root, the 
	 * search is successful. If the data is less than the root, search the left subtree. 
	 * Similarly, if it is greater than the root, search the right subtree. This process is 
	 * repeated until the data is found or the remaining subtree is null. If the searched data 
	 * is not found before a null subtree is reached, then the item must not be present in the tree.
	 * 
	 * @see http://en.wikipedia.org/wiki/Binary_search_tree#Searching
	 * @param key the key to use for comparisons on where to search for the data in the tree
	 * @return the found data object or null if it was not found in the tree
	 */
	public datatype find(keytype key)
	{
		boolean found = false;
		
		BinaryTreeIterator<keytype, datatype> iterator = getTraversalIterator(LEVEL_TRAV);
		while(iterator.hasNext() && !found)
		{
			if(comparator.compare(iterator.getCurrentKey(), key) == 0)
				return iterator.getCurrentData();
			else
				iterator.next();
		}
		
		return null;
	}


	/**
	 * Removes a specific node from the tree that has a matching key value
	 * 
	 *  There are five possible cases to consider:
	 *      d(1) the tree is empty ( root == null )
	 *      d(2) only one node in the tree ( root = null )
	 *      (3) Deleting a leaf (node with no children): 
	 *                Deleting a leaf is easy, as we can simply remove it from the tree.
	 *      (4) Deleting a node with one child: 
	 *                Remove the node and replace it with its child.
	 *      (5) Deleting a node with two children: 
	 *                Call the node to be deleted N. Do not delete N. Instead, choose its 
	 *                in-order successor (R) . Replace the value of N with the value of R, then delete R.
	 *
	 *       As with all binary trees, a node's in-order successor is the left-most child of its right subtree.
	 * 
	 * @see http://en.wikipedia.org/wiki/Binary_search_tree#Deletion
	 * 
	 * @param key the key to use for the search
	 * @return the data at the node or null if no such node found
	 */
	public datatype remove(keytype key)
	{
		datatype data;
		BinaryTreeIterator<keytype, datatype> iterator = getTraversalIterator(LEVEL_TRAV);
		boolean found = false;
		
		if(size == 0)
		{
			return null;
		}
		else if(size == 1)
		{
			data = root.getData();
			root = null;
			size--;
			return data;
		}
		else
		{
			while(iterator.hasNext() && !found)
			{
				if(comparator.compare(iterator.getCurrentKey(), key) == 0)
				{
					found = true;
				}
				else
					iterator.next();
			}
			
			if(found)
			{
				data = iterator.getCurrentData();
				removeNode(iterator.getCurrentNode());
				size--;
				return data;
			}
		}
		return null;
	}
	
	private void removeNode(BinaryNode<keytype, datatype> node)
	{		
		BinaryNode<keytype, datatype> parent = null;
		
		char relationToParent;
		
		
		if(node == root)
			relationToParent = ROOT;
		else
		{
			parent = node.getParent();
			if(parent.getLeftChild() == node)
				relationToParent = LEFT;
			else
				relationToParent = RIGHT;
		}
		
		
		if(!node.hasLeftChild() && !node.hasRightChild())   //is a leaf
		{
			switch(relationToParent) {
			case(ROOT):
				root = null;
				break;
			case(LEFT):
				parent.setLeftChild(null);
				break;
			case(RIGHT):
				parent.setRightChild(null);
				break;
			}
		}
		else if(node.hasLeftChild() && !node.hasRightChild())
		{
			switch(relationToParent) {
			case(ROOT):
				node.getLeftChild().setParent(null);
				root = node.getLeftChild();
				break;
			case(LEFT):
				node.getLeftChild().setParent(parent);
				parent.setLeftChild(node.getLeftChild());
				break;
			case(RIGHT):
				node.getLeftChild().setParent(parent);
				parent.setRightChild(node.getLeftChild());
				break;
			}
		}
		else if(!node.hasLeftChild() && node.hasRightChild())
		{
			switch(relationToParent) {
			case(ROOT):
				node.getRightChild().setParent(null);
				root = node.getRightChild();
				break;
			case(LEFT):
				node.getRightChild().setParent(parent);
				parent.setLeftChild(node.getRightChild());
				break;
			case(RIGHT):
				node.getRightChild().setParent(parent);
				parent.setRightChild(node.getRightChild());
				break;
			}
		}
		else
		{
			BinaryNode<keytype, datatype> minimum = getMinimum(node.getRightChild());
			removeNode(minimum);
			
			switch(relationToParent) {
			case(ROOT):
				root = minimum;
				break;
			case(LEFT):
				parent.setLeftChild(minimum);
				minimum.setLeftChild(parent);
				break;
			case(RIGHT):
				parent.setRightChild(minimum);
				minimum.setLeftChild(parent);
				break;
			
			}
			
			if(node.hasLeftChild())
			{
				minimum.setLeftChild(node.getLeftChild());
				node.getLeftChild().setParent(minimum);
			}
			
			if(node.hasRightChild())
			{
				minimum.setRightChild(node.getRightChild());
				node.getRightChild().setParent(minimum);
			}
		}
		
		node = null;
	}
	
	private BinaryNode<keytype, datatype> getMinimum(BinaryNode<keytype, datatype> node)
	{
		BinaryNode<keytype, datatype> minimum = node;
		while(minimum.hasLeftChild())
		{
			minimum = minimum.getLeftChild();
		}
		return minimum;
	}
	
	/**
	 * Properly removes all nodes from the tree. You must remove each node in the tree individual 
	 * to properly unlink everything You are allowed to use a traversal in this method. Try to 
	 * remove the leaf nodes first and end with the root node
	 */
	public void clear()
	{
		BinaryTreeIterator<keytype, datatype> iterator = getTraversalIterator(IN_TRAV);
		
		if(size == 1 || size == 0)
		{
			root = null;	
		}
		else
		{
			while(iterator.hasNext())
			{
				BinaryNode<keytype, datatype> node = iterator.getCurrentNode();
				iterator.next();
				remove(node.getKey());
			}
		}
		size = 0;
	}

	/**
	 * prints the tree nodes to the console/standard output using the specified traversal
	 * @param type must be one of the constants provided by the class (PRE_TRAV,IN_TRAV,POST_TRAV,LEVEL_TRAV)
	 */
	public void printAllNodes(int type)
	{
		print(type, PRINT_NODE);
	}
	/**
	 * prints the just the key from each node to the console/standard output using the specified traversal
	 * @param type must be one of the constants provided by the class (PRE_TRAV,IN_TRAV,POST_TRAV,LEVEL_TRAV)
	 */
	public void printJustKeys(int type)
	{
		print(type, PRINT_KEY);
	}

	/**
	 * prints the just the data values from each node to the console/standard output using the specified traversal
	 * @param type must be one of the constants provided by the class (PRE_TRAV,IN_TRAV,POST_TRAV,LEVEL_TRAV)
	 */
	public void printJustData(int type)
	{
		print(type, PRINT_DATA);
	}

	public void setData(keytype key, datatype data)
	{
		boolean found = false;
		
		BinaryTreeIterator<keytype, datatype> iterator = getTraversalIterator(LEVEL_TRAV);
		while(iterator.hasNext() && !found)
		{
			if(comparator.compare(iterator.getCurrentKey(), key) == 0)
			{
				iterator.getCurrentNode().setData(data);
				found = true;
			}
			else
				iterator.next();
		}
		
	}
	
	/**
	 * Helper method used to print out the tree with the given traversal
	 * @param type the type of traversal
	 * @param valueType the type of data output
	 */
	private void print(int type, int valueType)
	{
		BinaryTreeIterator<keytype, datatype> iter = getTraversalIterator(type);
		System.out.print("TREE[ ");
		while(iter.hasNext())
		{
			BinaryNode<keytype, datatype> node = iter.getCurrentNode();
			String dataOutPut = "";
			switch(valueType)
			{
			case PRINT_NODE:  dataOutPut += node; break;
			case PRINT_DATA:  dataOutPut = ""+node.getData(); break;
			default:  dataOutPut = ""+node.getKey(); break;
			}

			System.out.print("("+dataOutPut+")");

			iter.next();

			if(iter.hasNext())
				System.out.print(" , ");
		}
		System.out.println(" ]");

	}

}
