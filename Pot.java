import java.util.*;
import java.math.*;

public class Pot {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int numberOfInputs = Integer.parseInt(sc.nextLine());
		int sum = 0;
		int[] table = new int[numberOfInputs];
		// collect the relevant data
		for (int i = 0 ; i < numberOfInputs; i++){
			table[i] = Integer.parseInt(sc.nextLine());
		}
		// Now, we need to get the sum from the table
		for (int i = 0 ; i < numberOfInputs; i++) {
			int temp = table[i];
			int power = temp % 10;
			int actualValue = Math.floorDiv(temp, 10);
			sum += Math.pow(actualValue,power);
		}
		System.out.println(sum); 

	}
}