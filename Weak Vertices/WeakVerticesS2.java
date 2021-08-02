import java.util.*;
import java.io.*;

public class WeakVerticesS2{

	public static void main(String[] args) throws IOException{
		InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

       
        while (true) {
        	int numberOfVertices = Integer.parseInt(sc.readLine());
        	if (numberOfVertices == -1) {break;}
        	

    		int[][] adjMatrix = new int[numberOfVertices][numberOfVertices];

        	// boolean array to determine which triangle found for which vertices 
        	// ultimately those with "false" will have their vertices index printed 
        	boolean[] gotTriangle = new boolean[numberOfVertices];
        	Arrays.fill(gotTriangle,false);
        	

        	//Store vertices info into adjMatrix 
        	for (int i = 0; i < numberOfVertices; i++){ 
        		String[] connectionArr = sc.readLine().split(" ");
        		for (int j = 0; j < numberOfVertices; j++){
        			adjMatrix[i][j] = Integer.parseInt(connectionArr[j]);
        		}
        	}

        	//Loop through every row with references to boolean arr to skip those
        	// that have already been determined to have a triangle linkage 

        	for (int row = 0; row < numberOfVertices; row++){
        		if (gotTriangle[row]){
        			continue;        			
        		}
        		else{
        			ArrayList<Integer> checkLinksList = new ArrayList<Integer>();
        			for (int column = 0; column < numberOfVertices; column++){
        				if (adjMatrix[row][column] == 1){
        					checkLinksList.add(column);
        				}
        			}
        			if (checkLinksList.size() <= 1) {continue;}
        			else{
        				//checkLinksList size 2 or more, need check if they are link with one another 
        				for (int i = 0; i < checkLinksList.size() - 1; i++){
        					for (int j = i + 1; j <checkLinksList.size();j++){
        						if ((adjMatrix[checkLinksList.get(i)][checkLinksList.get(j)] == 1)) {
        							gotTriangle[checkLinksList.get(i)] = true;
        							gotTriangle[checkLinksList.get(j)] = true; 
        							gotTriangle[row] = true;
        							break;
        						}
        					}
        				}
        				checkLinksList.clear();
        			}
        		}

        	}

        	for (int i = 0; i < gotTriangle.length; i++) {
        		if (gotTriangle[i]){
        			continue;
        		}
        		else{
        			writer.printf("%d ",i);
        		}  
        	}

        	writer.println();

        	
	
        }
        writer.flush();

	}


}