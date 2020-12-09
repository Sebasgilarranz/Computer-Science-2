// Sebastian Gilarranz
// COP 3503, Spring 2019
// Se171788
// Assignment 3, SneakyKnights

import java.io.*;
import java.util.*;
import java.awt.*;

public class SneakyKnights
{
	public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		HashSet<Point> hashyHash = new HashSet<>();

		int coordinateSize = coordinateStrings.size();

		// The one and only for loop in this method that gets the string from the coordinateString
		// parses the column and row, adds them to a hashSet using the point system, but before 
		// it adds to the hashSet it check to see if there is a possible attack, if there is it will 
		// return false and not continue.
		for (int i = 0; i < coordinateSize; i++)
		{
			int colParsed = parseCoordinateString(coordinateStrings.get(i).replaceAll("[^A-Za-z]+", ""));
			int rowParsed = Integer.parseInt(coordinateStrings.get(i).replaceAll("[^0-9]", ""));
			
			// These check to see if there is a possible attack Knight in the 
			// hashSet already before adding.
			if (hashyHash.contains(new Point(colParsed + 1, rowParsed + 2)))
				return false;
			
			if (hashyHash.contains(new Point(colParsed + 1, rowParsed - 2)))
				return false;
			
			if (hashyHash.contains(new Point(colParsed - 1, rowParsed + 2)))
				return false;			
			
			if (hashyHash.contains(new Point(colParsed - 1, rowParsed - 2)))
				return false;

			if (hashyHash.contains(new Point(colParsed + 2, rowParsed + 1)))
				return false;
			
			if (hashyHash.contains(new Point(colParsed - 2, rowParsed + 1)))
				return false;
			
			if (hashyHash.contains(new Point(colParsed + 2, rowParsed - 1)))
				return false;
			
			if (hashyHash.contains(new Point(colParsed - 2, rowParsed - 1)))
				return false;
			
			// If we got to this point it adds the new Knight.
			hashyHash.add(new Point(colParsed, rowParsed));
		}
	
		// If we dont return false, then there are no possible attacks
		// we return true.
		return true;
	}

	public static int parseCoordinateString (String col)
	{
		int intCol = 0;
		int strLength = col.length();

		// Loop through the length of the string, and do arithmetic on each
		// character to return the integer representation of alphabetical column.
		for(int q = 0; q < strLength; q++)
		{
			intCol *= 26;
			intCol += (col.charAt(q)) - ('a' - 1);
		}
		
		return intCol;
	}

	public static double difficultyRating()
	{
		return 5.0;
	}

	public static double hoursSpent()
	{
		return 3.0;
	}
}