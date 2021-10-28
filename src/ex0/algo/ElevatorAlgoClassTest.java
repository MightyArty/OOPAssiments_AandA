package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.simulator.Call_A;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;
import ex0.algo.ElevatorAlgoClass;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorAlgoClassTest {
    public Building building;
    ElevatorAlgoClass bestAlgo;

    public ElevatorAlgoClassTest(){
        Simulator_A.initData(9,null);
        this.building = Simulator_A.getBuilding();
        bestAlgo = new ElevatorAlgoClass(building);

    }

    @Test
    void allocateAnElevator() {
        Simulator_A.initData(3,null);
        bestAlgo = new ElevatorAlgoClass(Simulator_A.getBuilding());
        building = Simulator_A.getBuilding();
        Call_A call = new Call_A(9,0,20);
        Simulator_A.initAlgo(bestAlgo);
        Simulator_A.runSim();
        Simulator_A.report();
        building.getElevetor(1).goTo(3);
        building.getElevetor(2).goTo(7);
        building.getElevetor(3).goTo(0);
        assertEquals(0, bestAlgo.allocateAnElevator(call));

        CallForElevator call1 = new Call_A(1, 60, 0);
        Simulator_A.initAlgo(bestAlgo);
        Simulator_A.runSim();
        Simulator_A.report();
        building.getElevetor(1).goTo(7);
        building.getElevetor(2).goTo(2);
        building.getElevetor(3).goTo(-1);

        assertEquals(0, bestAlgo.allocateAnElevator(call1));
        assertEquals(0, bestAlgo.allocateAnElevator(call1));
    }

    @Test
    void algoNameTest(){
        bestAlgo = new ElevatorAlgoClass(building);
        assertEquals("Ex0_Elevators_Algorithm",bestAlgo.algoName());
    }

    @Test
    void cmdElevator() {
        allocateAnElevator();
        building = Simulator_A.getBuilding();
        assertEquals(1,building.getElevetor(0).getState());
        assertEquals(1,building.getElevetor(1).getState());
        assertEquals(1,building.getElevetor(2).getState());
        assertEquals(1,building.getElevetor(3).getState());
    }

    @Test
    void getBuildingTest(){
        assertEquals(building,bestAlgo.getBuilding());
        System.out.println("The building is:"+building);
    }
}