import java.util.*;
import java.io.*;

public class KattisQuest{

	public static void main(String[] args) throws IOException{
		InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        int numberOfCommands = Integer.parseInt(sc.readLine());

        


        for (int i = 0; i < numberOfCommands; i++){
        	String[] lineCommands = sc.readLine().split(" ");
        	String individualCommand = lineCommands[0]; // to determine the action to add or to query 
        	
        	if (individualCommand.equals("add")){
        		// it is to add into the tree mapping 
        		int energyRequiredForQuest = Integer.parseInt(lineCommands[1]);
        		int gold = Integer.parseInt(lineCommands[2]);
        		if (!mapping.containsKey(energyRequiredForQuest)){
        			mapping.put(energyRequiredForQuest, new PriorityQueue<Integer>(Comparator.reverseOrder()));
        			mapping.get(energyRequiredForQuest).add(gold);
        		}
        		else{
        			// contains the energy as the key in the treemap 
        			mapping.get(energyRequiredForQuest).add(gold);
        		}

        	}

        	else{
        		// it is to query 
        		Integer energyAvailable = Integer.parseInt(lineCommands[1]);
        		long finalGoldObtained = 0;
        		while (mapping.floorEntry(energyAvailable) != null){toi 
        			// to make sure that there are no more quests available to be cleared
        			// with the energy level left/available 
        			// floorEntry Returns the first entry with a key equals to or smaller than element in the TreeMap
        			// get flooor key from the map, 
        			int energyToBeExpended = mapping.floorKey(energyAvailable);
        			int gold = mapping.get(energyToBeExpended).poll();
        			finalGoldObtained += gold;
        			energyAvailable -= energyToBeExpended; 

        			if (mapping.get(energyToBeExpended).isEmpty()) {mapping.remove(energyToBeExpended);}

        		}

        		writer.println(finalGoldObtained);
        	}

        	writer.flush(); 
       }
}       
}
