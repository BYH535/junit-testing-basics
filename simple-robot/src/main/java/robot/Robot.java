package robot;

import java.util.ArrayList;
import java.util.List;

import static robot.Direction.*;
import static robot.Instruction.*;

public class Robot {

    private Coordinates position;
    private Direction direction;
    private boolean isLanded;
    private RoadBook roadBook;

    public Robot() {
        isLanded = false;
    }

    public void land(Coordinates landPosition) {
        position = landPosition;
        direction = NORTH;
        isLanded = true;
    }

    public int getXposition() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        return position.getX();
    }

    public int getYposition() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        return position.getY();
    }

    public Direction getDirection() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        return direction;
    }

    public void moveForward() throws UnlandedRobotException, InsufficientChargeException {
        if (!isLanded) throw new UnlandedRobotException();
        position = nextForwardPosition(position, direction);
    }

    public void moveBackward() throws UnlandedRobotException, InsufficientChargeException {
        if (!isLanded) throw new UnlandedRobotException();
        position = nextBackwardPosition(position, direction);
    }

    public void turnLeft() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        direction = counterclockwise(direction);
    }

    public void turnRight() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        direction = clockwise(direction);
    }

    private static Coordinates nextForwardPosition(Coordinates position, Direction direction) {
        if (direction == NORTH)
            return new Coordinates(position.getX(), position.getY() - 1);
        if (direction == SOUTH)
            return new Coordinates(position.getX(), position.getY() + 1);
        if (direction == EAST)
            return new Coordinates(position.getX() + 1, position.getY());
        return new Coordinates(position.getX() - 1, position.getY());
    }

    private static Coordinates nextBackwardPosition(Coordinates position, Direction direction) {
        if (direction == NORTH)
            return new Coordinates(position.getX(), position.getY() + 1);
        if (direction == SOUTH)
            return new Coordinates(position.getX(), position.getY() - 1);
        if (direction == EAST)
            return new Coordinates(position.getX() - 1, position.getY());
        return new Coordinates(position.getX() + 1, position.getY());
    }

    private static Direction counterclockwise(Direction direction) {
        if (direction == NORTH) return WEST;
        if (direction == WEST) return SOUTH;
        if (direction == SOUTH) return EAST;
        return NORTH;
    }

    private static Direction clockwise(Direction direction) {
        if (direction == NORTH) return EAST;
        if (direction == EAST) return SOUTH;
        if (direction == SOUTH) return WEST;
        return NORTH;
    }


    public void setRoadBook(RoadBook roadBook) {
        this.roadBook = roadBook;
    }

    public void letsGo() throws UnlandedRobotException, UndefinedRoadbookException, InsufficientChargeException {
        if (roadBook == null) throw new UndefinedRoadbookException();
        while (roadBook.hasInstruction()) {
            Instruction nextInstruction = roadBook.next();
            if (nextInstruction == FORWARD) moveForward();
            else if (nextInstruction == BACKWARD) moveBackward();
            else if (nextInstruction == TURNLEFT) turnLeft();
            else if (nextInstruction == TURNRIGHT) turnRight();
        }
    }

    public void computeRoadTo(Coordinates destination) throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        setRoadBook(calculateRoadBook(direction, position, destination, new ArrayList<Instruction>()));
    }

    private static RoadBook calculateRoadBook(Direction direction, Coordinates position, Coordinates destination, ArrayList<Instruction> instructions) {
        List<Direction> directionList = new ArrayList<Direction>();
        if (destination.getX() < position.getX()) directionList.add(WEST);
        if (destination.getX() > position.getX()) directionList.add(Direction.EAST);
        if (destination.getY() > position.getY()) directionList.add(Direction.SOUTH);
        if (destination.getY() < position.getY()) directionList.add(Direction.NORTH);
        if (directionList.isEmpty()) return new RoadBook(instructions);
        if (directionList.contains(direction)) {
            instructions.add(FORWARD);
            return calculateRoadBook(direction, nextForwardPosition(position, direction), destination, instructions);
        } else {
            instructions.add(TURNRIGHT);
            return calculateRoadBook(clockwise(direction), position, destination, instructions);
        }
    }

}
