import java.util.*; 
public class Coconut_Splat { 
 
 public static void main(String[] args) { 
   
  Scanner sc = new Scanner(System.in); 
  int s = Integer.parseInt(sc.next()); // syllables 
  int n = Integer.parseInt(sc.next()); // no of players 
  int last_man =  0; // the starter of the count 
   
  ArrayList<player> list = new ArrayList<player>(); 
  //create the list to store them 
  for (int i = 1; i < n+1 ; i ++) { 
   player info = new player(i,3); 
   list.add(info);  
  } 
   
  while (list.size()> 1) {  
   last_man = (last_man + s - 1)%list.size(); // last man is gotten by counter + syllables  
              //thn mod by group size 
    
   player touched = list.get(last_man);//who is the last man in that list 
    
   // reduce his chances by -1 and see which category he falls in 
    
   if (touched.decreased_chances() == 2) { //he has 2 chances left 
    player update = new player(touched.getnum(),2); 
    list.add(last_man,update); 
   } 
   else if (touched.getchances() == 1) { // 1 chance left 
    last_man = last_man + 1; 
   } 
   else{ 
    list.remove(last_man); 
   } 
  } 
   
  int winner = list.get(0).getnum(); // get the single winner num  
  System.out.println(winner); 
   
 } 
} 
 
 

class player{  
//create class of player 
  public int num; // number 
  public int chances;  // his chances 
 
  public player(int num, int chances){ 
    this.num = num; 
    this.chances = chances;  
  } 
 
  public int getnum() {return num;}  
  public int getchances() {return chances;} 
  public int decreased_chances() {return chances = chances - 1;} // if touched, he loses 1 chance 
}


































/*import java.util.*; 
public class CoconutSplatAlternative { 
 
 public static void main(String[] args) { 
   
  Scanner sc = new Scanner(System.in); 
  int s = Integer.parseInt(sc.next()); // syllables 
  int n = Integer.parseInt(sc.next()); // no of players 
  int last_man =  0; // the starter of the count 
   
  ArrayList<player> list = new ArrayList<player>(); 
  //create the list to store them 
  for (int i = 1; i < n+1 ; i ++) { 
   player info = new player(i,3); 
   list.add(info);  
  } 
   
  while (list.size()> 1) { 
    // to get the last man 
   last_man = (last_man + s - 1)%list.size(); // last man is gotten by counter + syllables  
              //thn mod by group size 
    
    
   player touched = list.get(last_man);//who is the last man in that list 
    
   // reduce his chances by -1 and see which category he falls in 
    
   if (touched.decreased_chances() == 2) { //he has 2 chances left 
    player update = new player(touched.getnum(),2); 
    list.add(last_man,update); 
   } 
   else if (touched.getchances() == 1) { // 1 chance left 
    last_man = last_man + 1; 
   } 
   else { 
    list.remove(last_man); 
   } 
  } 
   
  int winner = list.get(0).getnum(); // get the single winner num  
  System.out.println(winner); 
   
 } 
} 
 
 
 
 
class player{  
//create class of player 
  public int num; // number 
  public int chances;  // his chances 
 
  public player(int num, int chances){ 
    this.num = num; 
    this.chances = chances;  
  } 
 
  public int getnum() {return num;}  
  public int getchances() {return chances;} 
  public int decreased_chances() {return chances = chances - 1;} // if touched, he loses 1 chance 
}