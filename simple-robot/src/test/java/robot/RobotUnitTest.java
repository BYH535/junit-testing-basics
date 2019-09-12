package robot;

import com.company.simple.robot.Coordinates;
import com.company.simple.robot.Direction;
import com.company.simple.robot.Robot;
import com.company.simple.robot.UndefinedRoadbookException;
import com.company.simple.robot.Instruction;
import com.company.simple.robot.InsufficientChargeException;
import com.company.simple.robot.UnlandedRobotException;
import com.company.simple.robot.RoadBook;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.company.simple.robot.Direction.NORTH;

public class RobotUnitTest {

    @Test(expected = UnlandedRobotException.class)
    public void testGetXUnlandedRobot() throws UnlandedRobotException {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.getXposition();
    }

    @Test(expected = UnlandedRobotException.class)
    public void testGetYUnlandedRobot() throws UnlandedRobotException {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.getYposition();
    }

    @Test(expected = UnlandedRobotException.class)
    public void testGetDirectionsUnlandedRobot() throws UnlandedRobotException{
        Robot robot = new Robot();

        robot.getDirection();
    }

    @Test
    public void testLand() throws UnlandedRobotException {
        // ---DEFINE---
        Robot robot = new Robot();
        // ---WHEN---
        robot.land(new Coordinates(3, 0));
        // ---THEN---
        Assert.assertEquals(NORTH, robot.getDirection());
        Assert.assertEquals(3, robot.getXposition());
        Assert.assertEquals(0, robot.getYposition());
    }

    // Testing the expected exception
    @Test(expected = UnlandedRobotException.class)
    public void testRobotMustBeLandedBeforeMoveForward() throws Exception {
        Robot robot = new Robot();
        robot.moveForward();
    }

    @Test(expected = UnlandedRobotException.class)
    public void testRobotMustBeLandedBeforeMoveBackward() throws Exception {
        Robot robot = new Robot();
        robot.moveBackward();
    }

    @Test
    public void testMoveForward() throws Exception {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.land(new Coordinates(5, 5));

        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();

        // ---WHEN---
        robot.moveForward();

        // ---THEN---
        Assert.assertEquals(currentXposition, robot.getXposition());
        Assert.assertEquals(currentYposition - 1, robot.getYposition());
    }
    @Test
    public void testForwardInEveryDirection() throws UnlandedRobotException, InsufficientChargeException{
        Robot robot = new Robot();

        robot.land(new Coordinates(5, 5));

        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();
        
        robot.turnLeft();
        robot.moveForward();

        Assert.assertEquals(currentXposition - 1, robot.getXposition());
        Assert.assertEquals(currentYposition, robot.getYposition());
    }

    @Test
    public void testMoveBackward() throws Exception {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.land(new Coordinates(3, 0));

        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();

        // ---WHEN---
        robot.moveBackward();

        // ---THEN---
        Assert.assertEquals(currentXposition, robot.getXposition());
        Assert.assertEquals(currentYposition + 1, robot.getYposition());
    }

    @Test
    public void testMoveBackwardInEveryDirection() throws Exception {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.land(new Coordinates(3, 0));

        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();

        // ---WHEN---
        robot.moveBackward();

        robot.turnLeft();
        robot.moveBackward();

        robot.turnLeft();
        robot.moveBackward();

        robot.turnLeft();
        robot.moveBackward();

        robot.turnLeft();
        robot.moveBackward();

        // ---THEN---
        Assert.assertEquals(currentXposition, robot.getXposition());
        Assert.assertEquals(currentYposition + 1, robot.getYposition());
    }

    @Test(expected = UnlandedRobotException.class)
    public void testTurnLeftBeforeLanding() throws Exception{
        Robot robot = new Robot();

        robot.turnLeft();
    }

    @Test(expected = UnlandedRobotException.class)
    public void testTurnRightBeforeLanding() throws Exception{
        Robot robot = new Robot();

        robot.turnRight();
    }

    @Test
    public void testTurnLeft() throws Exception {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.land(new Coordinates(3, 0));

        // ---WHEN---
        robot.turnLeft();

        // ---THEN---
        Assert.assertEquals(robot.getDirection(), Direction.WEST);
    }

    @Test
    public void testTurnRight() throws Exception {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.land(new Coordinates(3, 0));

        // ---WHEN---
        robot.turnRight();
        robot.turnRight();
        robot.turnRight();

        // ---THEN---
        Assert.assertEquals(robot.getDirection(), Direction.WEST);
    }

    @Test(expected = UndefinedRoadbookException.class)
    public void testLetsGoWithoutRoadbook() throws Exception {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.land(new Coordinates(3, 0));

        // ---WHEN---
        robot.letsGo();
    }

    @Test
    public void testLetsGo() throws Exception {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.land(new Coordinates(5, 7));
        robot.setRoadBook(new RoadBook(
                Arrays.asList(Instruction.FORWARD, Instruction.BACKWARD, Instruction.TURNLEFT,
                 Instruction.BACKWARD, Instruction.TURNRIGHT, Instruction.BACKWARD)));

        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();

        // ---WHENE---
        robot.letsGo();

        // ---THEN---
        Assert.assertEquals(currentXposition + 1 , robot.getXposition());
        Assert.assertEquals(currentYposition + 1, robot.getYposition());
    }

    @Test(expected = UnlandedRobotException.class)
    public void testComputeRoadToWithUnlandedRobot() throws Exception {
        // ---DEFINE---
        Robot robot = new Robot();
        // ---WHENE---
        robot.computeRoadTo(new Coordinates(7, 5));

    }

    @Test
    public void testComputeRoadToBiggerCoordinates() throws UnlandedRobotException {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.land(new Coordinates(3, 0));

        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();

        // ---WHENE---
        robot.computeRoadTo(new Coordinates(7, 5));

        // ---THEN---
        Assert.assertEquals(currentXposition + 4, 7);
        Assert.assertEquals(currentYposition + 5, 5);
    }

    @Test
    public void testComputeRoadToSmallerCoordinates() throws UnlandedRobotException {
        // ---DEFINE---
        Robot robot = new Robot();

        robot.land(new Coordinates(7, 5));

        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();

        // ---WHENE---
        robot.computeRoadTo(new Coordinates(3, 0));

        // ---THEN---
        Assert.assertEquals(currentXposition - 4, 3);
        Assert.assertEquals(currentYposition - 5, 0);
    }
}
