import java.util.*;
import java.lang.*;

public class DetailedDifferences{

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int numInput = Integer.parseInt(sc.nextLine());
		String[] table = new String[numInput * 2]; // store data 

		// get and store the data on the array table 

		for (int i = 0 ; i < numInput * 2; i++)
			table[i] = sc.nextLine();

		// Time to compare the pairs and give the relevant output 
		for (int i = 0; i < (numInput * 2)-1; i += 2) {
			String[] a = table[i].split("");
			String[] b = table[i+1].split("");
			System.out.println(table[i]);
			System.out.println(table[i+1]);
			String comparision = "";
			for (int j = 0; j < a.length; j++){
				if ((a[j]).equals(b[j]))
					comparision += ".";
				else
					comparision += "*";
			}
			System.out.println(comparision);
			System.out.println();
		}
		
	}
}