import java.util.*;

public class NumberFun{
	// I need to create an addition, substraction, divid, mul check 
	public static boolean OpCheck(double a,double b,double c){
		int counter = 0;
		if (a + b == c)
			counter += 1;
		if (a*b == c)
			counter += 1;
		if (a/b == c || b/a == c)
			counter += 1;
		if (a - b == c || b - a == c)
			counter += 1;

		return (counter >= 1);
	}

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in); 
		int numOfTestCases = Integer.parseInt(sc.nextLine());
		String[] testCasesAnalysis = new String[numOfTestCases];
		for (int i = 0 ; i < numOfTestCases; i++) {
			double a = sc.nextDouble();
			double b = sc.nextDouble();
			double c = sc.nextDouble();
			if (OpCheck(a,b,c))
				testCasesAnalysis[i] = "Possible";
			else
				testCasesAnalysis[i] = "Impossible";
			sc.nextLine();
		}
		for (int i = 0; i < numOfTestCases; i++) {
			System.out.println(testCasesAnalysis[i]);
		}

	}
}