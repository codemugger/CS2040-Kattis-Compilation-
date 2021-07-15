import java.util.*;

public class TakeTwoStones{

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		boolean isOdd = (input % 2 != 0);
		if (isOdd) 
			System.out.println("Alice");
		else
			System.out.println("Bob");

	}

}