// PHUA ANSON A0216176E 

import java.util.*;

public class Spelling{



	public static void main(String[] args){
		Scanner sc = new Scanner(System.in); // Create a scanner first to get input from user
		

		HashMap<Character, Integer> map =new HashMap<>(); //Creating HashMap  

		map.put('a',2);
		map.put('b',22);
		map.put('c',222);
		map.put('d',3);
		map.put('e',33);
		map.put('f',333);
		map.put('g',4);
		map.put('h',44);
		map.put('i',444);
		map.put('j',5);
		map.put('k',55);
		map.put('l',555);
		map.put('m',6);
		map.put('n',66);
		map.put('o',666);
		map.put('p',7);
		map.put('q',77);
		map.put('r',777);
		map.put('s',7777);
		map.put('t',8);
		map.put('u',88);
		map.put('v',888);
		map.put('w',9);
		map.put('x',99);
		map.put('y',999);
		map.put('z',9999);
		map.put(' ',0);

		int numberOfCases = Integer.parseInt(sc.nextLine()); // get the number of cases to iterate with a for-loop use parseInt mtd to read
        // String[] input = new String[numberOfCases]; // create a string array to store input
        String[] result = new String[numberOfCases];


		for (int i = 0; i < numberOfCases; i++){
			String analysis = sc.nextLine();
			char[] wordForWord = analysis.toCharArray();
			String prelim = ""; // indiv case result storage 
			prelim += Integer.toString(map.get(wordForWord[0]));
			// loop through every single character 
			for (int j = 1 ; j < wordForWord.length ; j++) {
				/*
				if (map.get(wordForWord[j]) == 0 && map.get(wordForWord[(j-1)]) == 0) 
					prelim += 

				else if (map.get(wordForWord[j]) == 0)
					prelim += "0"; */
				if (map.get(wordForWord[j]) % 10 == map.get(wordForWord[(j-1)]) % 10) 
					prelim = prelim + " " + Integer.toString(map.get(wordForWord[j]));
				else 
					prelim += Integer.toString(map.get(wordForWord[j]));
			}
			result[i] = prelim; 
		}

		for (int i = 0; i < numberOfCases; i++){
			System.out.printf("Case #%d: %s\n",i+1,result[i]);
		}



















	}
}