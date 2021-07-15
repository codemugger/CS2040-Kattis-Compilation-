import java.util.*;

public class StuckInATimeLoop {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		for (int i = 1 ; i <= input ; i++) {
			System.out.printf("%d Abracadabra\n",i); // Very similar to C syntax for this printf 
		}

	}
}