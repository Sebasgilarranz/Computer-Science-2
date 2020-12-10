// Sebastian Gilarranz
// COP 3503, Spring 2019
// Se171788
// Assignment 4, SkipList

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.Math;

class Node<T>
{
	T data;
	int height = 0;
	ArrayList<Node<T>> next = new ArrayList<>(); // next pointers.
	
	// Head node constructor.
	Node(int height)
	{
		this.height = height;
		
		// create null pointers.
		for (int i = 0; i < height; i++)
		{
			next.add(null);
		}
	}

	// Constructor for Normal Nodes.
	Node(T data, int height)
	{
		this.height = height;
		this.data = data;

		// create null pointers.
		for (int i = 0; i < height; i++)
		{
			next.add(null);
		}

	}

	// returns data value.
	public T value()
	{
		return this.data;
	}

	// returns current height of the node.
	public int height()
	{
		return this.height;
	}

	// returns the next node in the certain level.
	public Node<T> next(int level)
	{
		if (level > height - 1)
			return null;

		return next.get(level);
	}

	// sets the next level's node.
	public void setNext(int level, Node<T> node)
	{
		next.set(level, node);
	}

	// grows the node by 1.
	public void grow()
	{
		next.add(null);
		this.height++;
	}

	// will possibly grow the node, maybe not.
	public int maybeGrow()
	{
		int n = (int)((Math.random() * 2) + 1);

		if (n == 1)
			grow();

		return n;
	}

	// trim the node by one level.
	public void trim(int height)
	{
		while (this.height > height)
		{
			next.remove(this.height - 1);
			this.height--;
		}
	}
}

class SkipList<T extends Comparable<T>>
{
	Node<T> head;
	int numNodes = 0;
	int currHeight = 0;
	
	// Empty SkipList constructor.
	SkipList()
	{
		this.head = new Node<T>(1);
		currHeight = this.head.height;
	}

	// Creates SkipList with given height.
	SkipList(int height)
	{
		this.head = new Node<T>(height);
		currHeight = this.head.height;
	}

	// Returns the current size of the skipList
	public int size()
	{	
		return this.numNodes;
	}

	// Returns the current height of the skipList
	public int height()
	{
		return this.currHeight;
	}

	// Returns the head of the SkipList
	public Node<T> head()
	{
		return this.head;
	}

	// Insert a node with a random height.
	public void insert(T data)
	{
		int height;

		// If current height is creater than maxHeight of nodes, then use current
		// to generate random number.
		if (currHeight > getMaxHeight(numNodes + 1))
		{
			insert(data, generateRandomHeight(currHeight));

		}

		else 
		{
			insert(data, generateRandomHeight(getMaxHeight(numNodes + 1)));
		}
	}

	public void insert(T data, int height)
	{
		Node<T> buff = head;
		Node<T> holder;
		numNodes++;
		int trackHeight;
		Node<T> newNode = new Node<T>(data, height);

		// If head is smaller than the newNode height grow head.
		if (head.height() < newNode.height())
		{
			while (head.height() < newNode.height())
			{
				head.grow();
				currHeight++;
			}
		}
		
		// If getMax of numNodes is greater than currHeight, must grow skipList.
		else if (getMaxHeight(numNodes) > currHeight)
		{
			growSkipList();
		}

		// This is how we track what level we are on.
		trackHeight = currHeight - 1; 

		// While we don't hit the bottom level.
		while (trackHeight >= 0)
		{
			
			// If next is null.
			if (buff.next.get(trackHeight) == null)
			{
				// If we are currently on first level.
				if (trackHeight == 0)
				{
					buff.next.set(trackHeight, newNode); // setNext to the newNode.
				}
				
				// If we are on a higher level.
				else if (trackHeight > 0)
				{
					// We will make the next point the the newNode.
					if (trackHeight < newNode.height())
					{
						holder = buff.next.get(trackHeight);
						buff.next.set(trackHeight, newNode);
						newNode.next.set(trackHeight, holder);
					}
				}
				trackHeight--;
			}

			// If the next node's data is less than data we want to insert...
			else if (buff.next.get(trackHeight).data.compareTo(data) < 0)
			{
				// Move on the next node in level.
				buff = buff.next.get(trackHeight);
			}

			// If the next node's data is greater than or equal to what we want to insert...
			else if (buff.next.get(trackHeight).data.compareTo(data) >= 0)
			{
				// If trackHeight is 0, we update the next pointer to point to the newNode.
				if (trackHeight == 0)
				{
					holder = buff.next.get(trackHeight);
					buff.next.set(trackHeight, newNode);
					newNode.next.set(trackHeight, holder);
				}
				
				// If trackHeight is greater than 0...
				else if (trackHeight > 0)
				{
					// If trackHeight is less than the newNodes height then, we update the next pointer to point
					// to the newNode.
					if (trackHeight < newNode.height())
					{
						holder = buff.next.get(trackHeight);
						buff.next.set(trackHeight, newNode);
						newNode.next.set(trackHeight, holder);
					}

				}

				trackHeight--;
			}
		}
	}
	
	public void delete(T data)
	{
		Node<T> buff = head;
		int trackHeight = currHeight - 1;
		Node<T> holder;
		Node<T> theOne = get(data);

		// Loop through all levels.
		while (trackHeight >= 0)
		{
			// If next is null...
			if (buff.next.get(trackHeight) == null)
			{
				// If trackHeight is zero...
				if (trackHeight == 0)
				{
					// return we did not find a match.
					return;
				}
				
				// If trackHeight is greater than zero
				else if (trackHeight > 0)
				{ 	
					// Go down a level.
					trackHeight--;
				}
			}

			// If next's data is less than data, go to next node in level.
			else if (buff.next.get(trackHeight).data.compareTo(data) < 0)
			{
				buff = buff.next.get(trackHeight);
			}

			// If nexts data is greater than data...
			else if (buff.next.get(trackHeight).data.compareTo(data) > 0)
			{
				// If trackHeight is zero, return, no match.
				if (trackHeight == 0)
				{
					return;
				}
				
				// If trackHeight is greater than zero, go down a level.
				else if (trackHeight > 0)
				{
					trackHeight--;
				}
			}

			// If we got a match, buffs next node equal next next.
			else if (buff.next.get(trackHeight).data.compareTo(data) == 0)
			{
				if (get(data) == buff.next.get(trackHeight))
				{
					buff.next.set(trackHeight, buff.next.get(trackHeight).next.get(trackHeight));
				}
				trackHeight--;
			}
		}
		numNodes--;
		if (numNodes > 0)
			trimSkipList(getMaxHeight(numNodes));
	}

	public boolean contains(T data)
	{
		Node<T> buff = head;
		int trackHeight = currHeight - 1;

		// Go through all levels.
		while (trackHeight >= 0)
		{
			// If next is null.
			if (buff.next.get(trackHeight) == null)
			{
				// If we got to the last level, return false, no match.
				if (trackHeight == 0)
				{
					return false;
				}
				
				// Go down a level.
				else if (trackHeight > 0)
				{ 	
					trackHeight --;
				}
			}

			// If next's data is less than what we are looking for, go to next node in level.
			else if (buff.next.get(trackHeight).data.compareTo(data) < 0)
			{
				buff = buff.next.get(trackHeight);
			}

			// If next's data is greater than data we are looking for...
			else if (buff.next.get(trackHeight).data.compareTo(data) > 0)
			{
				// If we are in the bottom level, return, no match.
				if (trackHeight == 0)
				{
					return false;
				}

				// Go to next level.
				else if (trackHeight > 0)
				{
					trackHeight--;
				}
			}

			// If we find a match return true.
			else if (buff.next.get(trackHeight).data.compareTo(data) == 0)
			{
				return true;
			}
		}

		return false;
	}

	public Node<T> get(T data)
	{
		Node<T> buff = head;
		int trackHeight = currHeight - 1;

		// Go through all levels.
		while (trackHeight >= 0)
		{
			// If next is null.
			if (buff.next.get(trackHeight) == null)
			{
				// If we got to the last level, return false, no match.
				if (trackHeight == 0)
				{
					return null;
				}
				
				// Go down a level.
				else if (trackHeight > 0)
				{ 	
					trackHeight --;
				}
			}

			// If next's data is less than what we are looking for, go to next node in level.
			else if (buff.next.get(trackHeight).data.compareTo(data) < 0)
			{
				buff = buff.next.get(trackHeight);
			}

			// If next's data is greater than data we are looking for...
			else if (buff.next.get(trackHeight).data.compareTo(data) > 0)
			{
				// If we are in the bottom level, return, no match.
				if (trackHeight == 0)
				{
					return null;
				}

				// Go to next level.
				else if (trackHeight > 0)
				{
					trackHeight--;
				}
			}

			// If we find a match return true.
			else if (buff.next.get(trackHeight).data.compareTo(data) == 0)
			{
				// If we are in the bottom level, return match.
				if (trackHeight == 0)
				{
					return buff.next.get(trackHeight);
				}

				// Go to lower level.
				else if (trackHeight > 0)
				{
					trackHeight--;
				}
			}
		}

		return null;
	}

	// return maxHeight with given nodes.
	public static int getMaxHeight(int n)
	{
		if (n == 1)
			return 1;
		else 
			return (int)Math.ceil(Math.log(n) / Math.log(2));
	}

	// Generate a random height with a maximum.
	private static int generateRandomHeight(int maxHeight)
	{
		int n;

		// 50 percent chance of returning number.
		for (int i = 1; i <= maxHeight; i ++)
		{
			n = (int)(Math.random() * 2) + 1;
			
			if (n == 1)
				return i;
			else 
				continue;
		}

		return maxHeight;
	}

	// Grow the skipList by one.
	private void growSkipList()
	{
		Node<T> buff = head;
		buff.grow();
		buff = buff.next.get(currHeight - 1);
		Node<T> relink = head;

		// While we dont get to null.
		while (buff != null)
		{
			// If maybeGrow is 1, then grow and relink the new top level.
			if (buff.maybeGrow() == 1)
			{
				relink.next.set(relink.height() - 1, buff);
				relink = relink.next.get(relink.height() - 1);
			}
			
			buff = buff.next.get(currHeight - 1);
		}
			
		currHeight++;
	}

	// trim the list to the height we need.
	private void trimSkipList(int height)
	{
		Node<T> buff = head;
		
		if (numNodes == 0)
			return;
		
		// While we dont get to null.
		while (buff != null)
		{
			// if NodeHeight is greater than height, trim.
			if (buff.height() > height)
				buff.trim(height);
			
			buff = buff.next.get(height - 1);
		}

		currHeight = height;
	}


	public static double difficultyRating()
	{
		return 5.0;
	}

	public static double hoursSpent()
	{
		return 30.0;
	}
}
