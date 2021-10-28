package ex0.algo;
import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class ElevatorAlgoClass implements ElevatorAlgo {

    private final Building building;
    private Queue<Integer>[] goup;
    private Queue<Integer>[] godown;
    private  Diraction[] elevdiraction;

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
        double minTime = this.arrivingTime(c, this.building.getElevetor(0));
        int newell = 0;
        for (int i = 1; i < this.building.numberOfElevetors(); i++) {
            double curTime = this.arrivingTime(c, this.building.getElevetor(i));
            if (curTime < minTime) {
                minTime = curTime;
                newell = i;
            }
        }
        return newell;
    }
    private double arrivingTime(CallForElevator c, Elevator e) {
        double basic_time = e.getTimeForClose() + e.getTimeForOpen() + e.getStopTime();
        switch (e.getState()) {
            case Elevator.LEVEL:
                return basic_time + e.getStartTime() + (Math.abs(c.getDest() - c.getSrc())/e.getSpeed());

            case Elevator.DOWN:

            case Elevator.UP:
                return basic_time + ((Math.abs(c.getDest() - c.getSrc())/e.getSpeed()));

            default:
                return 0;
        }
    }
    @Override
    public int allocateAnElevator(CallForElevator c) {
        int ans = best(c);
        if(goup[ans].isEmpty() && godown[ans].isEmpty()){
            this.building.getElevetor(ans).goTo(c.getSrc());
            this.building.getElevetor(ans).goTo(c.getDest());
        }
        else
        {if (c.getSrc() < c.getDest()){
            elevdiraction[ans] = Diraction.UP;
            goup[ans].add(c.getSrc());
            goup[ans].add(c.getDest());
        }
        else {
            elevdiraction[ans] = Diraction.DOWN;
            godown[ans].add(c.getSrc());
            godown[ans].add(c.getDest());
        }}
        // אם המעלית לא עוברת התור של הירידה וגם לא עוברת על התר של העליה אז לשלוח אותה כבר מעכשיו לקומת המקור של הקריאה ולשנות את בהתאם הכל

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
