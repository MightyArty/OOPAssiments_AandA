package ex0.algo;
import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class ElevatorAlgoClass implements ElevatorAlgo {

    private final Building building;
    private Queue<Integer>[] go_up;
    private Queue<Integer>[] go_down;
    private Diraction[] elevdiraction;

    /*
    class constructor
     */
    public ElevatorAlgoClass(Building building) {
        this.building = building;
        elevdiraction = new Diraction[this.building.numberOfElevetors()];
        go_up = new PriorityQueue[this.building.numberOfElevetors()];
        go_down = new PriorityQueue[this.building.numberOfElevetors()];
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            go_down[i] = new PriorityQueue<>(Collections.reverseOrder());
            go_up[i] = new PriorityQueue<>();
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
        if(go_up[ans].isEmpty() && go_down[ans].isEmpty()){
            this.building.getElevetor(ans).goTo(c.getSrc());
            this.building.getElevetor(ans).goTo(c.getDest());
        }
        else {
               if (c.getSrc() < c.getDest()){
            elevdiraction[ans] = Diraction.UP;
            go_up[ans].add(c.getSrc());
            go_up[ans].add(c.getDest());
        }
        else {
            elevdiraction[ans] = Diraction.DOWN;
            go_down[ans].add(c.getSrc());
            go_down[ans].add(c.getDest());
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
                if (go_up[elev].isEmpty() == false) {
                    this.building.getElevetor(elev).goTo(go_up[elev].poll());
                    // need to change to down
                } else if (go_down[elev].isEmpty() == false) {
                    elevdiraction[elev] = Diraction.DOWN;
                    this.building.getElevetor(elev).goTo(go_down[elev].poll());
                    // no more calls
                } else {
                    elevdiraction[elev] = Diraction.NON;
                }
                // if down
            } else {
                // still need to go down
                if (go_down[elev].isEmpty() == false) {
                    this.building.getElevetor(elev).goTo(go_down[elev].poll());
                    // need to change to up
                } else if (go_up[elev].isEmpty() == false) {
                    elevdiraction[elev] = Diraction.UP;
                    this.building.getElevetor(elev).goTo(go_up[elev].poll());
                    // no more calls
                } else {
                    elevdiraction[elev] = Diraction.NON;
                }
            }
        }
    }
}
