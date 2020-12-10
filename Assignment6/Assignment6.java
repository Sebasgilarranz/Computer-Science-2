// Sebastian Gilarranz
// COP 3503, Spring 2019
// Se171788
// Assignement 6, TopolALLgical

import java.io.*;
import java.util.*;

public class TopolALLgical
{
	// Declare and allocate all static objects necessary for program.
	static int[][] diGraph;
	static int numVertices, currListVertices;
	static int [] incoming;
	static HashSet<String> topoSortsHashSet = new HashSet<>();
	static ArrayList<ArrayList<Integer>> topoSortsArrayList = new ArrayList<>();
	static boolean retVal = true;
	
	// Given function to start off program.
	public static HashSet<String> allTopologicalSorts(String filename) throws IOException
	{
		// Open file and save vertices.
		storeFileVertices(filename);
		
		// If retval is true then we can continue 
		// and evalute adjacent list.
		if (retVal)
		{
			incoming();
			backTrack();
		}

		// Return the created HashSet.
		return topoSortsHashSet;
	}

	// backTracking helper function.
	public static void backTrack()
	{
		// Declare and allocate visited array.
		int [] visited = new int [numVertices];

		// Empty arrayList to kick off backTracking function.
		ArrayList<Integer> currPath = new ArrayList<>();

		backTrack(visited, currPath); // Initial call.
		convertToString(); // Convert to HashSet.
	}

	// Main backTracking function.
	public static void backTrack(int [] visited, ArrayList<Integer> currPath)
	{
		ArrayList<Integer> incomingStack = incomingStack(visited);
		ArrayList<Integer> thisPath = new ArrayList<>();
		
		// Copy currPath to newPath. (Clone() was not working whoops)
		for (Integer i : currPath)
			thisPath.add(i);

		// If incoming stack is empty,
		if (incomingStack.isEmpty())
		{
			// Check that every Vertex was visited. If not, 
			// we will return and not add path to ArrayList
			for (Integer i: visited)
				if (i == 0)
					return;

			// If we get this far we will add path.
			topoSortsArrayList.add(thisPath);
		}

		// For every vertex in the stack,
		for (Integer i: incomingStack)
		{
			visited[i] = 1; // Mark vertex as visited.

			// Drop incoming # of every other vertex adjacent to 
			// this one.
			for (int a = 0; a < diGraph[i].length; a++)
				incoming[diGraph[i][a]]--;
			
			// Add current vertex to path.
			thisPath.add(i);

			// Recursive call with updated info.
			backTrack(visited, thisPath);

			// Undo changes (lines 89 - 95)
			visited[i] = 0;

			for (int j = 0; j < diGraph[i].length; j++)
				incoming[diGraph[i][j]]++;
			
			
			thisPath.remove(new Integer(i));
		}
	}

	// Easy function to convert Arrays holding paths 
	// to strings and store in HashSet.
	public static void convertToString()
	{
		StringBuilder bobTheBuilder = new StringBuilder();

		// For every ArrayList in ArrayList
		for (ArrayList i: topoSortsArrayList)
		{
			// Reset string builder.
			bobTheBuilder.setLength(0);
			
			// For every ArrayList,
			for(int q = 0; q < i.size(); q++)
				bobTheBuilder.append((int)i.get(q) + 1 + " "); // Append to string.
			
			topoSortsHashSet.add(bobTheBuilder.toString().trim()); // Add string to hashSet.
		}
	}

	// Function to create incoming Stack based on incoming array.
	public static ArrayList<Integer> incomingStack(int [] visited)
	{
		ArrayList<Integer> incomingStack = new ArrayList<>();
		
		// If incoming number is 0 and has not been visited yet
		// add to stack.
		for(int i = 0; i < numVertices; i++)
			if (visited[i] == 0 && incoming[i] == 0)
				incomingStack.add(i);
		
		// Return the stack.
		return incomingStack;
	}

	// Create incoming array.
	public static void incoming()
	{
		incoming = new int[numVertices];
		int length;

		// Loop through diGraph and depending on how 
		// many times it shows up on the diGraph
		// increase incoming number for node.
		for (int i = 0; i < numVertices; i++)
		{
			length = diGraph[i].length;

			for (int q = 0; q < length; q++)
				incoming[(diGraph[i][q])]++;
		}

	}

	// Function to open file and store vertices.
	public static void storeFileVertices(String filename) throws FileNotFoundException
	{
		// Try opening the file.
		try
		{
			Scanner in = new Scanner(new File(filename));
			numVertices = in.nextInt();

			// Declare and allocate the graph and incoming array.
			diGraph = new int[numVertices][];
			incoming = new int[numVertices];

			// Add Vertices based on file input file,
			for (int i = 0; i < numVertices; i++)
			{
				currListVertices = in.nextInt();
				diGraph[i] = new int[currListVertices];

				// Use first character of each line to loop through next
				// vertices in line.
				for(int q = 0; q < currListVertices; q++)
					diGraph[i][q] = (in.nextInt()) - 1;
			}
		}

		// If the file could not be open make sure we dont continue the program by 
		// making retVal false.
		catch (FileNotFoundException whoops)
		{
			retVal = false;
		}
	}

	public static double difficultyRating()
	{
		return 4.5;
	}

	public static double hoursSpent()
	{
		return 35.0;
	}
}