import java.util.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class DominoesRevamp{

	// main idea is to just use UFDS, using kosaraju's algorithm possible but too overkill 
	// maybe i can try to connect those dominoes that are together, to a single group with a parent as a representative? 

	public static void main(String[] args) throws IOException{
		    InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        int testCases = Integer.parseInt(sc.readLine()); 

        StringBuffer finalOutput = new StringBuffer(""); 

       
        while (testCases-- > 0){
        	String[] mainInfoLine  = sc.readLine().split(" ");
        	int numberOfDominoes = Integer.parseInt(mainInfoLine[0]);
        	int numberOfLines = Integer.parseInt(mainInfoLine[1]);

          ArrayList<ArrayList<Integer>> adjList = new ArrayList<ArrayList<Integer>>();
          for (int i = 0; i < numberOfDominoes; i++) {
            adjList.add(new ArrayList<Integer>());
          }
              

          
          ArrayList<Integer> initialGraph = new ArrayList<Integer>();

          ArrayList<Integer> toposortls = new ArrayList<Integer>(); 

          boolean[] visited = new boolean[numberOfDominoes];

          Arrays.fill(visited,false);

          int[] p = new int[numberOfDominoes];

          Arrays.fill(p,-1);

        	while (numberOfLines-- > 0){
            // store adjList 
        		String[] eachLine  = sc.readLine().split(" ");
          	int dominoX = Integer.parseInt(eachLine[0])-1;
          	int dominoY = Integer.parseInt(eachLine[1])-1;
            adjList.get(dominoX).add(dominoY);	        
          }

          for (int v = 0; v < numberOfDominoes; v++){
            if (!visited[v])
              DFSRocks(v,adjList,visited,initialGraph);
          }

          for (int i = initialGraph.size() - 1; i >= 0; i--){
            // reverse order of original graph to toposort 
            toposortls.add(initialGraph.get(i));
          }

          int scc = 0;
          for (int v : toposortls){
            visited[v] = false; 
          }
          for (int v : toposortls){
            if (!visited[v]){
              scc++;
              DFSRocks(v,adjList,visited,initialGraph);
            }
          }

          finalOutput.append(scc).append("\n");

        	
     } // end main while 

        writer.print(finalOutput);
        writer.flush();

	}


  public static void DFSRocks(int v, ArrayList<ArrayList<Integer>> adjList, boolean[] visited,ArrayList<Integer> initialGraph){
    
    visited[v] = true; // to avoid cycle 

    for (int adjacentdomino : adjList.get(v)){
      if (!visited[adjacentdomino]){
        //p[adjacentdomino] = v;
        DFSRocks(adjacentdomino, adjList, visited, initialGraph);
      } 
    }

    initialGraph.add(v);

  }


}