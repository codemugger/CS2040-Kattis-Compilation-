import java.util.*;

public class LastFactorialDigit {
	
	public static int factorialSpecial(int n) {
		int result = 1; 
		for (int i = 1; i <= n; i++) {
			result *= i;
		}
		return result % 10 ; // %10 a.k.a modulo 10 to get the last digit of the number 
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numberOfInput = Integer.parseInt(sc.nextLine());
		int[] table = new int[numberOfInput];
		for (int i = 0 ; i < numberOfInput; i++) {
			table[i] = Integer.parseInt(sc.nextLine());
		}
		for (int j = 0 ; j < numberOfInput; j++) {
			System.out.println(factorialSpecial(table[j]));
		}

	}
}