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

    /**
     * This is Test Class
     * using JUnit
     * by Artem Shabalin and Anna Pinchuk
     * enjoy
     */
    public ElevatorAlgoClassTest(){
        Simulator_A.initData(5,null);
        this.building = Simulator_A.getBuilding();
        bestAlgo = new ElevatorAlgoClass(building);
    }

    @Test
    void allocateAnElevator() {
        Call_A call = new Call_A(0,8,20);
        Call_A call1 = new Call_A(0,8,20);
        Simulator_A.initAlgo(bestAlgo);
        Simulator_A.runSim();
        Simulator_A.report();
        assertEquals(2,bestAlgo.allocateAnElevator(call));
        assertEquals(5,bestAlgo.allocateAnElevator(call1));
    }

    @Test
    void algoNameTest(){
        bestAlgo = new ElevatorAlgoClass(building);
        assertEquals("Ex0_Elevators_Algorithm",bestAlgo.algoName());
    }

    @Test
    void cmdElevator() {
        allocateAnElevator();
        assertEquals(-1,building.getElevetor(0).getState());
        assertEquals(1,building.getElevetor(1).getState());
        assertEquals(-1,building.getElevetor(2).getState());
        assertEquals(-1,building.getElevetor(3).getState());
        assertEquals(1,building.getElevetor(6).getState());

    }

    @Test
    void getBuildingTest(){
        assertEquals(building,bestAlgo.getBuilding());
        System.out.println("The building is:"+building);
    }
}