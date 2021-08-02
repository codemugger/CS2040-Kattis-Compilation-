import java.util.*; 
public class Frosh{ 
 
 public static void main(String[] args) { 
   
  Scanner sc = new Scanner (System.in); 
   
  int n  = Integer.parseInt(sc.nextLine()); 
   
  int totalfrosh = 0; 
  int maxfreq = 0; 
   
  ArrayList<Integer> courses = new ArrayList<Integer>(); 
   
  HashMap<String,Integer> combination = new HashMap<String,Integer>(); 
   
  for (int i  = 0 ; i < n; i ++) { 
    
   for (int j = 0; j < n ; j++) { 
    courses.add(Integer.parseInt(sc.next()));  
   } 
    
   Collections.sort(courses);  // sort in ascending order 
   
   String course_ts = ""; 
   
   for (int k = 0; k < courses.size(); k ++) { 
    course_ts = course_ts + Integer.toString(k); // converts to string 
    } 
    
   //gets the value of the key by itself 
   int freq = combination.containsKey(course_ts)? combination.get(course_ts) : 0; 
   
   if (combination.containsKey(course_ts)) { 
    combination.put(course_ts, freq + 1); 
    } 
   else { 
    combination.put(course_ts, 1); 
    } 
    courses.clear(); 
  } 
   
  maxfreq = Collections.max(combination.values()); 
  
  for (Integer value : combination.values()) { 
   if (value == maxfreq) { 
    totalfrosh = totalfrosh + maxfreq; 
   } 
  } 
  System.out.println(totalfrosh);

}
}







































/*
import java.util.*;


class Frosh{

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int numberOfFrosh = Integer.parseInt(sc.nextLine());

		HashMap<String, Integer> mapping = new HashMap<>(); // store each of the every 5 combis and freq 

		int freqMax = 0; // see which freq is the max 
		int totalNumberOfFrosh = 0; 

		ArrayList<Integer> indivFroshCombi = new ArrayList<Integer>(); // store each of the Frosh combis input 

		for (int i = 0; i < numberOfFrosh; i++){
			// process input of combi from each frosh 
			
			for(int j = 0; j < 5 ; j++){
				indivFroshCombi.add(sc.nextInt());
			}

			sc.nextLine(); // to create a line for next input of the next frosh's five combi 

			// process the indivFroshCombi ArrayList 
			// sort then convert to string 

			Collections.sort(indivFroshCombi);
			String strInputIndividual = "";
			for(int k = 0; k < 5 ; k++){
				strInputIndividual += Integer.toString(indivFroshCombi.get(k));
			}

			if (!mapping.containsKey(strInputIndividual)) {mapping.put(strInputIndividual,1);}
			else {
				int initialFreq = mapping.get(strInputIndividual); 
				mapping.put(strInputIndividual, initialFreq + 1 );}

			indivFroshCombi.clear();
		} // end for loop 

		freqMax = Collections.max(mapping.values());

		// for combi in each of the mapping, if  (freq of combi == freqMax), then, totalNumberOfFrosh += freq of combi
		// else just continue iterating through 

		for (Integer value : mapping.values()){
			if (value == freqMax) {totalNumberOfFrosh += value;}
		}

		System.out.println(totalNumberOfFrosh);

		}


}

*/