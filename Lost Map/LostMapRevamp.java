import java.util.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class LostMapRevamp{
  
  /*
  Hello! 
  Initially, i tried using the prim algo, but it failed very badly, as you can see from my previous attempt! 
  So i decided to use Kruskal algo, with reference to the UFDS, integerTriple and integerPair classes provided
  by the lecture contents. Would appreciate if I have feedback on why my Prim algo keeps failing :( */

	public static void main(String[] args) throws IOException{

		    InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);


        int numberOfVillages = Integer.parseInt(sc.readLine());
        StringBuffer finalOutput = new StringBuffer("");

        ArrayList<IntegerTriple> edgeList = new ArrayList<IntegerTriple>(); // to store edges 

        ArrayList<IntegerPair> mstEdges =  new ArrayList<IntegerPair>(); // to help store the resultant edges of the MST 

        // store edgelist 
        // recall that for a spanning tree, e = v - 1. 
        // if i have 7 villages to investigate, it means that i need 6 edges at least for an MST 


        for (int i = 0 ; i < numberOfVillages; i++){
            String[] villageConnectionsDist = sc.readLine().split(" ");
            for (int j = 0; j < numberOfVillages; j++){
                if (i == j) {continue;}
                else if (i < j){
                	// road is bi-directed
                  // might as well choose i < j, can ignore the i > j portion since they are repeats!  
                	int distance = Integer.parseInt(villageConnectionsDist[j]);
                  edgeList.add(new IntegerTriple(distance,i,j));
                }
                else{
                  continue; 
                }
           }
        }

        Collections.sort(edgeList);  // sort by edge weight O(E log E)

        // THE SECOND PART, THE MAIN LOOP OF KRUSKAL'S ALGORITHM

        UnionFind villageUFDS = new UnionFind(numberOfVillages);
        // all V are disjoint sets at the beginning
        int i = 0;

        for (i = 0; i < edgeList.size(); i++){
          IntegerTriple e = edgeList.get(i);
          int u = e.second(), v = e.third(), w = e.first(); // note that we have re-ordered the integer triple
          if (!villageUFDS.isSameSet(u,v)){
            // if no cycle 
            villageUFDS.unionSet(u,v); // link these two vertices 
            mstEdges.add(new IntegerPair(u,v));
          }
        }

        Collections.sort(mstEdges); 

        // print out what we have in mstEdges list 

        for (IntegerPair edges : mstEdges){
          int from = edges.first()+1;
          int to = edges.second()+1;
          finalOutput.append(from).append(" ").append(to).append("\n");
        }

        writer.print(finalOutput);
        writer.flush();

	}
}

class IntegerTriple implements Comparable<IntegerTriple> {
  public Integer _first, _second, _third;

  public IntegerTriple(Integer f, Integer s, Integer t) {
    _first = f;
    _second = s;
    _third = t;
  }

  public int compareTo(IntegerTriple o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else if (!this.second().equals(o.second()))
      return this.second() - o.second();
    else
      return this.third() - o.third();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
  Integer third() { return _third; }

  public String toString() { return first() + " " + second() + " " + third(); }
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



// Union-Find Disjoint Sets Library, using both path compression and union by rank heuristics
class UnionFind {
  public int[] p;
  public int[] rank;
  public int[] setSize;
  public int numSets;

  public UnionFind(int N) {
    p = new int[N];
    rank = new int[N];
    setSize = new int[N];
    numSets = N;
    for (int i = 0; i < N; i++) {
      p[i] = i;
      rank[i] = 0;
      setSize[i] = 1;
    }
  }

  public int findSet(int i) { 
    if (p[i] == i) return i;
    else {
      p[i] = findSet(p[i]);
      return p[i]; 
    } 
  }

  public Boolean isSameSet(int i, int j) { return findSet(i) == findSet(j); }

  public void unionSet(int i, int j) { 
    if (!isSameSet(i, j)) { 
      numSets--; 
      int x = findSet(i), y = findSet(j);
      // rank is used to keep the tree short
      if (rank[x] > rank[y]) { 
      	p[y] = x; 
      	setSize[x] = setSize[x] + setSize[y]; 
      }
      else { 
      	p[x] = y; 
      	setSize[y] = setSize[x] + setSize[y];
        if (rank[x] == rank[y]) 
          rank[y] = rank[y]+1; 
      } 
    } 
  }

  public int numDisjointSets() { return numSets; }

  public int sizeOfSet(int i) { return setSize[findSet(i)]; }
}
