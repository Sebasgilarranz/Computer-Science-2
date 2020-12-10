// Sebastian Gilarranz
// COP 3503, Spring 2019
// Se171788
// Assignement 7, RunLikeHell

import java.io.*;
import java.util.*;

public class RunLikeHell
{
	// Declare and initialize running sum integer and 1D array.
	static int sum = 0;
	static int[] R = new int[2];
	
	public static int maxGain(int [] blocks)
	{
		// Decalare and initialize control variable and an integer
		// to track which was the last operation.
		int i = 0;
		int lastOp = -1;
		
		// Simple while loop to loop through blocks.
		while(i < blocks.length)
		{
			// Set R[0] to the current control and R[1] to i+1 
			// unless we hit the last block in which we will set it to i.
			R[0] = blocks[i];
			R[1] = i < blocks.length - 1 ? blocks[i + 1] : blocks[i];;
		
			// If 0 is greater than 1 then we will add 0 to the 
			// running sum and increment i by 2. We also
			// set the lastOp to 0.
			if (R[0] >= R[1])
			{
				sum += R[0];
				i += 2;
				lastOp = 0;
			}
			
			// Otherwise,
			else
			{
				// If lastOp equals 1 then we already did this and need to 
				// add i-1 to the running sum. We also need to icrement i by 1
				// and lastOp to 0 so this doesnt occur on next loop.
				if (lastOp == 1)
				{
					sum += blocks[i - 1];
					i++;
					lastOp = 0;
					continue;
				}
				// if lastOp wasn't 1 then we just add 1 to i and 
				// mark lastOp to 1.
				i++;
				lastOp = 1;
			}
		}
		
		return sum;
	}

	public static double difficultyRating()
	{
		return 2.5;
	}

	public static double hoursSpent()
	{
		return 2.0;
	}
}