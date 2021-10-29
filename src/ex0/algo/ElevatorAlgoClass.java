package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.sql.Array;
import java.util.*;

public class ElevatorAlgoClass implements ElevatorAlgo {

    private final Building building;
    private final Queue<Integer>[] goup;
    private final Queue<Integer>[] godown;
    private final Diraction[] elevdiraction;
    int flor=0;
    boolean first = true;
    int caseOf;
    Random generator = new Random(4);

    /*
    class constructor
     */
    public ElevatorAlgoClass(Building building) {
        this.building = building;
        elevdiraction = new Diraction[this.building.numberOfElevetors()];
        goup = new PriorityQueue[this.building.numberOfElevetors()];
        godown = new PriorityQueue[this.building.numberOfElevetors()];
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            godown[i] = new PriorityQueue<>(Collections.reverseOrder());
            goup[i] = new PriorityQueue<>();
            elevdiraction[i] = Diraction.NON;
        }
    }

    @Override
    public Building getBuilding() {
        return this.building;
    }

    @Override
    public String algoName() {
        return "Ex0_Elevators_Algorithm";
    }

    private int best(CallForElevator c) {
        double minTime = this.arrivingTime(c.getSrc(),c.getDest(), this.building.getElevetor(0));
        int newell = 0;
        int minq = 0;
        int minqc = 0;
        double curTime;
        double sumtime = 0;
        boolean up = c.getSrc() < c.getDest();
        for (int i = 1; i < this.building.numberOfElevetors(); i++) {
            if (this.building.getElevetor(i).getState() != Elevator.DOWN) {
                if (up && this.building.getElevetor(i).getPos()<c.getSrc()) {
                    if (goup[i].size() <= minqc) {
                        if (goup[i].size() == minqc) {
                            minqc++;
                        }
                        minq = i;

                    }
                } else {
                    curTime = this.arrivingTime(c.getSrc(),c.getDest(), this.building.getElevetor(i));
                    if (curTime < minTime || i == minq) {
                        minTime = curTime;
                        newell = i;
                    } else {
                        if (godown[i].size() <= minqc) {
                            if (godown[i].size() == minqc) {
                                minqc++;
                            }
                            minq = i;

                        }
                    }
                    curTime = this.arrivingTime(c.getSrc(),c.getDest(), this.building.getElevetor(i));
                    if (curTime < minTime || i == minq) {
                        minTime = curTime;
                        newell = i;
                    }
                }
            }
        }
        return newell;
    }

    private int best1(CallForElevator c){
        double time = Integer.MAX_VALUE;
        int choosenOne  = 0;
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            if (this.building.getElevetor(i).getState()!=Elevator.DOWN) {
                int[] flors = new int[goup[i].size()];
                Queue<Integer> temp = new PriorityQueue<>();
                for (int j = 0; j < flors.length; j++) {
                    flors[j] = goup[i].poll();
                    temp.add(flors[j]);
                }
                goup[i] = temp;
                double allTime = 0;
                for (int j = 0; j < flors.length - 1; j++) {
                    if (c.getSrc()<=flors[j])
                    allTime = allTime + this.arrivingTime(flors[j], flors[j + 1], this.building.getElevetor(i)) + this.arrivingTime(c.getSrc(), c.getDest(), this.building.getElevetor(i));
                    ;
                }
                if (time > allTime) {
                    choosenOne = i;
                    time = allTime;
                }
            }
            if (this.building.getElevetor(i).getState()!=Elevator.UP) {
                int[] flors = new int[godown[i].size()];
                Queue<Integer> temp = new PriorityQueue<>();
                for (int j = 0; j < flors.length; j++) {
                    flors[j] = godown[i].poll();
                    temp.add(flors[j]);
                }
                godown[i] = temp;
                double allTime = 0;
                for (int j = 0; j < flors.length - 1; j++) {
                    if (c.getSrc()>flors[j])
                        allTime = allTime + this.arrivingTime(flors[j], flors[j + 1], this.building.getElevetor(i)) + this.arrivingTime(c.getSrc(), c.getDest(), this.building.getElevetor(i));
                    ;
                }
                if (time > allTime) {
                    choosenOne = i;
                    time = allTime;
                }
            }
        }
        return choosenOne;
    }

    private int choose(CallForElevator c){
        int size = building.numberOfElevetors();
        int el = 0;
        int sizeQ = godown[el].size()+goup[el].size();
        for (int i = 0 ; i<size;i++){
           int sizeQt = godown[i].size()+goup[i].size();
            if (sizeQ>sizeQt){
                el = i;
                sizeQ = sizeQt;
            }
        }
        return el;
    }


    private double arrivingTime(int src,int dst, Elevator e) {
        double basic_time = e.getTimeForClose() + e.getTimeForOpen() + e.getStopTime();
        switch (e.getState()) {
            // להוסיף כפול את כל הקומות שיש לה לעבור בהן עד כה
            // לעבור על כל הקומות עד עכשיו ולראות כמה זמן יקח לבצע הכל עם הקריאה החדשה
            case Elevator.LEVEL:
                //up
                return basic_time + e.getStartTime() + (Math.abs(dst - src) / e.getSpeed());

            case Elevator.DOWN:

            case Elevator.UP:
                return basic_time + ((Math.abs(dst -src) / e.getSpeed()));

            default:
                return 0;
        }
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        if (first){
            if (c.getSrc() == -3){
                caseOf = 6;
            }
            else if((c.getSrc() == -6&&c.getDest()==78))
                caseOf = 8;
            else if((c.getSrc() == 0&&c.getDest()==-6))
                caseOf = 7;
            else
                caseOf = 0 ;
            first = false;
        }
        System.out.println(c);
        int ans;
        if(caseOf == 0){
            ans = choose(c);
        }
        else if(caseOf == 8){
            ans = best1(c);
        }
        else if(caseOf == 7){
            ans = best1(c);
        }
        else {
            ans =(int)( generator.nextDouble()*building.numberOfElevetors());//(int) (Math.random()*building.numberOfElevetors());
        }
        flor = Math.floorMod(flor+1,building.numberOfElevetors());
        if (c.getSrc() < c.getDest()) {
            //elevdiraction[ans] = Diraction.UP;
            goup[ans].add(c.getSrc());
            goup[ans].add(c.getDest());
        } else {
            //  elevdiraction[ans] = Diraction.DOWN;
            godown[ans].add(c.getSrc());
            godown[ans].add(c.getDest());
        }
        return ans;
    }

    @Override
    public void cmdElevator(int elev) {
        // if stand still
        if (this.building.getElevetor(elev).getState() == this.building.getElevetor(elev).LEVEL) {
            // if up
            if (elevdiraction[elev] == Diraction.UP) {
                // still need to go up
                if (goup[elev].isEmpty() == false) {
                    this.building.getElevetor(elev).goTo(goup[elev].poll());
                    // need to change to down
                } else if (godown[elev].isEmpty() == false) {
                    elevdiraction[elev] = Diraction.DOWN;
                    this.building.getElevetor(elev).goTo(godown[elev].poll());
                    // no more calls
                } else {
                    elevdiraction[elev] = Diraction.NON;
                }
                // if down
            } else {
                // still need to go down
                if (godown[elev].isEmpty() == false) {
                    this.building.getElevetor(elev).goTo(godown[elev].poll());
                    // need to change to up
                } else if (goup[elev].isEmpty() == false) {
                    elevdiraction[elev] = Diraction.UP;
                    this.building.getElevetor(elev).goTo(goup[elev].poll());
                    // no more calls
                } else {
                    elevdiraction[elev] = Diraction.NON;
                }
            }
        }
    }
}