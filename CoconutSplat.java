import java.util.*;

public class CoconutSplat{ 

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numberOfSyllables = sc.nextInt();
		int numberOfPlayers = sc.nextInt();

		ArrayList<Person> peopleList = new ArrayList<Person>();

		for (int i = 1; i <= numberOfPlayers; i++) { peopleList.add(new Person(i,3)); }

		int counterIndex = 0;

		while (peopleList.size() > 1 ) { // while no last one standing survivor has emerged yet 
			counterIndex += numberOfSyllables - 1;
			counterIndex %= peopleList.size();
			Person unlucky = peopleList.get(counterIndex);
			unlucky.updateStatus();

			if (unlucky.getStatus() == 2) {peopleList.add(counterIndex,new Person(unlucky.getPersonIndex(),2));}

			else if (unlucky.getStatus() == 1) {counterIndex++;}

			else {peopleList.remove(counterIndex);}

		}

		System.out.println(peopleList.get(0).getPersonIndex());

	}
}


class Person{

	public int personIndex;
	public int status;

	public Person(int personIndex, int status) {
		this.personIndex = personIndex;
		this.status = status;
	}

	public int getPersonIndex() {return personIndex;}
	public int getStatus() {return status;}

	public void updateStatus() {status -= 1;}
}




















/*import java.util.*;

public class CoconutSplat{

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int numberOfSyllables = sc.nextInt();
		int numberOfPlayer = sc.nextInt();

		// create the number of player objects based on numberOfPlayer
		ArrayList<Person> peopleTable = new ArrayList<Person>();

		for (int i = 1; i <= numberOfPlayer; i++){
			peopleTable.add(new Person(i,3));
		}

		int countIndex = 0; 

		while (peopleTable.size() > 1) { // Haven't last one standing yet 
			countIndex += numberOfSyllables - 1;
			countIndex %= peopleTable.size();
			Person unlucky = peopleTable.get(countIndex);
			unlucky.updateStatus(); // - "1 live"

			if (unlucky.getStatus() == 2) {peopleTable.add(countIndex,new Person(unlucky.getPersonIndex(), 2));}

			else if (unlucky.getStatus() == 1) {countIndex++;}

			else {peopleTable.remove(countIndex);}

		}




        // print output 
		System.out.println(peopleTable.get(0).getPersonIndex());


	}
}

class Person{

	public int personIndex;
	public int status; 

	public Person(int personIndex, int status){
		this.personIndex = personIndex;
		this.status = status; 
	}

	public int getPersonIndex() {return personIndex;}
	public int getStatus() {return status;}

	public void updateStatus() {status -= 1;}

}

*/