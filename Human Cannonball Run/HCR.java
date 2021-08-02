import java.util.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;


public class HCR{

	public static void main(String[] args) throws IOException{

		    InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);


        double inf = 1000000000.0; // choose large number to fill d array 
        ArrayList<Double> d = new ArrayList<Double>(); // store distance from source vertex 0 
        ArrayList<Integer> p = new ArrayList<Integer>(); // store the parent 
        ArrayList<ArrayList<AnsonPair>> adjList = new ArrayList<ArrayList<AnsonPair>>();


        // get current location 
        String[] currentLoc = sc.readLine().split(" ");
        double currentX = Double.parseDouble(currentLoc[0]);
        double currentY = Double.parseDouble(currentLoc[1]);

        // get destination location 
        String[] destLoc = sc.readLine().split(" ");
        double destX = Double.parseDouble(destLoc[0]);
        double destY = Double.parseDouble(destLoc[1]);

        int numberOfCannons = Integer.parseInt(sc.readLine());
        int totalCoordinatesConfirmed = 1 + 1 + numberOfCannons; // since total coord = 1 start pt, 1 end pt and total cannons 


        // running 5 meteres per second 
        // cannon launches 50 meteres, any dirn by 2 secs 


        ArrayList<Coordinates> coordinatesList = new ArrayList<Coordinates>(); // to store the coordinates be it point a or b or cannon pt
        coordinatesList.add(new Coordinates(currentX,currentY,false,0)); // source vertex set as 0 
        
        for (int i = 0; i < totalCoordinatesConfirmed; i++) {
          ArrayList<AnsonPair> Neighbor = new ArrayList<AnsonPair>(); // create vector of Integer pair 
          adjList.add(Neighbor); // store blank vector first
    }

        int idAllocater = 1; 

        while (numberOfCannons-- > 0){
        	String[] cannonLoc = sc.readLine().split(" ");
	        double cannonX = Double.parseDouble(cannonLoc[0]);
	        double cannonY = Double.parseDouble(cannonLoc[1]);
	        Coordinates individualCannon = new Coordinates(cannonX,cannonY,true,idAllocater); 
	        coordinatesList.add(individualCannon); 
          idAllocater++;
        }

        coordinatesList.add(new Coordinates(destX,destY,false,idAllocater));

        // store adjacency list with the infos: - (u,v,w)

        for (int i = 0; i < totalCoordinatesConfirmed; i++){
          for (int j = 0; j < totalCoordinatesConfirmed; j++){
            if (i == j) 
              continue;
            else{
              // do not use i < j, since can go shoot in every directions 
              Coordinates a = coordinatesList.get(i);
              Coordinates b = coordinatesList.get(j);
              double distance = findDistance(a,b); 
              double durationFromAtoB;
              if (!a.cannon) {
                durationFromAtoB = distance/5;
              } 
              else if(distance < 50){
                durationFromAtoB = Math.min(distance/5.0,2.0+((50.0-distance)/5.0));
              } 
              else{
                durationFromAtoB = Math.min(2 + ((distance - 50) / 5), distance/5);
              }
              adjList.get(i).add(new AnsonPair(durationFromAtoB,j));
            }
          }
        } // end for loop for process: storing adjacent list

        // Modified Dijkstra's routine
        // sort based on increasing distance
        // initialisation 
        PriorityQueue<AnsonPair> pq = new PriorityQueue<AnsonPair>();
        //d.clear();
        d.addAll(Collections.nCopies(totalCoordinatesConfirmed, inf)); // use 1B to represent INF
        //p.clear();
        p.addAll(Collections.nCopies(totalCoordinatesConfirmed, -1)); // use -1 to represent NULL'
        d.set(0, 0.0); // set coord id 0 as the source vertex, distance to itself is logically zero! 
        // end initialisation 

        pq.offer(new AnsonPair(0.0,0)); // offer source vertex , ansonpair <- (duration weight, coord id)

        while (!pq.isEmpty()) {
          // main loop 
          AnsonPair top = pq.poll(); // greedy: pick shortest duration vertex
          double theDuration = top.first(); // duration 
          int from = top.second(); // u 

          // relax if possible for each vtx "to" adjacent to vtx "from"
          if (theDuration == d.get(from)) { // make sure is the up to date queue, else discard 
            for (AnsonPair individualEdgeData : adjList.get(from)){
              int to = individualEdgeData.second();
              double thisParticularDuration = individualEdgeData.first();
              if (d.get(to) > d.get(from) + thisParticularDuration){
                d.set(to,d.get(from) + thisParticularDuration);
                pq.offer(new AnsonPair(d.get(to),to));
              }

            }
          }
          
        } // end while 

        writer.print(d.get(idAllocater)); // id allocater = last vertex id 
        writer.flush();


	}

	public static double findDistance(Coordinates a,Coordinates b){
		return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
	}

}


/*
class SpecialPair implements Comparable<SpecialPair>{
  Integer _first; // store coord of v 
  Double _second; // store weight a.k.a duration 


  public SpecialPair(Integer f, Double s) {
    _first = f;
    _second = s;
  }


  public int compareTo(SpecialPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }


  Integer first() { return _first; }
  Double second() { return _second; }
} */ 


class AnsonPair implements Comparable<AnsonPair>{
  Double _first; // store duration (weights)
  Integer _second; // store the next id 


  public AnsonPair(Double f, Integer s){
    _first = f;
    _second = s;
  }

  public int compareTo(AnsonPair o) {
    if (!this.first().equals(o.first()))
      return this.first() > o.first() ? 1 : -1; 
    else
      return this.second() - o.second();
  }

  Double first() { return _first; }
  Integer second() { return _second; }
}

class Coordinates implements Comparable<Coordinates> {
  // adapted from intergerPair class code from lecture notes 
  public int id; 
  public double x, y;
  public boolean cannon; // check if it is cannon or not 
  // Climbing into a cannon, launching yourself and landing takes a total of 2 seconds

  public Coordinates(double x, double y, boolean cannon,int id) {
    this.x = x;
    this.y = y;
    this.cannon = cannon;  
    this.id = id;
  }

  public int compareTo(Coordinates o){
    // main idea here is for us to compare with the help of pythagareous them 
    // if (x**2 + y**2) of this coord > (x**2 + y**2) of other coord, return 1 else -1. 
    // if all are equal, return 0 
    if (!this.getX().equals(o.getX()))
      return Math.pow(this.getX(),2)+Math.pow(this.getY(),2) > Math.pow(o.getX(),2)+Math.pow(o.getY(),2)  ?   1  :  -1;
    else if (!this.getY().equals(o.getY()))
      return Math.pow(this.getX(),2)+Math.pow(this.getY(),2) > Math.pow(o.getX(),2)+Math.pow(o.getY(),2)  ?   1  :  -1;
    else
      return 0;
  }

  Double getX() { return x; }

  Double getY() { return y; }

  public int getid() {return id;}

  public void updateX(double val){
    this.x += val;
  }

  public void updateY(double val){
    this.y += val;
  }

  public double distance(Coordinates o){
    return Math.sqrt(Math.pow(this.x-o.x,2) + Math.pow(this.y-o.y,2));
  }

}