import java.util.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class LostMapSeason2{
  public static ArrayList <ArrayList<IntegerPair>> adjList;
  public static ArrayList<Boolean> taken;
  public static PriorityQueue<IntegerPair> pq;

  public static void process(int village) {
    taken.set(village, true);
    //index = village; 
    for (int j = 0; j < adjList.get(village).size(); j++) {
      IntegerPair v = adjList.get(village).get(j);
      if (!taken.get(v.first())) {
        pq.add(new IntegerPair(v.second(), v.first()));  // we sort by weight then by adjacent vertex

        
      }
     
    }
    
  }

  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception {

    InputStreamReader sr = new InputStreamReader(System.in);
    BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter writer = new PrintWriter(System.out);

    int numberOfVillages = Integer.parseInt(sc.readLine());

    StringBuffer finalOutput = new StringBuffer(""); 
   
    adjList = new ArrayList<ArrayList<IntegerPair>>();

    for (int i = 0; i < numberOfVillages; i++) {
      ArrayList<IntegerPair> Neighbor = new ArrayList<IntegerPair>(); // create vector of Integer pair 
      adjList.add(Neighbor); // store blank vector first
    }
    
    // store adjacent list from user's input 
    for (int i = 0; i < numberOfVillages; i++){
      String[] villageConnectionsDist = sc.readLine().split(" ");
      for (int j = 0; j < numberOfVillages; j++){
        if (i == j) {continue;}
        else if (i < j){
            // road is bi-directed ! 
            int distance = Integer.parseInt(villageConnectionsDist[j]);
            adjList.get(i).add(new IntegerPair(j,distance));
        }
      }
  }

    taken = new ArrayList<Boolean>(); 
    taken.addAll(Collections.nCopies(numberOfVillages, false));
    pq = new PriorityQueue<IntegerPair>();
    // take any vertex of the graph, for simplicity, vertex 0, to be included in the MST
    int index = 0; 
    process(0);
    
    

   
    while (!pq.isEmpty()) { // we will do this until all V vertices are taken (or E = V-1 edges are taken)
      IntegerPair front = pq.poll();

      if (!taken.get(front.second())) { // we have not connected this vertex yet
        int from  = index + 1;
        int to = front.second() + 1;
        finalOutput.append(from).append(" ").append(to).append("\n");
        index = front.second(); 
        process(front.second());
      }
      //else  this vertex has been connected before via some other tree branch        
    }

    writer.print(finalOutput);
    writer.flush();
  }
}

class IntegerPair implements Comparable<IntegerPair> {
  public Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }

  Integer first() { return _first; }

  Integer second() { return _second; }
}
