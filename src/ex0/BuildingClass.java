package ex0;

import java.util.ArrayList;
import java.util.List;

public class BuildingClass implements Building{

    private String BuildingName;
    private int minFloor,maxFloor;
    private int numOfElevators;
    private List ElevatorArr = new ArrayList<ElevatorClass>();

    //constructor
    public BuildingClass(int minFloor,int maxFloor,int numOfElevators,String buildingName){
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.numOfElevators = numOfElevators;
        this.BuildingName = buildingName;
    }

    //copy constructor
    public BuildingClass(BuildingClass other){
        this.BuildingName = other.BuildingName;
        this.numOfElevators = other.numOfElevators;
        this.maxFloor = other.maxFloor;
        this.minFloor = other.minFloor;
        this.ElevatorArr = new ArrayList(other.ElevatorArr);
    }
    @Override
    public String getBuildingName() {
        return "" + this.BuildingName;
    }

    @Override
    public int minFloor() {
        return this.minFloor;
    }

    @Override
    public int maxFloor() {
        return this.maxFloor;
    }

    @Override
    public int numberOfElevetors() {
        return this.numOfElevators;
    }

    @Override
    public Elevator getElevetor(int i) {
        return (Elevator) ElevatorArr.get(i);
    }
}
