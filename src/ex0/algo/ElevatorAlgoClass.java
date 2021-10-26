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
    private ArrayList<Elevator> elevatorsArr;
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
        if(elevatorsArr.isEmpty())
            throw new NoSuchElementException("No elevators in this building");
        double time = Integer.MAX_VALUE;
        int k = 0;
        for(int i = 0 ; i < elevatorsArr.size() ; i++){
            Elevator e = this.building.getElevetor(i);

        }
    }

    @Override
    public void cmdElevator(int elev) {

    }

    //getting the direction of given elevator
    public int getDirection(CallForElevator c) {
        /*
        we will check the conditions of each call same as in the 'call' method
         */
        //if the elevator is on his way to some floor
        if(c.getState() == 2)
            return c.getDest();
        //if the elevator is on his way to toe source floor || he is arrived
        if(c.getState() == 1 || c.getState() == 0)
            return c.getSrc();
        //in case that the elevator is in "rest" mood
        return 0;
    }

    //method to initialize a call for specific elevator
    private void call(Elevator e,CallForElevator c){
        //going to destination
        if(c.getState() == 2)
            e.goTo(c.getDest());
        //if the elevator is on the way to the source floor or arrived
        else if(c.getState() ==1 || c.getState() == 0)
            e.goTo(c.getDest());
    }

}
