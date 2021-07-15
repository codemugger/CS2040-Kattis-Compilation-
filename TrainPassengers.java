// Phua Anson A0216176E 

import java.util.*;

public class TrainPassengers{


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int trainCapacity = sc.nextInt();
		int numberOfStations = sc.nextInt();
		sc.nextLine();
		boolean isPossible = true;
		int currentCapacity = 0; // cannot be -ve or exceed trainCapacity 


		for (int i = 0; i < numberOfStations; i++){
			int left = sc.nextInt();
			int entered = sc.nextInt();
			int stay = sc.nextInt();
			sc.nextLine();

			if (left > currentCapacity) isPossible = false; 

			currentCapacity = currentCapacity - left + entered; 

			if (currentCapacity > trainCapacity) isPossible = false;  

			if (currentCapacity >= 0 && currentCapacity <= trainCapacity){
				if (stay > 0 && trainCapacity - currentCapacity > 0){
					isPossible = false;
					
				} // remaining capacity 

			}

			if (i == numberOfStations - 1 && stay != 0) isPossible = false; 


			if (currentCapacity < 0 || stay < 0 || left < 0 || entered < 0) {
				isPossible = false;
				
			}
	} // end for 
	// train should start and finish the journey empty
	if (currentCapacity != 0) isPossible = false; 

	if (isPossible) System.out.println("possible");
	else System.out.println("impossible");

}
}