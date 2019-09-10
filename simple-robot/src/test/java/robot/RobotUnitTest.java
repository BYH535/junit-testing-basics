package robot;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static robot.Direction.NORTH;

public class RobotUnitTest {

    @Test
    public void testLand() throws UnlandedRobotException {
        //---DEFINE---
        Robot robot = new Robot();
        //---WHEN---
        robot.land(new Coordinates(3,0));
        //---THEN---
        Assert.assertEquals(NORTH, robot.getDirection());
        Assert.assertEquals(3, robot.getXposition());
        Assert.assertEquals(0, robot.getYposition());
    }

    // tester l'apparition d'une exception, l'annotation @Test intègre expected suivi de la classe de l'exception attendue
    // Attention : il est parfois nécessaire de s'assurer que l'exception n'apparaît pas avant la dernière instruction du test
    @Test (expected = UnlandedRobotException.class)
    public void testRobotMustBeLandedBeforeMoveForward() throws Exception {
        Robot robot = new Robot();
        robot.moveForward();
    }

    @Test
    public void testMoveForward() throws Exception {
        //---DEFINE---
        Robot robot = new Robot();
        robot.land(new Coordinates(5, 5));
        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();
        //---WHEN---
        robot.moveForward();
        //---THEN---
        // TODO : complétez ce test
    }

    @Test
    public void testMoveBackward() throws Exception {
        //---DEFINE---
        Robot robot = new Robot();
        robot.land(new Coordinates(3, 0));
        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();
        //---WHEN---
        // TODO : complétez ce test
        //---THEN---
        Assert.assertEquals(currentXposition, robot.getXposition());
        Assert.assertEquals(currentYposition+1, robot.getYposition());
    }

    @Test
    public void testTurnLeft() throws Exception {
        // TODO : complétez ce test
    }

    @Test
    public void testTurnRight() throws Exception {
        // TODO : complétez ce test
    }

    @Test
    public void testLetsGoWithoutRoadbook() throws Exception {
        // TODO : complétez ce test
        Robot robot = new Robot();
        robot.land(new Coordinates(3, 0));
        robot.letsGo();
    }

    @Test
    public void testLetsGo() throws Exception {
        // TODO : complétez ce test
        Robot robot = new Robot();
        robot.land(new Coordinates(5, 7));
        robot.setRoadBook(new RoadBook(Arrays.asList(Instruction.FORWARD, Instruction.FORWARD, Instruction.TURNLEFT, Instruction.FORWARD)));
        robot.letsGo();
    }

    @Test (expected = UnlandedRobotException.class)
    public void testComputeRoadToWithUnlandedRobot() throws Exception {
        Robot robot = new Robot();
        // TODO : complétez ce test
    }

    @Test
    public void testComputeRoadTo() throws UnlandedRobotException {
        // TODO : complétez ce test
        Robot robot = new Robot();
        robot.land(new Coordinates(3, 0));
        robot.computeRoadTo(new Coordinates(7, 5));
    }
}
