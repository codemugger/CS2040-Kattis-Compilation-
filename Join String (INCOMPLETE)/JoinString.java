import java.util.*;
import java.io.*;
import java.lang.*; 

public class JoinString{

	public static void main(String[] args) throws IOException{
    //buffered reader and input stream reader reference to: https://sandeepdass003.wordpress.com/2016/06/27/inputstreamreader-class-in-java/
    // crediting just in case i get flagged out or sth lol 
    InputStreamReader sr = new InputStreamReader(System.in);
    BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter writer = new PrintWriter(System.out);

    int numberOfStrings = Integer.parseInt(sc.readLine()); // get number of strings to read 

    String[] stringArr = new String[numberOfStrings]; // store original strings here 

    HashMap<Integer,StringNodeTracker> map = new HashMap<Integer,StringNodeTracker>(); // store the integers and their string type nodes here  

    int numberOfEquations = numberOfStrings - 1; // since number of eqn is num of string - 1 by observation 


    for (int i = 0; i < numberOfStrings ; i++) { // we start from 1 so that it corresponds to the starting string index beginning from 1 ! 
        // append string input to arrList and append string index and it's corresponding node here          
        stringArr[i] = sc.readLine();  // note index here starts from zero! 
        map.put(i+1,new StringNodeTracker(i+1,stringArr[i])); // get i+1 since stringArr index starts from zero
    } 

    if (numberOfStrings <= 1) {writer.print(stringArr[0]);} // since there's only 1 string input and no eqn needed might as well just print one 
    int selectedIndex = 0; // this is the final index with the all the nodes linked together 
     // if numberOfEquations is more than 0 if there's multiple strings
    for (int j = 1; j <= numberOfEquations; j++){
        String[] equationInput = sc.readLine().split(" ");
        int firstModifier = Integer.parseInt(equationInput[0]); // convert to integer first 
        int secondModifier = Integer.parseInt(equationInput[1]);
        // now time to join the 2 strings together with the String index modifiers
        map.get(firstModifier).setNext(map.get(secondModifier));
        if (j == numberOfEquations){ // when we reach the last equation, it is obvious that the first modfier of the last eqn comprises all the linked nodes
            selectedIndex = firstModifier; 
        } // reach the last eqn alrd 
    } // end for 

    StringNodeTracker currentNode = map.get(selectedIndex); // set current node corresponding to the only node with "connected strings" 
    for (int i = 0; i < numberOfStrings; i++){
        writer.print(stringArr[currentNode.getIndex()-1]); // obtain word from node since node stores the word 
        currentNode = currentNode.getNext(); // get the next node 
    }

    writer.flush();
}

}


class StringNodeTracker {
    /* attributes */
    public int index;// store index of the string
    public String word; // store the word of the string 
    public StringNodeTracker next; // next to link the another node 
    public StringNodeTracker last; // last to track the last node 

    /* constructors */
    public StringNodeTracker(int index,String word) {this(index,word,null);}

    public StringNodeTracker(int index,String word, StringNodeTracker n) { 
        this.word = word; 
        this.index = index; 
        next = n; 
        last = this; // since at the start the last pointer points to itself 
    }

    /* get the next ListNode */
    public StringNodeTracker getNext() {return next;}

    /* get the item of the ListNode */
    public String getWord() {return word;}

    public int getIndex() { return index;}

    /* set the index of the ListNode */
    public void setIndex(int val) {index = val;}

    /* set the next reference */
    public void setNext(StringNodeTracker n) {
        //next = n; last = n.last;
        if (this.next == null) {this.next = n; this.last = n.last;}
        else {
            /*
            StringNodeTracker curr = this; 
            while (curr.next != null){curr = curr.next;} // we get to the very last node 
            curr.next = n;
            last = n.last; */ 
            StringNodeTracker endOfNode = this.last;
            endOfNode.next = n;
            last = n.last; 

        }
    }

    public void setWord(String word) {this.word = word;}
}



/*
INPUT 1: 
4
cute
cat
kattis
is
3 2
4 1
3 4

OUTPUT 1 : kattiscatiscute

INPUT 2: 
3
howis
this
practicalexam
1 2
1 3

OUTPUT 2: howisthispracticalexam */ 