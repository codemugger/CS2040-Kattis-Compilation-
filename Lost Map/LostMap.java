import java.util.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class LostMap{

	public static void main(String[] args) throws IOException{

		    InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        int numberOfVillages = Integer.parseInt(sc.readLine());


        StringBuffer finalOutput = new StringBuffer("");

       // using prim's algorithm 

        ArrayList<Boolean> taken = new ArrayList<Boolean>();
        taken.addAll(Collections.nCopies(numberOfVillages, false));



        ArrayList<ArrayList<IntegerPair>> adjList = new ArrayList<ArrayList<IntegerPair>>();
        for (int i = 0; i < numberOfVillages; i++){
          adjList.add(new ArrayList<IntegerPair>());
    	}
        // min heap by Java Default! 
        PriorityQueue<IntegerPair> pq = new PriorityQueue<IntegerPair>(); // insert edges based on increasing weight 

    	// store adjacent list from user's input 
        for (int i = 0; i < numberOfVillages; i++){
            String[] villageConnectionsDist = sc.readLine().split(" ");
            for (int j = 0; j < numberOfVillages; j++){
                if (i == j) {continue;}
                else {
                    // road is bi-directed ! 
                    int distance = Integer.parseInt(villageConnectionsDist[j]);
                    adjList.get(i).add(new IntegerPair(j,distance));
                    adjList.get(j).add(new IntegerPair(i,distance));
                }
            }
        }

        // we start from village 0 (i.e. 1, but we start by convention 0)

        process(0,taken,adjList,pq,visited); // process facilitates insertion into pq 

        int fromIndex = 0; 

        while(!pq.isEmpty()){ // we will do this until all V vertices are taken (or E = V-1 edges are taken)

          IntegerPair front = pq.poll();
          //.first is weight , .second is adjacent vertex 

          if (!taken.get(front.second())) { // we have not connected this vertex yet
            int adjacent = front.second() + 1; 
            finalOutput.append(fromIndex+1  + " " + adjacent).append("\n"); 
            //System.out.println("Adding edge: (" + front.first() + ", " + front.second() + "), MST cost now = " + mst_cost);
            process(pq.poll().second(),taken,adjList,pq,visited);
            fromIndex = pq.poll().second();
           }

        }

        writer.print(finalOutput);
        writer.flush();
	}

  public static void process(int vtx,ArrayList<Boolean> taken,ArrayList<ArrayList<IntegerPair>> adjList,PriorityQueue<IntegerPair> pq, boolean[] visited ){
    //System.out.println(">> At vertex " + vtx);
    taken.set(vtx,true);
    for (int j = 0; j < adjList.get(vtx).size(); j++) {
      IntegerPair v = adjList.get(vtx).get(j);
      if (!taken.get(v.first())) {
        pq.add(new IntegerPair(v.second(), v.first()));  // we sort by weight then by adjacent vertex
       
        //System.out.println(">> Inserting (" + v.second() + ", " + v.first() + ") to priority queue");
      }
      else
        continue; 
        //System.out.println(">> Ignoring (" + v.second() + ", " + v.first() + ")");
    }

  }
}

class IntegerPair implements Comparable<IntegerPair> {
  public Integer _first, _second;
  // _first is the adj village (j) 
  // _second is the distance from village i to j 

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first(); // ascending order 
    else
      return this.second() - o.second();
  }

  Integer first() { return _first; }

  Integer second() { return _second; }
}
