package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ElevatorAlgoClass implements ElevatorAlgo {
    private final int UP=1,DOWN=-1;
    private Building building;
    private int direction;
    private Elevator[] elevatorsArr;
    private ArrayList<CallForElevator> calls;

    /*
    class constructor
     */
    public ElevatorAlgoClass(Building building){
        this.building = building;
        this.direction = UP;
        int amountOfFloors = (this.building.maxFloor() - this.building.minFloor()) + 1; //amount of floors
        this.elevatorsArr = new ArrayList<>(building.numberOfElevetors());    //initialize arr with the number of elevators
        this.calls = new ArrayList<CallForElevator>();  //initialize arr with amount of calls
    }

    @Override
    public Building getBuilding() {
        return this.building;
    }

    @Override
    public String algoName() {
        return "Ex0_Elevators_Algorithm";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        if(elevatorsArr.length == 0) {
            throw new NoSuchElementException("No elevators in this building");
        }
        else {
            double mintime = this.arrivingtime(c, this.building.getElevetor(0));
            int newell = 0;
            for (int i = 1; i < elevatorsArr.length; i++) {
                Elevator e = this.building.getElevetor(i);
                double curtime = this.arrivingtime(c, e);
                if (curtime < mintime) {
                    mintime = curtime;
                    newell = i;
                }
                return newell;
            }
    }}
    public double arrivingtime(CallForElevator c,Elevator e){
        double basic_time = e.getTimeForClose()+e.getTimeForOpen()+ e.getStartTime() +e.getStopTime();
        switch (e.getState()){
            case 0:
                return basic_time + (e.getSpeed()*Math.abs(c.getDest()-c.getSrc()));
            case 1:
                return basic_time + (e.getSpeed()*Math.abs(c.getDest()-c.getSrc()))
                        +(basic_time + (e.getSpeed()*Math.abs(c.getSrc()-e.getPos())));
            case 2:
                return basic_time +(e.getSpeed()*Math.abs(c.getDest()-e.getPos()));

        }

    }
    @Override
    public void cmdElevator(int elev) {

    }
}
