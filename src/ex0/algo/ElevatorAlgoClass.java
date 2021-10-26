package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ElevatorAlgoClass implements ElevatorAlgo {
    private final int UP=1,DOWN=-1;
    private Building building;
    private int direction;
    private List<Elevator> elevators = new ArrayList<Elevator>();

    //constructor
    public ElevatorAlgoClass(Building name){
        this.building = name;
        this.direction = UP;
        this.elevators = new ArrayList<>(this.building.numberOfElevetors());
    }

    @Override
    public Building getBuilding() {
        return this.building;
    }

    @Override
    public String algoName() {
        return "The best algo!";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        if(elevators.isEmpty())
            throw new NoSuchElementException("No elevators in this building");
        //if there are only one elevator int the building
        if(elevators.size() == 1){
            //we will split it to two sections - up and down
            //up-->
            if(c.getSrc() < c.getDest())
                return 0;


        }
        return 0;
    }

    @Override
    public void cmdElevator(int elev) {

    }

    private int ArrivingTime(int source,Elevator index){
        int result = 1;
        double speed = index.getSpeed();
        int position = index.getPos();
        double stopTime = index.getStopTime();
        double startTime = index.getStartTime();
        double timeForOpen = index.getTimeForOpen();
        double timeForClose = index.getTimeForClose();
        int maxFloor = this.building.maxFloor(),mintFloor = this.building.minFloor();
        double interval = (maxFloor-mintFloor) * speed;
        double fromTo = (stopTime + startTime + timeForClose + timeForOpen) * (Math.abs(source-position));
        //split to up and down -->
        if(position <= source){
            result = (int)fromTo + (int)interval;
        }
        return 0;
    }
}
