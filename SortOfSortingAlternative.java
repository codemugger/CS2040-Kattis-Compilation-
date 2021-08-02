import java.util.*;
import java.io.*; 

public class SortOfSortingAlternative {

	public static void main(String[] args) throws IOException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        while (true) {
        	
        	int number_Of_Cases = Integer.parseInt(sc.readLine());

        	if (number_Of_Cases <= 0)
        		break;


        	List<String> list = new ArrayList<String>();  

        	for (int i = 0; i < number_Of_Cases; i++)
        		list.add(sc.readLine()); // This stores the inputs of the case into the caseArr 

        	//Now what i need to do is to sort the array 

        	Collections.sort(list, new SortOfComparator());

         	for (String output : list) 
         		writer.println(output);

         	writer.println();
         	   	
        }

        writer.flush();

	}
}

class SortOfComparator implements Comparator<String> {

	public int compare(String s1, String s2){
		// Compares its two arguments for order by name
		return s1.substring(0,2).compareTo(s2.substring(0,2));
	}

	public boolean equals(Object obj) {
		// Simply checks to see if we have the same object
		return this == obj;
	}
}