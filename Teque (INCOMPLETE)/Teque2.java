import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter; 



public class Teque{

    public static void main(String[] args) throws IOException{
        InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        int numberOfOperations = Integer.parseInt(sc.readLine());
        
        String pushBack = "push_back";
        String pushFront = "push_front";
        String pushMiddle = "push_middle";
        String get = "get"; 


        String[] userInput; // user input per line 

        HashMap<Integer,Integer> frontMap = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> backMap = new HashMap<Integer,Integer>();

        int frontHead = -1;
        int frontTail = 0;
        int backHead = -1;
        int backTail = 0; 



        // store results with the use of a stringBuffer 
        StringBuffer sbf = new StringBuffer("");

 
        for (int i = 0; i < numberOfOperations; i++){
            userInput = sc.readLine().split(" "); 
            String function = userInput[0];
            int number = Integer.parseInt(userInput[1]); //can be either the number or the index(if is get function)

            if (function.equals(get)){
              if (number > )
            }



              } 
        writer.print(sbf);
        writer.flush();

      }

        
        

}



