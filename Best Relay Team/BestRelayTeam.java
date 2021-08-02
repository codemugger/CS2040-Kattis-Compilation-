// Phua Anson A0216176E 
import java.util.*;
import java.io.*;

public class BestRelayTeam {

	public static void main(String[] args) throws IOException{
		//BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out); // more efficient printer object instantiated here 
        Scanner sc = new Scanner(System.in); // instantiate scanner object to scan user input 
        int numberOfRunners = Integer.parseInt(sc.nextLine()); // get number of runners for input and data collection purposes 
        Runner[] runnerTable = new Runner[numberOfRunners]; // To store all runner type objects in this designated ARRAY! 
        double bestTime = 100.0; // Store the best time of the best team of 4 men , i.e. the best of all the bestCurrentTime
        

        for (int i = 0 ; i < numberOfRunners ; i++) {
        	String[] input = sc.nextLine().split(" ");  // {"BLAKE", "9.69", "8.72"} convert single line to string array 
        	String name = input[0];
        	double firstLegTime = Double.parseDouble(input[1]);
        	double secondLegTime = Double.parseDouble(input[2]); 
        	runnerTable[i] =  new Runner(name,firstLegTime,secondLegTime); // store respective runner object into runnerTable array 
        } 

        // Algorithm suggestion according to NCPC 2017:
		// Firstly, pre-sort runners by their secondLegTime 
		// Secondly, try every runner on the firstLeg
		// For every choice, fill up with 3 fastest remaining secondLegTime 

        // KIV: Now it is time to analyse the best and worst timings and add accordingly 

        // Sort runnerTable by secondLegTime first! 

        Arrays.sort(runnerTable, new SecondLegComparator()); // pre-sort by secondLegTime in ascending order 

        //Secondly, try every runner on the firstLeg
        // For every choice, fill up with 3 fastest remaining secondLegTime 

        List<Runner> bestRunnerTable = new ArrayList<Runner>(); // this list helps to ultimately store the ideal team of 4 runners 
        List<Runner> fastestThreeSecondLeg = new ArrayList<Runner>(); // this list is just for reference to the 3 fastest secondLegTime runners 

        for (int i = 0; i < 3; i++) {fastestThreeSecondLeg.add(runnerTable[i]);} // add the respective 3 fastest secondLegTime runner objects to the fastestThreeSecondLeg list 

        for (int i = 0; i < runnerTable.length; i++){
        	List<Runner> analysis = new ArrayList<Runner>(); // to store the temporarily ones for analysis purpose, see if it's the better or worser bestTime 
        	if (!fastestThreeSecondLeg.contains(runnerTable[i])) {
        		analysis.add(runnerTable[i]);
        		for (int j = 0; j < 3; j++) {analysis.add(fastestThreeSecondLeg.get(j));} // At this junction, analysis comprises the selected one + 3 fastestSecondLegTime 

        		for (int k = 0; k < analysis.size(); k++){
        			Runner chosenFirstLeg = analysis.get(k);
        			double bestCurrentTime = 0.0;
        			for(int l = 0; l < analysis.size(); l++){
        				if ((analysis.get(l).getName()).equals(chosenFirstLeg.getName())){
        					bestCurrentTime += chosenFirstLeg.getFirstLegTime();
        				}
        				else {bestCurrentTime += analysis.get(l).getSecondLegTime();}
        			}
        			if (bestCurrentTime < bestTime) {
        				bestRunnerTable.clear();
        				// append analysis to bestRunnerTable, put chosenFirstLeg at the front
        				for (Runner individual : analysis){
        					if (individual.getName().equals(chosenFirstLeg.getName())) {bestRunnerTable.add(0, individual);}
        					else {bestRunnerTable.add(bestRunnerTable.size(), individual);}
        				} 
        				bestTime = bestCurrentTime;
        			}
        		} 
       		} // else just continue the loop till it's over 
		}

		writer.println(bestTime);

		for (Runner bestie : bestRunnerTable){
			writer.println(bestie.getName());
		}

		writer.flush();



        /*bestRunnerTable[0] = runnerTable[0];
        bestRunnerTable[1] = runnerTable[1];
        bestRunnerTable[2] = runnerTable[2];

        Runner[] tempTable = Arrays.copyOfRange(runnerTable,3,runnerTable.length); // use temp table to remove the top 3 best 2nd leg runner, does not make sense for them to run again right? 

        // Sort runnerTable now by the 1st leg! 

        Arrays.sort(tempTable, new FirstLegComparator()); // sort the temp table 

        
        bestRunnerTable[3] = tempTable[0];

        // Finally, the bestRunnerTable is complete so we print the output respectively! 
        // BUT we need to get the totalTimeTaken first! 
        // Then 

        for (int i = 0; i < bestRunnerTable.length; i++){
        	if (i == 0) 
        		totalTimeTaken += bestRunnerTable[i].getFirstLegTime();
        	else
        		totalTimeTaken += bestRunnerTable[i].getSecondLegTime();
			}

		// Ready to print output 

		writer.println(totalTimeTaken);
		for (Runner selectedIndividual:bestRunnerTable){
			writer.println(selectedIndividual.getName());
		}*/

		
    } 
 } // end public class 


class Runner {
	//Initialiser
	public String name;
	public double firstLegTime;
	public double secondLegTime;

	public Runner(String name, double firstLegTime, double secondLegTime){
		this.name = name;
		this.firstLegTime = firstLegTime;
		this.secondLegTime = secondLegTime;
	}
	// getters 
	public double getFirstLegTime() { return firstLegTime;}
 	public double getSecondLegTime() { return secondLegTime;}
 	public String getName() {return name;}
    
   
}

// implement comparator to compare the first leg timing! 

class FirstLegComparator implements Comparator<Runner>{
	public int compare(Runner r1, Runner r2){
        if (r1.getFirstLegTime() < r2.getFirstLegTime()) {return -1;}
        if (r1.getFirstLegTime() > r2.getFirstLegTime()) {return 1;}
        return 0;
		
	}

	public boolean equals(Object obj) {
		// Simply checks to see if we have the same object
		return this == obj;
	}

}

// implement comparator to compare the second leg timing! 

class SecondLegComparator implements Comparator<Runner>{

	public int compare(Runner r1, Runner r2){
        if (r1.getSecondLegTime() < r2.getSecondLegTime()) {return -1;}
        if (r1.getSecondLegTime() > r2.getSecondLegTime()) {return 1;}
        return 0;
	}

	public boolean equals(Object obj) {
		// Simply checks to see if we have the same object
		return this == obj;
	}
	
}

