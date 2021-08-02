import java.util.*;
import java.io.*;
import java.lang.*; 

public class LadiceSecond{

	public static void main(String[] args) throws IOException{

		InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        String[] firstLine = sc.readLine().split(" ");
        int numberOfItems = Integer.parseInt(firstLine[0]);
        int numberOfDrawers = Integer.parseInt(firstLine[1]); 
        // LADICA == Success storage  SMECE == Throw item away! 

        String storedstr = "LADICA";
        String thrownstr = "SMECE";

        StringBuffer finalOutput = new StringBuffer("");

        //PriorityQueue<Drawer> drawerQueue = new PriorityQueue<Drawer>();
        TreeMap<Integer,Drawer> drawerMap = new TreeMap<Integer,Drawer>(); 

        // creating all of the drawer objects and inserting into treemap 
        for (int i = 0; i < numberOfDrawers; i++) {
        	//drawerQueue.add(new Drawer(i));
        	// add according to the drawer's index! 
        	drawerMap.put(i,new Drawer(i));
        }

        // loop through to process each and every item 

        for (int i = 0; i <= numberOfItems; i++){
        	String[] subsequentLine = sc.readLine().split(" ");
        	int drawerAIndex = (Integer.parseInt(subsequentLine[0]) - 1) % (numberOfDrawers);
        	int drawerBIndex = (Integer.parseInt(subsequentLine[1]) - 1) % (numberOfDrawers);


        	if (drawerMap.get(drawerAIndex).emptyOrNot()){
        		// condition 1 when drawer a is empty
        		drawerMap.get(drawerAIndex).store(i);
        		finalOutput.append(storedstr).append("\n");
  

        	}
        	else if (drawerMap.get(drawerBIndex).emptyOrNot()){
        		// condition 2 when drawer b is empty 
        		drawerMap.get(drawerBIndex).store(i);
        		finalOutput.append(storedstr).append("\n");
        		
        	}

        	else {
        		// both drawer A and drawer B are not empty! 
        		// try condition  3 
        		boolean didMirkoStoreItemI = false; 
        		int startPointer = (drawerAIndex + 1) % numberOfDrawers;
        		int endPointer = drawerAIndex;
        		while (true/*numberOfDrawers - (numberOfDrawers - startPointer)%numberOfDrawers != drawerBIndex*/){
        			if (startPointer % (numberOfDrawers) == endPointer) {break;}
        			if (drawerMap.get(startPointer % (numberOfDrawers+1)).emptyOrNot()){
        				// if the next subsequent drawer found empty, transfer content there
        				// then store item i into drawer A 
        				int initialDrawerItemIndex = drawerMap.get(drawerAIndex).remove(); // remove from drawer A the initial item and transfer to pointer index drawer 
        				drawerMap.get(startPointer % (numberOfDrawers+1)).store(initialDrawerItemIndex);
        				drawerMap.get(drawerAIndex).store(i); // this is to store the NEW item i into drawer A
        				didMirkoStoreItemI = true; 
        				finalOutput.append(storedstr).append("\n");
        				break;
        			}
        			else {startPointer++;}
        		}
        		//if (didMirkoStoreItemI) {break;} // since condition 3 fulfilled (if) end the entire for loop already here 

        		// try condition 4 
        		startPointer = (drawerBIndex + 1) % numberOfDrawers;
        		endPointer = drawerBIndex;
        		while (/*numberOfDrawers - (numberOfDrawers - startPointer)%numberOfDrawers != drawerAIndex &&*/ !didMirkoStoreItemI){
        			if (startPointer % (numberOfDrawers) == endPointer) {break;}
        			if (drawerMap.get(startPointer % (numberOfDrawers)).emptyOrNot()){
        				// if the next subsequent drawer found empty, transfer content there
        				// then store item i into drawer A 
        				int initialDrawerItemIndex = drawerMap.get(drawerBIndex).remove(); // remove from drawer A the initial item and transfer to pointer index drawer 
        				drawerMap.get(startPointer % (numberOfDrawers)).store(initialDrawerItemIndex);
        				drawerMap.get(drawerBIndex).store(i); // this is to store the NEW item i into drawer A
        				didMirkoStoreItemI = true; 
        				finalOutput.append(storedstr).append("\n");
        				break;
        			}
        			else {startPointer++;}
        		}
        		// try condition 5 
        		if (!didMirkoStoreItemI) {finalOutput.append(thrownstr).append("\n");}	

        	} 
        	

        } 

        writer.print(finalOutput.substring(0,finalOutput.length() - 1));
        writer.flush();

	}
}

class Drawer{

	public int drawerIndex;
	public int itemIndex; 

	public Drawer(int drawerIndex){
		this.drawerIndex = drawerIndex;
		itemIndex = -1; // set to zero to indicate no item inside the drawer 
	}

	public void store(int itemIndex){
		this.itemIndex = itemIndex; 
	}

	public int remove(){
		int item = itemIndex;
		this.itemIndex = -1;
		return item; 
	}

	public boolean emptyOrNot(){
		if (itemIndex == -1) {return true;}
		else {return false;} 
	}
}