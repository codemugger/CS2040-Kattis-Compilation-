import java.util.*;


public class ExactlyElectrical{

	public static void main(String[] args){
		// A recursion question i guess? 
		Scanner sc = new Scanner(System.in);
		//get starting coordinate 
		int startingX = sc.nextInt();
		int startingY = sc.nextInt();
		//Coordinates startingCoordinates = new Coordinates(startingX,startingY);
		sc.nextLine();
		//get destination coordinate 
		int endingX = sc.nextInt();
		int endingY = sc.nextInt();
		//Coordinates destinationCoordinates = new Coordinates(endingX,endingY);
		sc.nextLine();

		//get electrical units of the vehicle 
		int electricalCapacity = sc.nextInt();

		boolean possible = true; 

		int keyDifference = Math.abs(startingX - endingX) + Math.abs(startingY - endingY);
		if (electricalCapacity < keyDifference || (electricalCapacity - keyDifference) % 2 != 0 ) {possible = false;}

		if (!possible) {System.out.println("N");}
		else {System.out.println("Y");} 



	}
} 


/*
class Coordinates{
	public int x; // streets 
	public int y; // avenues 

	public Coordinates(int x, int y){
		this.x = x; 
		this.y = y;
	}

	public int getX() {return x;}
	public int getY() {return y;}

	public void setX(int value) {x += value;}
	public void setY(int value) {y += value;}

} */ 