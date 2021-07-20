import java.util.*;
import java.io.*;

/*

SUGGESTED APPROACH:

SOURCE: NCPC 2017 

1 Maintain a set S: the teams whose score is better than your
team's score. Your rank is |S| + 1.
2 When your team solves a problem, remove all teams with a
worse score from S.
3 When another team solves a problem, add it to S if its score
becomes better than your team's score.

The amortized complexity of both operations is O(log n), so I will
need to create an AVL (BALANCED BST)Tree in this case with balancing methods and
algorithms in place. 

Perhaps create a class Team which implements the comparable of its own class  

*/


public class GCPC{

	public static void main(String[] args) throws IOException{
		// create buffered readers and writers 
		    InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        String[] firstLine = sc.readLine().split(" ");
        int numberOfTeams = Integer.parseInt(firstLine[0]);
        int numberOfEvents = Integer.parseInt(firstLine[1]);

        BST mainTreeMapping = new BST(); // create modified BST i.e AVL here! 

        ArrayList<Team> teamsList = new ArrayList<Team>(); // store respective teams here from 1,2,3 to team N

        StringBuffer finalOutput = new StringBuffer("");

        // create and insert teams into mainTreeMapping
        for (int j = 1; j <= numberOfTeams; j++){
          // Initially all teams attributes (penalty and solved counters) are set to zero
          // j starts from 1 since team index starts from 1 
          Team team = new Team(j,0,0);
        	mainTreeMapping.insert(team);
          teamsList.add(team);
        }

        // loop through every events and update each time each team scores respectively
        for (int i = 1 ; i <= numberOfEvents; i++){
        	String[] subsequentLine = sc.readLine().split(" ");
	        int teamWhichSolved = Integer.parseInt(subsequentLine[0]);
	        int penalty = Integer.parseInt(subsequentLine[1]);
          Team targetTeam = teamsList.get(teamWhichSolved - 1);
          mainTreeMapping.delete(targetTeam); 
          targetTeam.update(penalty); //teamsList index starts from 0, which reps team 1!
          mainTreeMapping.insert(targetTeam); 
          Team teamOne = teamsList.get(0);
	        finalOutput.append(numberOfTeams - mainTreeMapping.rank(teamOne)).append("\n");
          // notice i used zero index since we are only interested in team 1, which is index 0 in the arrayList! 
        }

        writer.print(finalOutput);
        writer.flush();

	}
}

class Team implements Comparable<Team> {
	public int teamIndex;
	public int problemsSolvedCounter;
	public int penaltiesCounter;
  public boolean present; 

	public Team(int teamIndex,int problemsSolvedCounter,int penaltiesCounter){
		this.teamIndex = teamIndex;
    // NOTE: Before any events happen, all counters set as zero by default 
		this.problemsSolvedCounter = problemsSolvedCounter; 
		this.penaltiesCounter = penaltiesCounter; 
	}

	public int getProblemsSolved() {return problemsSolvedCounter;}
	public int getPenaltiesCount() {return penaltiesCounter;}
	public int getTeamIndex() {return teamIndex;}

	public void update(int penaltiesCounter){
		this.problemsSolvedCounter++;
		this.penaltiesCounter += penaltiesCounter; 
	}

  public boolean isPresent() {
    // because we don't want duplicates in our AVL Tree 
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

  public int compareTo(Team other) {
    if (this.problemsSolvedCounter != other.problemsSolvedCounter) return this.problemsSolvedCounter - other.problemsSolvedCounter;
    if (this.penaltiesCounter != other.penaltiesCounter) return other.penaltiesCounter - this.penaltiesCounter; // put in reverse order since more penalties mean worst
    return other.teamIndex - this.teamIndex; // Account for cases when problems solved and penalties are all equivalent
    
  }

}

// barebones implementation of BST

// Every vertex in this BST is a Java Class

class BSTVertex{
  public BSTVertex parent, left, right;
  public Team key; // store the team object with key 
  public int height; 
  public int size; // essential to find rank, as observed by pseudocode given in tutorials and lectures
 

  public BSTVertex(Team t) { 
    key = t; 
    parent = null;
    left = null;
    right = null;
    height = 0;
    size = 1;  
  }


}

// This is just a sample implementation
// There are other ways to implement BST concepts...
class BST{
  // The AVL (Modified BST) centrals around the idea of team objects, 
  // we use the compareTo function to determine, a method defined in team class
  public BSTVertex root; //BSTVertex is the node 

  public BST() {
    this.root = null;
  }

  // public method called to search for team t. 
  // Return t if it is found in the BST otherwise return null.
  // Here the assumption is that null is never a valid key value.
  // Unlike lecture note we cannot use -1 since the return type !=int 
  public Team search(Team t) {
    BSTVertex res = search(root, t);
    return res == null ? null : res.key;
  }

  // helper method to perform search
  public BSTVertex search(BSTVertex T, Team t){
         if (T == null)  return null;                     // not found
    else if (t.compareTo(T.key) == 0) return T;                        // found
    else if (t.compareTo(T.key) > 0)  return search(T.right, t);       // search to the right
    else return search(T.left, t);        // search to the left
  }
  
  // public method called to find Minimum key value in BST
  public Team findMin() {
   return findMin(root); 
 }

  // helper method to perform findMin
  // Question: What happens if BST is empty?
  public Team findMin(BSTVertex T) {
    if (T.left == null) return T.key;                    // this is the min
    else return findMin(T.left);           // go to the left
  }

  // public method called to find Maximum key value in BST
  public Team findMax() { 
  return findMax(root); 
  }

  // helper method to perform findMax
  // Question: Again, what happens if BST is empty?
  public Team findMax(BSTVertex T) {
    if (T.right == null) return T.key;                   // this is the max
    return findMax(T.right);        // go to the right
  }

  // public method to find successor to given value v in BST.
  public Team successor(Team t) { 
    BSTVertex vPos = search(root, t);
    return vPos == null ? null : successor(vPos);
  }

  // helper recursive method to find successor to for a given vertex T in BST
  public Team successor(BSTVertex T) {
    if (T.right != null)                       // this subtree has right subtree
      return findMin(T.right);  // the successor is the minimum of right subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its right children
      while ((par != null) && (cur == par.right)) {
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? null : par.key;           // this is the successor of T
    }
  }

  // public method to find predecessor to given value v in BST
  public Team predecessor(Team t) { 
    BSTVertex vPos = search(root, t);
    return vPos == null ? null : predecessor(vPos);
  }

  // helper recursive method to find predecessor to for a given vertex T in BST
  public Team predecessor(BSTVertex T) {
    if (T.left != null)                         // this subtree has left subtree
      return findMax(T.left);  // the predecessor is the maximum of left subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its left children
      while ((par != null) && (cur == par.left)) { 
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? null : par.key;           // this is the successor of T
    }
  }

  // public method called to perform inorder traversal
  public void inorder() { 
    inorder(root);
    System.out.println();
  }

  // helper method to perform inorder traversal
  public void inorder(BSTVertex T){
    if (T == null) return;
    inorder(T.left);                               // recursively go to the left
    System.out.printf(" %d", T.key.getTeamIndex()); // visit this BST node
    inorder(T.right);                             // recursively go to the right
  }

  // public method called to insert a new key with value v into BST
  public void insert(Team t) {
   root = insert(root, t); 
 }

  // helper recursive method to perform insertion of new vertex into BST
  public BSTVertex insert(BSTVertex T, Team t){
  if (T == null) return new BSTVertex(t);          // insertion point is found
  //if (t.compareTo(T.key) == 0) {return T;}
  if (t.compareTo(T.key) > 0) {                                      // search to the right
    // when the team t is more than the key 
    T.right = insert(T.right, t);
    T.right.parent = T;
  }
  else{                                                 // search to the left
    T.left = insert(T.left, t);
    T.left.parent = T;
  }

  // update vertex T's height and size 
  updateHeight(T);
  updateSize(T);
  T = balancingAct(T);
  return T;                                         // return the updated BST
}  

  // public method to delete a vertex containing key with value v from BST
  // i.e to delete a vertex containing the team with index t 
  public void delete(Team t) {
   root = delete(root, t); 
 }

  // helper recursive method to perform deletion 
  public BSTVertex delete(BSTVertex T, Team t) {
    if (T == null)  return T;              // cannot find the item to be deleted

    if (T.key.compareTo(t) < 0)                                    // search to the right
      T.right = delete(T.right, t);
    else if (T.key.compareTo(t) > 0)                               // search to the left
      T.left = delete(T.left, t);
    else {                                            // this is the node to be deleted
      if (T.left == null && T.right == null)                   // this is a leaf
        return null;                                     // simply erase this node
      else if (T.left == null && T.right != null) {   // only one child at right        
        T.right.parent = T.parent;
        return T.right;                                                 // bypass T        
      }
      else if (T.left != null && T.right == null) {    // only one child at left        
        T.left.parent = T.parent;
        return T.left;                                                  // bypass T        
      }
      else {                                 // has two children, find successor
        Team successor = successor(T);
        T.key = successor;         // replace this key with the successor's key
        T.right = delete(T.right, successor);      // delete the old successorV
      }
    }
    if (T != null){
      updateHeight(T);
      updateSize(T);
      T = balancingAct(T);
    }
    return T;                                          // return the updated BST
  }

  // What remains for me to implement is the height, size and rank getters/setters/mutators
  // Also must implement balancing methods and algos to ensure a balanced tree throughout


  public int height(BSTVertex T){
    return T == null ? -1 : T.height;
  }

  public void updateHeight(BSTVertex T){
    // just follow prof's implementation! 
    T.height = Math.max(height(T.left),height(T.right)) + 1; 
  }

  public int size(BSTVertex T){
    return T == null ? 0 : T.size;
  }

  public void updateSize(BSTVertex T){
    T.size = size(T.left) + size(T.right) + 1;
  }

  public int rank(Team t){
    return rank(root,t);
  }

  public int rank(BSTVertex T, Team t){
    if (T == null) {return 0;}
    else if (T.key.compareTo(t) == 0){
      return  size(T.left); 
    }
    else if (T.key.compareTo(t) > 0){
      return rank(T.left,t);
    }
    else{
      return size(T.left) +1+ rank(T.right,t); 
    }
  }
  // We need to focus on the balancing now 
  //Helper to get balance factor for the node 
  public int balanceFactor(BSTVertex T){
    // general idea is T.left.height - T.right.height
    if (T == null) {return 0;}
    if (T.left == null && T.right == null){
      return 0; // When it's the leaf, -1-(-1) == 0 
    }
    else if (T.left != null && T.right == null){
      return T.left.height + 1; // since --1 == +1
    }
    else if (T.left == null && T.right != null){
      return -1 - T.right.height; //refer to the general idea above 
    }
    // the case of both left child and right child not being a null 
    else
      return T.left.height - T.right.height; 
  }


  // now we formulate rotation methods, as discussed in the lecture 
  // Just use the pseudocode from the lecture the W and T examples

  public BSTVertex leftRotation(BSTVertex T){
    // pre-req T.right != null
    BSTVertex W = T.right;
    W.parent = T.parent;
    T.parent = W;
    T.right = W.left;
    if(W.left != null) {W.left.parent = T;}
    W.left = T; 
    // We have to update the height and size of both T and W 
    // for e.g. T will "adopt" W's left child, so size changes 
    // for e.g W and T switches positions a.k.a rotate, so this leads to 
    // shift in height 
    // update height of T and then W 
    updateHeight(T);
    updateHeight(W);
    updateSize(T);
    updateSize(W);
    return W; 
  }

  // right rotation is just the mirror of leftrotation! 
  // Again follow lecture note implementation can juz copy paste! 

  public BSTVertex rightRotation(BSTVertex T){
    //prereq T.left != null 
    BSTVertex W = T.left; 
    W.parent = T.parent;
    T.parent = W;
    T.left = W.right;
    if(W.right != null){W.right.parent = T;}
    W.right = T; 
    // update height of T and then W, as shown in lecture note! 
    updateHeight(T);
    updateHeight(W);
    updateSize(T);
    updateSize(W);
    return W; 
  }

  // We also need to implement a "balancing act" method to balance 
  // the system upon insertion or deletion! 
  // similarly when we perform balancing moves we return the vertex
  // recall balanceFactor method created, make use of that 

  public BSTVertex balancingAct(BSTVertex T){
    //Prerequisite T != null, else there's nothing to check or balance
    // IF T == null, do nothing  
    if (T != null){
      if (balanceFactor(T) < -1 ){
        if (balanceFactor(T.right) == 1){
          T.right = rightRotation(T.right); //Right Left case
        }
        // if not just rotate left about T
        T = leftRotation(T);
      }
      if (balanceFactor(T) > 1){
        if (balanceFactor(T.left) == -1){
          // this is a left right case
          T.left = leftRotation(T.left);
        }
        //if not just rotate right about T 
        T = rightRotation(T);
      }
      
    }
   return T;   
  }

}

