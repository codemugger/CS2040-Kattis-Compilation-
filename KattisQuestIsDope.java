import java.util.*;
import java.io.*; 

public class LoveCatsForever{

	public static void main(String args[]) throws IOException{
	InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        int totalNumOfCommands = Integer.parseInt(sc.readLine());

        TreeMap<Integer,PriorityQueue<Quest>> mapping = new TreeMap<>(); // alternatively the heap can store a quest object! 


        for (int i = 0; i < totalNumOfCommands; i++){
        	String[] inputLine = sc.readLine().split(" ");
        	String actionCommand = inputLine[0];
        	if (actionCommand.equals("add")){
        		// get quest energy level required and gold reward 
        		int energyRequiredForQuest = Integer.parseInt(inputLine[1]);
        		int goldRewardForQuest = Integer.parseInt(inputLine[2]);

        		// check if energy level is in the treemap keys 
        		if (!mapping.containsKey(energyRequiredForQuest)){
        			mapping.put(energyRequiredForQuest, new PriorityQueue<Quest>(new QuestComparator()));
        			mapping.get(energyRequiredForQuest).add(new Quest(energyRequiredForQuest,goldRewardForQuest));
        		}
        		else{
        			mapping.get(energyRequiredForQuest).add(new Quest(energyRequiredForQuest,goldRewardForQuest));
        		}
        	}
        	else {
        		// we know that it is a query instead so only the energy available provided and the action itself 
        		Integer totalEnergyAvailable = Integer.parseInt(inputLine[1]);
        		long finalGoldObtained = 0; 
        		while (mapping.floorEntry(totalEnergyAvailable) != null){
        			int energyToBeExpended = mapping.floorKey(totalEnergyAvailable); 
        			int goldFromQuest = mapping.get(energyToBeExpended).poll().getReward(); 
        			totalEnergyAvailable -= energyToBeExpended;
        			finalGoldObtained += goldFromQuest;

        			if (mapping.get(energyToBeExpended).isEmpty()){mapping.remove(energyToBeExpended);}
        		} 

        		writer.println(finalGoldObtained);

        	}

        } // end for 

        writer.flush(); 
	}
}



class Quest{
	public int energyRequired;
	public int reward; 

	public Quest(int energyRequired, int reward){
		this.energyRequired = energyRequired;
		this.reward = reward; 
	}

	public int getEnergyRequired() {return energyRequired;}
	public int getReward() {return reward;}
}


class QuestComparator implements Comparator<Quest> {

   public int compare(Quest q1, Quest q2){
   	// set to descending order 
    if (q1.getReward() < q2.getReward()) {return 1;}
    if (q1.getReward() > q2.getReward()) {return -1;}
    return 0; // if equal 
	}

    public boolean equals(Object obj) {
        // Simply checks to see if we have the same object
        return this == obj;
    }

}
