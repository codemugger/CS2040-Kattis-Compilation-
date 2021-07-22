import java.util.*;
import java.io.*;

public class Islands{

	public static void main(String[] args) throws IOException{
		InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        String[] firstLine = sc.readLine().split(" ");
        int numberOfRows = Integer.parseInt(firstLine[0]);
        int numberOfColumns = Integer.parseInt(firstLine[1]); 

        int minIslandsCount = 0; 

        char[][] satelliteImage = new char[numberOfRows][numberOfColumns];

        boolean[][] visitBoolean = new boolean[numberOfRows][numberOfColumns];

        for (int i = 0; i < numberOfRows; i++){
        	// store images into satelliteImage 2d char arr
        	// set everything in boolean Array as false
        	satelliteImage[i] = sc.readLine().toCharArray(); 
        	Arrays.fill(visitBoolean[i],false);
        }

        for (int row = 0; row < numberOfRows; row++){
        	for (int col = 0 ; col < numberOfColumns; col++){
        		if (satelliteImage[row][col] == 'L' && !visitBoolean[row][col]){
        			// Here I will assume that once i see the L and as i traverse through, the adj 'C' clouds could be presumed as a land too.
        			minIslandsCount++;
        			DFSRocks(row,col,satelliteImage,visitBoolean);
        			// DFS seems better than BFS, since BFS is a little more complicated esp. when handling with chars and not integers, the chars 
        			// may be indistinguishable, esp. when inserting to the queue, as seen in the BFS Algorithm 
        		}
        	}
        }

        writer.print(minIslandsCount);
        writer.flush();
  
	}

	public static void DFSRocks(int row, int col, char[][] satelliteImage, boolean[][] visitBoolean){
		// find out what's the maximum possible row and col for the DFS recursion to be valid 
		// NOTE: Can only traverse up,down,left and right. No diagonal! 
		/*      DFSrec(u) pseudocode: 
				-visited[u] 1 // to avoid cycle
				-for all v adjacent to u // order of neighbor
				    -if visited[v] = 0 // influences DFS
				         -p[v] = u // visitation sequence
				         -DFSrec(v) // recursive (implicit stack) */ 


		visitBoolean[row][col] = true; // to avoid cycle 

		//boolean helpers to call recursion appropriately 
		int maximumRows = satelliteImage.length; // up, down will see shifts in rows, -1, +1 respectively 
		int maximumColumns = satelliteImage[0].length; // left, right will see shifts in columns, -1, +1 respectively 

		boolean upShiftPossibility = (row-1 >= 0 && row-1 < maximumRows); //no change in col
		boolean downShiftPossibility = (row+1 >= 0 && row+1 < maximumRows); //no change in col 
		boolean leftShiftPossibility = (col-1 >= 0 && col-1 < maximumColumns); //no change in row 
		boolean rightShiftPossibility = (col+1 >= 0 && col+1 < maximumColumns); //no change in row 

		 
		// need to get the adjacent neighbours "for all v adj to u"
		// condition is to row and col must be legitimate, that's why we set 4 direction booleans b4hand 
		// "W" means not land and no cloud and a hurdle 
		// try checking up if !Water and rows valid 

		if (!upShiftPossibility && !downShiftPossibility && !leftShiftPossibility && !rightShiftPossibility) {System.exit(1);}

		// means can still call recursion, so let's do it 

		// below recursion simulates the part of the pseudocode:
		// "for all v adjacent to u", since we look up down left and right 

		//check up condition (only row decreases by 1, col not affected)
		if (upShiftPossibility){
			if (!visitBoolean[row-1][col])
				if (satelliteImage[row-1][col] != 'W'){
				    DFSRocks(row-1,col,satelliteImage,visitBoolean);
				}
		}

		//check down condition (only row increases by 1, col not affected)
		if (downShiftPossibility){
			if (!visitBoolean[row+1][col])
				if (satelliteImage[row+1][col] != 'W'){
				    DFSRocks(row+1,col,satelliteImage,visitBoolean);
				}
		}

		//check left condition (only col decreases by 1, row not affected)
		if (leftShiftPossibility){
			if (!visitBoolean[row][col-1])
				if (satelliteImage[row][col-1] != 'W'){
				    DFSRocks(row,col-1,satelliteImage,visitBoolean);
				}
		}


		//check right condition (only col increases by 1, row not affected)
		if (rightShiftPossibility){
			if (!visitBoolean[row][col+1])
				if (satelliteImage[row][col+1] != 'W'){
				DFSRocks(row,col+1,satelliteImage,visitBoolean);
			}
		}
	} 
}



