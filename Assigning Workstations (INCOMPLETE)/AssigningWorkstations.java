import java.util.*;
import java.io.*;

public class AssigningWorkstations{

    public static void main(String[] args) throws IOException{
        InputStreamReader sr = new InputStreamReader(System.in);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);

        String[] firstLine = sc.readLine().split(" ");

        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(); // min heap ; min to max 'done' time  

        PriorityQueue<WorkStationTracker> stationpq = new PriorityQueue<WorkStationTracker>(new WorkStationComparator()); // added this station pq 

        long numberOfResearchers = Integer.parseInt(firstLine[0]);
        int lockDuration = Integer.parseInt(firstLine[1]); //grace period of time before station gets locked again 
        int saves = 0; // number of saves stored here! 

        ArrayList<Researchers> researchersList = new ArrayList<Researchers>(); 
        ResearchersArrivalComparator arrivalComparator = new ResearchersArrivalComparator(); // To sort researchers according to arrival
        //ResearchersLeaveComparator leaveComparator = new ResearchersLeaveComparator();


        // to store the WorkStations created and to access their last used current time 
        ArrayList<WorkStationTracker> workStationList = new ArrayList<WorkStationTracker>();


        for (long i = 1; i <= numberOfResearchers; i++){
            // loop through information of every researcher 
            String[] subsequentLine = sc.readLine().split(" ");
            int timeOfArrival = Integer.parseInt(subsequentLine[0]);
            int stayHowLong = Integer.parseInt(subsequentLine[1]);
            Researchers researcherObject = new Researchers(timeOfArrival,stayHowLong);
            pq.add(researcherObject.getLeaveTime()); // add the done time a.k.a individuals leave time to queue 
            researchersList.add(researcherObject); // store the researcher to the researcher list 
        }

        // sort researchers by chronological order, in other words arrival time

        Collections.sort(researchersList, arrivalComparator); 


        if (researchersList.size() <= 1) {System.out.println(0); return;}

        for (int i = 1 ; i <= numberOfResearchers; i++){
            if (i == 1) { 
                // since is first researcher, die die must unlock a new station, so we also create a new station! 
                WorkStationTracker newWorkStation = new WorkStationTracker(researchersList.get(i-1).getLeaveTime(),lockDuration);
                //workStationList.add(newWorkStation);
                stationpq.add(newWorkStation);
            }
            // if not the first researcher who arrived, since that one confirmed need to unlock right? 
            else{

                int arrivalTimeofResearcher = researchersList.get(i-1).getArrivalTime();

                int doneTime = pq.peek();

                int leaveTimeofResearcher = researchersList.get(i-1).getLeaveTime();

                if (arrivalTimeofResearcher < doneTime){
                    boolean gotStation = false;
                    for (WorkStationTracker individualStation: stationpq){
                        if (arrivalTimeofResearcher < individualStation.getLatestCurrentTimeUsed()) {continue;}
                        individualStation.setLatestCurrentTimeUsed(leaveTimeofResearcher);
                        saves++;
                        pq.poll();
                        gotStation = true;
                        break;
                    }
                    if (!gotStation) { 
                        WorkStationTracker newWorkStation = new WorkStationTracker(leaveTimeofResearcher,lockDuration);
                        //workStationList.add(0,newWorkStation);
                        stationpq.add(newWorkStation);
                        //pq.poll();   
                    }
                    
                }
                else if (arrivalTimeofResearcher >= doneTime && arrivalTimeofResearcher <= doneTime + lockDuration){
  
                    for (WorkStationTracker individualStation : stationpq){

                        if (arrivalTimeofResearcher < individualStation.getLatestCurrentTimeUsed() /*|| arrivalTimeofResearcher - individualStation.getLatestCurrentTimeUsed() < arrivalTimeofResearcher - doneTime */){continue;}
                        else {
                            individualStation.setLatestCurrentTimeUsed(leaveTimeofResearcher);
                            saves++;
                            pq.poll();
                            break;
                     }
                } // end for 
            }
                else {
                    
                    // when researcher's time of arrival is beyond the done time + lock duration! 
                    
                    boolean canFindAStation = false;
                    for (WorkStationTracker individualStation: stationpq){
                        if (arrivalTimeofResearcher < individualStation.getLatestCurrentTimeUsed()) {continue;}
                        individualStation.setLatestCurrentTimeUsed(leaveTimeofResearcher);
                        canFindAStation = true;
                        pq.poll();
                        break;
                    }
                    if (!canFindAStation) {
                        stationpq.peek().setLatestCurrentTimeUsed(leaveTimeofResearcher);
                        pq.poll(); 
                    }
                             
                }


                }

            }
            writer.println(saves);
            writer.flush();

  
        }

    }



class Researchers{
    public int arrivalTime;
    public int leaveTime; 

    public Researchers(int timeOfArrival, int stayHowLong){
        arrivalTime = timeOfArrival;
        leaveTime = timeOfArrival + stayHowLong;
    }

    public int getArrivalTime() {return arrivalTime;}
    public int getLeaveTime() {return leaveTime;}
}

class WorkStationTracker{
    public int latestCurrentTimeUsed;
    public int lockDuration;

    public WorkStationTracker(int latestCurrentTimeUsed,int lockDuration){
        this.latestCurrentTimeUsed =latestCurrentTimeUsed;
        this.lockDuration = lockDuration;
    }

    public int getLatestCurrentTimeUsed() {return latestCurrentTimeUsed;}

    public void setLatestCurrentTimeUsed(int time) {latestCurrentTimeUsed = time;}

}


class ResearchersArrivalComparator implements Comparator<Researchers> { 

    public int compare(Researchers r1, Researchers r2){
        if (r1.getArrivalTime() < r2.getArrivalTime()) {return -1;}
        if (r1.getArrivalTime() > r2.getArrivalTime()) {return 1;}
        return 0; // if equal 
    }

    public boolean equals(Object obj) {
        // Simply checks to see if we have the same object
        return this == obj;
    }

}

class WorkStationComparator implements Comparator<WorkStationTracker> { 

    public int compare(WorkStationTracker r1, WorkStationTracker r2){
        // We will set this in descending order, so the latest one shows first! 
        if (r1.getLatestCurrentTimeUsed() < r2.getLatestCurrentTimeUsed()) {return 1;}
        if (r1.getLatestCurrentTimeUsed() > r2.getLatestCurrentTimeUsed()) {return -1;}
        return 0; // if equal 
    }

    public boolean equals(Object obj) {
        // Simply checks to see if we have the same object
        return this == obj;
    }
}
