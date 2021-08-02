import java.util.*;
import java.io.*;

public class Testing {

	public static void main(String[] args) throws IOException {
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter writer = new PrintWriter(System.out);
		writer.print("Enter your lucky number: ");
		writer.flush();
		int luckyNumber = Integer.parseInt(sc.readLine());
		writer.println();
		writer.println("Your lucky number is "+luckyNumber);
		writer.flush();

	}


}

/*
import java.io.*;
public class PeaSoupAndPancakes {
    public static void main(String[] args) throws IOException {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);
        boolean exists = false;
        int r = Integer.parseInt(sc.readLine()); // the number of restaurants
        for (int i = 0;i < r;i++) {
            int m = Integer.parseInt(sc.readLine()); // the number of menus
            String name = sc.readLine(); // restaurant name
            // String[] menus = new String[m];
            boolean peas = false;
            boolean pancakes = false;
            for (int j = 0;j < m;j++) {
                String food = sc.readLine();
                if (food.equals("pea soup")) {
                    peas = true;
                } else if (food.equals("pancakes")) {
                    pancakes = true;
                }
            }
            if (peas && pancakes) {
                writer.println(name);
                exists = true;
                break;
            }
        }
        if (!exists) {
            writer.println("Anywhere is fine I guess");
        }
        writer.flush();
    }
} 
*/