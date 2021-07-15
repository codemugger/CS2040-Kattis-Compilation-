import java.util.*;

public class Autori {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // create scanner obj 
		String result = ""; // initialise the result 
		String[] input = (sc.nextLine()).split("-"); // split function only works when I initialise with an array 


		for (String word : input) { // for-each loop, please remember to initialise to the type. Only works for arrays! 
			result += word.charAt(0); // Take Note! If you want to find the char at the last position, it should be word.charAt(word.length() - 1)
		}

		System.out.println(result);
	}
}