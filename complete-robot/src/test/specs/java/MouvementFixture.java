import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import robot.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(ConcordionRunner.class)
public class MouvementFixture {

    Random random = new Random(){
        @Override
        public int nextInt() {
            return 5;
        }

        @Override
        public int nextInt(int i) {
            return 1;
        }
    };

    Robot wallE = new Robot(1, new Battery(){
        @Override
        public boolean canDeliver(double neededEnergy) {
            return true;
        }
    });

    public String directionAfterLanding() throws LandSensorDefaillance {
        wallE.land(new Coordinates(3, 3), new LandSensor(new Random()));
        return directionAsString();
    }

    private String directionAsString() {
        Direction direction;
        try {
             direction = wallE.getDirection();
        } catch (UnlandedRobotException e) {
            return e.getMessage();
        }
        switch (direction) {
            case NORTH: return "nord";
            case EAST: return "est";
            case SOUTH: return "sud";
            case WEST: return "ouest";

        }
        return "";
    }

    public String deplacement(String direction) throws UnlandedRobotException, InterruptedException {
        throw new RuntimeException("not yet implemented");
    }

    public String tourne(String sens)  throws UnlandedRobotException, InterruptedException {
        throw new RuntimeException("not yet implemented");
    }

    public void land(String landing, String direction) throws UnlandedRobotException, LandSensorDefaillance, InsufficientChargeException {
        String[] split = landing.replace('(', ' ').replace(')', ' ').trim().split(",");
        Coordinates departure = new Coordinates(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
        wallE.land(departure, new LandSensor(random));
        if (direction.contains("nord")) return;
        if (direction.contains("est")) wallE.turnLeft();
        if (direction.contains("ouest")) wallE.turnRight();
        if (direction.contains("sud")) {
            wallE.turnLeft(); wallE.turnLeft();}
    }


    public String instruction(String ordre) throws UnlandedRobotException, InterruptedException, InsufficientChargeException, LandSensorDefaillance, InaccessibleCoordinate {
        if (ordre.contains("up")) wallE.moveForward();
        if (ordre.contains("down")) wallE.moveBackward();
        if (ordre.contains("right")) wallE.turnRight();
        if (ordre.contains("left")) wallE.turnLeft();
        return "("+wallE.getXposition()+","+wallE.getYposition()+","+directionAsString()+")";
    }

    public void calculeEtape(String landing, String direction, String destination) throws UnlandedRobotException, InsufficientChargeException, LandSensorDefaillance, UndefinedRoadbookException {
        land(landing, direction);
        String[] split = destination.replace('(', ' ').replace(')', ' ').trim().split(",");
        Coordinates destinate = new Coordinates(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
        wallE.computeRoadTo(destinate);
    }

    public Iterable<String> getInstructions() {
        RoadBook roadBook = wallE.getRoadBook();
        List<String> resultat = new ArrayList<String>();
        while (roadBook.hasInstruction()) {
            switch (roadBook.next()) {
                case FORWARD: resultat.add("Aller en avant");
                               break;
                case TURNRIGHT: resultat.add("Tourner dans le sens des aiguilles d'une montre");
                    break;
                case BACKWARD: resultat.add("Aller en arrière");
                    break;
                case TURNLEFT: resultat.add("Tourner dans le sens inverse des aiguilles d'une montre");
            }

        }
        return resultat;
    }

}
