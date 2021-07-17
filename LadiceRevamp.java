// PHUA ANSON A0216176E 



// NOTE: Some algorithmic references were made and inspired by the book titled : Algorithmic Thinking by Daniel Zingaro 
// Source is from chapter 8 Problem 3 : Drawer Chore, in particular the idea of assigning a "full" partial set/component to the number zero. Truly an inspirational book
// In particular the UFDS, but they are translated from a C context to a java context and the UFDS function is adapted from the one 
// provided in the lecture! 


import java.util.*;
import java.io.*;

public class LadiceRevamp{

	public static void main(String[] args) throws IOException{

		// create buffered readers and writers 
		InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        // get baseline info such as no of items and drawers respectively 
        String[] firstLine = sc.readLine().split(" ");
        int numberOfItems = Integer.parseInt(firstLine[0]);
        int numberOfDrawers = Integer.parseInt(firstLine[1]); 
        // LADICA == Success storage  SMECE == Throw item away! 

        String storedstr = "LADICA";
        String thrownstr = "SMECE";

        // stringbuffer or stringbuilder are fine. But, stringbuffer preferred and should be used by default. 
        // stringbuffer is thread safe. But, stringbuilder is not! 
        StringBuffer finaloutcomes = new StringBuffer("");

        DrawerUFDS drawerufds = new DrawerUFDS(numberOfDrawers); 

        for (int i = 0; i < numberOfItems; i++){
        	// In this step, we loop through every of the items, there is no need to modify drawer A or drawer B indexes since 
        	// the zeroth index of the UFDS is already accounted for P[0] will immutably be zero i.e. 
        	String[] subsequentLine = sc.readLine().split(" ");
        	int drawerAIndex = Integer.parseInt(subsequentLine[0]);
        	int drawerBIndex = Integer.parseInt(subsequentLine[1]);
        	Boolean storedItemOrNot = drawerufds.unionSet(drawerAIndex,drawerBIndex);
        	// the 4 cases already accounted for from the unionSet function
        	// remember to also append a line to go to the next line 
        	if (storedItemOrNot) {finaloutcomes.append(storedstr).append("\n");}
        	else {finaloutcomes.append(thrownstr).append("\n");}
        }

        writer.print(finaloutcomes);
        writer.flush(); 

	}
}


class DrawerUFDS{                                              
  public int[] p;
  public int numsets;
  // we don't need rank in this case, since rank is not the key determinant for the union function!  
  // we need to simply adhere to the 4 conditions strictly to decide which one goes under which  

  public DrawerUFDS(int N){
    p = new int[N+1]; // N+1 instead, since is UFDS and we set the zeroth index to just be zero 
    // i.e. from indexes 0 ,1 ,2 ,3,4.........N OR [0,N+1)   
    for (int i = 0; i <= N; i++) {
      p[i] = i;
    }
  }

  public int findSet(int i) { 
    if (p[i] == i) return i;
    else {
      p[i] = findSet(p[i]);
      return p[i]; 
    } 
  }

  public Boolean isSameSet(int i, int j) {return findSet(i) == findSet(j);}

  public Boolean unionSet(int i, int j){ 
  	// basically we set the root of a set as the empty drawer, if all drawers from the set are already occupied
  	// we "link" the representative to the number 0, as inspired by the book. 
  	if (isSameSet(i,j)){
  		int x = findSet(i), y = findSet(j);
  		if (x == 0){return false;}
  		else{
  			// there's as empty drawer, which is represented by root 
  			p[x] = 0; // this points the entire set to a occupied set 
  			return true; 
  		}
  	}

    else /*!isSameSet(i, j)*/ { 
    // else if is same set no need to do anything! 
      int x = findSet(i), y = findSet(j);
      if (x != 0 /*&& y != -1*/) {
      	// drawer A has an empty slot! 
      	p[x] = y;
      	return true; 
      }
      else if (x == 0 && y != 0){
      	// x == -1, i.e, all drawers filled so we look at drawer y
      	// assign p[y] to x so that the unions join tgt as an "occupied" set already, so points to zero, i.e. 
      	p[y] = x;
      	return true; 
      }
      else {return false;} //if both not same set, x and y representative are occupied and points to 0! 
    } 
  }


//  public int numDisjointSets() {return numSets;} // not sure how to use this anyways, so commented this out since no need! 
}