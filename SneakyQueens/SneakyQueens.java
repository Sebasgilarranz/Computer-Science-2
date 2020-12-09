// Sebastian Gilarranz
// COP 3503, Spring 2019
// Se171788
// Assignment 1, SneakyQueens

import java.io.*;
import java.util.*;

// Queen Class created to hold a column and row of each rook.
class Queen
{
	int col;
	int row;

	// Constructor to set the column and row.
	Queen(int col, int row)
	{
		this.col = col;
		this.row = row;
	}
}

public class SneakyQueens
{
	public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
	{
		ArrayList<Queen> queens = new ArrayList<>();
		int coordinateSize = coordinateStrings.size();
		
		// Loop through each coordinateString add it to queens Queen ArrayList.
		// we compare i to coordinateSize so we dont call size() on every iteration.
		for (int i = 0; i < coordinateSize; i++)
		{
			// We use parseCoordinateString to get the integer representation of a 
			// alphabetical column.
			queens.add(new Queen(parseCoordinateString(coordinateStrings.get(i).replaceAll("[^A-Za-z]+", "")),
				(Integer.parseInt(coordinateStrings.get(i).replaceAll("[^0-9]", "")))));
		} 

		// Obviously here we create ArrayLists but we are also setting elements to zero
		// so we can do arithmetic on elements later.
		ArrayList<Integer> pos = new ArrayList<>(Collections.nCopies(boardSize * 2 + 1, 0));
		ArrayList<Integer> neg = new ArrayList<>(Collections.nCopies(boardSize * 2 + 1, 0));
		ArrayList<Integer> col = new ArrayList<>(Collections.nCopies(boardSize, 0));
		ArrayList<Integer> row = new ArrayList<>(Collections.nCopies(boardSize, 0));
		
		int bufferCol, bufferRow, bufferPos, bufferNeg;

		// Loop through each coordinateString and update an ArrayList for the column, row,
		// positive diagonal, and negative diagonal. If the element is updated passed 1 
		// then we will break because two queens can attack eachother.
		for (int i = 0; i < coordinateSize; i++)
		{
			// Storing col, row, pos diagonal, and negDiagonal in buffers so we dont have 
			// to call get() again.
			bufferCol = queens.get(i).col;
			bufferRow = queens.get(i).row;
			bufferPos = (queens.get(i).col - queens.get(i).row);
			bufferNeg = (queens.get(i).col + queens.get(i).row);

			// Since we cannot have negative elements in an array, if the pos diagonal
			// algorithm results in negative, we make it positive then add boardSize to it.
			if (bufferPos < 0)
			{
				bufferPos = (bufferPos * -1);
				bufferPos = (bufferPos + boardSize);
			}
			
			// Here we update each ArrayList by 1, representing the position of coordinate.	
			col.set(bufferCol - 1, (col.get(bufferCol - 1) + 1));
			row.set(bufferRow - 1, (row.get(bufferRow - 1) + 1));
			pos.set(bufferPos, (pos.get(bufferPos) + 1));
			neg.set(bufferNeg, (neg.get(bufferNeg) + 1));

			// If an element of any ArrayList is greater than 1, we have queens that 
			// can attack eachother.
			if (col.get(bufferCol - 1) > 1 || row.get(bufferRow - 1) > 1 || pos.get(bufferPos) > 1 
				|| neg.get(bufferNeg) > 1)
			{	
				return false;
			}
		}

		// If we didn't break out of the loop, we return true because no queens can attack
		// one another.
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
		return 4;
	}

	public static double hoursSpent()
	{
		return 30;
	}
}