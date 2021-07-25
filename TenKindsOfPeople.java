import java.util.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class TenKindsOfPeople{

	public static void main(String[] args) throws IOException{
		InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        String[] firstLine = sc.readLine().split(" "); 
        int numberOfRows = Integer.parseInt(firstLine[0]);
        int numberOfColumns = Integer.parseInt(firstLine[1]); 

        //int[][] mapImage = new int[numberOfRows][numberOfColumns];
        //boolean[][] visited = new boolean[numberOfRows][numberOfColumns];

        // we create a territory checker, to store territories of the same type with unique index
        // this helps to reduce recursive calls since we just need to check if 
        // both zones belong to the same parent id, something just like UFDS.  
        int[][] territoryTrackerImage = new int[numberOfRows][numberOfColumns]; 
        int territoryid = 10; // start naming convention from 10, this is the parent index to classify similar territorial zones together  


        // get mapImage contents 
        // binary zone is 0 
        // decimal zone is 1 
        for (int i = 0 ; i < numberOfRows; i++){
        	String[] mapReader = sc.readLine().split("");
        	//Arrays.fill(visited[i],false); // fill all boo with false first since all not visited atm 
        	for (int j = 0; j < numberOfColumns; j++){
        		//mapImage[i][j] = Integer.parseInt(mapReader[j]);
                territoryTrackerImage[i][j] = Integer.parseInt(mapReader[j]);
        	}
        }

        int numberOfQueries = Integer.parseInt(sc.readLine()); 

        StringBuffer finalOutput = new StringBuffer(""); 
        
        HashMap<Integer,Integer> territoryMapping = new HashMap<Integer,Integer>(); 


        for (int row = 0; row < numberOfRows; row++){
            for (int col = 0; col < numberOfColumns; col++){
                if (territoryTrackerImage[row][col] < 10 /*!visited i.e.*/){
                    int identity = territoryTrackerImage[row][col];
                    DFSRocks(row,col,territoryid,identity,/*mapImage,visited,*/territoryTrackerImage); 
                    territoryMapping.put(territoryid,identity);
                    territoryid++; // once recursion finishes, we increment the "parent index", to mark the next territory of concern 
                }
                // else continue 
            }
        } 

        while (numberOfQueries-- > 0){
        	String[] queryLine = sc.readLine().split(" ");
            int fromRow = Integer.parseInt(queryLine[0])-1;
            int fromCol = Integer.parseInt(queryLine[1])-1;
            int toRow = Integer.parseInt(queryLine[2])-1;
            int toCol = Integer.parseInt(queryLine[3])-1;   
            
            // if origin and destination do not house the same type of people or territoryid is different, it's obviously neither, just print and continue 
            // the while loop
            if (territoryTrackerImage[fromRow][fromCol] != territoryTrackerImage[toRow][toCol]){
                finalOutput.append("neither").append("\n");
                continue;
            }

            // else when the origin's and destination's houses same people, we investigate if their "parent" territory index is same or not 
            // idea resembles UFDS also 

            else{
                // belong to the same territory id 
                if (territoryMapping.get(territoryTrackerImage[fromRow][fromCol]) == 0){
                    finalOutput.append("binary").append("\n");
                }
                else{
                    finalOutput.append("decimal").append("\n");
                }
                
            }
            
        }

        writer.print(finalOutput);
        writer.flush(); 

	}

	public static void DFSRocks(int row,int col,int territoryid,int identity,/* int[][] mapImage, boolean[][] visited,*/ int[][] territoryTrackerImage){
        // find out what's the maximum possible row and col for the DFS recursion to be valid 
        // NOTE: Can only traverse up,down,left and right. No diagonal! 
        /*      DFSrec(u) pseudocode: 
                -visited[u] 1 // to avoid cycle
                -for all v adjacent to u // order of neighbor
                    -if visited[v] = 0 // influences DFS
                         -p[v] = u // visitation sequence
                         -DFSrec(v) // recursive (implicit stack) */ 


        //visited[row][col] = true; // to avoid cycle
        //int incompatibleZoneNumber =  mapImage[row][col] == 1 ? 0 : 1;  // incompatability checker, so if binary, this gives a decimal number and vice versa   

        territoryTrackerImage[row][col] = territoryid; // mark territory a.k.a set similar terrtorial zones to the same 'parent' index 

        // set boolean helpers 
        // referred from my lab 8 codes: 
        int maxPossibleRows = territoryTrackerImage.length;
        int maxPossibleCols = territoryTrackerImage[0].length;


        boolean upShiftPossibility = (row-1 >= 0 && row-1 < maxPossibleRows); //no change in col
        boolean downShiftPossibility = (row+1 >= 0 && row+1 < maxPossibleRows); //no change in col 
        boolean leftShiftPossibility = (col-1 >= 0 && col-1 < maxPossibleCols); //no change in row 
        boolean rightShiftPossibility = (col+1 >= 0 && col+1 < maxPossibleCols); //no change in row 

        // need to get the adjacent neighbours "for all v adj to u"
        // condition is to row and col must be legitimate, that's why we set 4 direction booleans b4hand 
        // Instead of lab 8 prereq of "W" means not land and no cloud and a hurdle 
        // this time round instead is to try checking if the direction of concern is a compatible zone number or not?
        // binary to binary and not binary to decimal and vice versa 
 
       // if (!upShiftPossibility && !downShiftPossibility && !leftShiftPossibility && !rightShiftPossibility) {System.exit(1);} //exit recursion immediately for weird arguments given 

        

        if (upShiftPossibility){
            if (territoryTrackerImage[row-1][col] < 10 /* !visited i.e.*/)
                if (territoryTrackerImage[row-1][col] == identity){
                    //mapImage still used to check for compatability with the next territory 
                    DFSRocks(row-1,col,territoryid,identity,territoryTrackerImage);
                }
        }

        //check down condition (only row increases by 1, col not affected)
        if (downShiftPossibility){
            if (territoryTrackerImage[row+1][col] < 10)
                if (territoryTrackerImage[row+1][col] == identity){
                    DFSRocks(row+1,col,territoryid,identity,territoryTrackerImage);
                }
        }

        //check left condition (only col decreases by 1, row not affected)
        if (leftShiftPossibility){
            if (territoryTrackerImage[row][col-1] < 10)
                if (territoryTrackerImage[row][col-1] == identity){
                    DFSRocks(row,col-1,territoryid,identity,territoryTrackerImage);
                }
        }


        //check right condition (only col increases by 1, row not affected)
        if (rightShiftPossibility){
            if (territoryTrackerImage[row][col+1] < 10)
                if (territoryTrackerImage[row][col+1] == identity)
                DFSRocks(row,col+1,territoryid,identity,territoryTrackerImage);
        }

}

}

