// Sebastian Gilarranz
// COP 3503, Spring 2019
// Se171788
// Assignement 5, TopoPaths

import java.io.*;
import java.util.*;

public class TopoPaths
{
	public static int countTopoPaths(String filename) throws IOException
	{
		// Initialize all integers, strings, and arrays we will use to find valid topoPaths, paths, 
		// and cycles.
		String holder;
		int numVertices, currNumVertices, nextList = 0, length, topoPath = 0, 
			factorial1 = 0, factorial2 = 0, numPaths = 0;
		int [] buff, incoming;
		ArrayList<Integer> topos = new ArrayList<>();
		ArrayList<Integer> queue = new ArrayList<>();
		ArrayList<Integer>[] diGraph;
		
		// Begin scanning integers from file and initilializing appropiate arrays.
		Scanner in = new Scanner(new File(filename));
		numVertices = in.nextInt();
		diGraph = new ArrayList[numVertices];
		incoming = new int[numVertices];

		// while loop to add remaining numbers into the diGraph.
		while (nextList != numVertices)
		{
			currNumVertices = in.nextInt();
			diGraph[nextList] = new ArrayList<Integer>();
			
			for (int i = 0; i < currNumVertices; i++)
				diGraph[nextList].add(in.nextInt());
			
			nextList++;
		}

		// for loop to determine the vertices with incoming 
		// edges.
		for(int i = 0; i < numVertices; i++)
		{
			length = diGraph[i].size();
			for(int q = 0; q < length; q++)
			{
				incoming[(diGraph[i].get(q)) - 1]++;
			}
		}

		// Determine whether the graph has a cycle, if yes then 
		// there are no valid topo sorts.
		if (hasCycle(diGraph, numVertices))
			return 0;

		buff = new int [numVertices]; // initialize array

		// Determine how many vertices have incoming edges.
		for (int i = 0; i < numVertices; i++)
			if (incoming[i] > 0)
				factorial1 ++;
		
		// If all of them have incoming edges then we cannot have 
		// a topo path.
		if (factorial1 == numVertices)
			return 0;

		// Arithmetic to determine how many topological sorts there will be.
		factorial2 = (factorial(numVertices - factorial1) * factorial(factorial1));
		
		// While loop to save all valid topoSorts. 
		// Exctracted from Szumlanski TopoSorts.java
		while (topos.size() != factorial2)
		{
			buff = incoming.clone();
			holder = "";

			// We will visit any node with zero incoming edges and add 
			// to queue.
			for (int i = 0; i < numVertices; i++)
			{
				if (buff[i] == 0)
					queue.add(i);
			}

			while(!queue.isEmpty())
			{
				// Shuffle the queue and remove the top.
				Collections.shuffle(queue);
				int node = queue.remove(0);
				holder = holder + Integer.toString(node + 1);

				// Vertices we can reach from the current vertex has thier incoming edges decrimented.
				// If on hits zero we will add to current sort.
				for (int i = 0; i < numVertices; i++)
				{
					if (--buff[i] == 0)
						queue.add(i);
				}
			}
			
			// If the sort is'nt already in the array with all the topo Sorts
			// we add it. We do this because Collections.shuffle() can repeat
			// a certain order.
			if (topos.contains(Integer.parseInt(holder)) == false)
				topos.add(Integer.parseInt(holder));
			
		}

		// loop to check if each topo Sort is a valid path.
		// If it is then we will incrememnt the topoPath integer
		// to show how many valid topoPaths there are.
		for (int i = 0; i < factorial2; i++)
			if(checkValidPath(topos.get(i), diGraph, (factorial2)))
				topoPath++;
		
		return topoPath;
	}
	
	// Quick and painless function to determine the factorial 
	// of a number.
	public static int factorial(int n)
	{
	 	if (n == 0)
	 		return 1;

	 	return n * factorial(n - 1);
	}

	// Method we call to check whether a topoSort has a valid path.
	public static boolean checkValidPath(int path, ArrayList<Integer>[] diGraph, int numVertices)
	{	
		// Initialize two buffers to hold next two numbers in path.
		int buffer;
		int buffer2;
		
		// For loop to extract the next two numbers in path.
		for(int i = numVertices - 1; i > 0; i--)
		{
			buffer = Integer.valueOf(path);
			buffer2 = Integer.valueOf(path);

			buffer = (int)(buffer % (Math.pow(10, i + 1)));
			buffer = (int)(buffer / (Math.pow(10, i)));

			buffer2 = (int)(buffer2 % (Math.pow(10, i)));
			buffer2 = (int)(buffer2 / (Math.pow(10, i - 1)));


			// If the first buffer contains the next number in buffer2,
			// its so far a valid path.
			if (diGraph[i - 1].contains(buffer2))
				continue;
			else 
				return false;
			
		}
		
		// If we make it out it's a valid path.
		return true;
	}

	// hasCycle helper method.
	public static boolean hasCycle(ArrayList<Integer>[] diGraph, int numVertices)
	{
		// Create two arrays to hold the visited array
		// and which verticies we are currently looking at.
		boolean visited[] = new boolean[numVertices];
		boolean stack[] = new boolean[numVertices];

		// Go through each vertex in diGraph.
		for (int i = 0; i < numVertices; i++)
			// Recursive function to check if there is a cycle present.
			// If there is, we return true.
			if (hasCycle(diGraph, numVertices, i, visited, stack))
				return true;
		
		// If we make it out there is no cycle present.
		return false;
	}

	// hasCycle method that does the dirty work
	public static boolean hasCycle(ArrayList<Integer>[] diGraph, int numVertices, int currList, boolean[] visited, boolean[] stack)
	{
		// Initialize the current size of list and create
		// the nextList.
		int currListSize = diGraph[currList].size();
		int nextList;
		
		// Set currList to visited and put the currNode 
		// into the stack.
		visited[currList] = true;
		stack[currList] = true;

		// For loop which makes the recursive call to check all nodes 
		// in current List.
		for (int i = 0; i < currListSize; i++)
		{
			// Set the nextList.
			nextList = diGraph[currList].get(i) - 1;
			
			// If next list is already visited and the recursive call returns true, then return true.
			if (!visited[nextList] && hasCycle(diGraph, numVertices, nextList, visited, stack))
				return true;
			
			// If the next Node is already on the stack return true.
			else if (stack[nextList])
				return true;
		}
		
		// If we make it out, remove node from stack.
		stack[currList] = false;
		
		return false;
	}
	
	
	public static double difficultyRating()
	{
		return 4.0;
	}

	public static double hoursSpent()
	{
		return 30.0;
	}

}