package com.company.robot;

import java.util.*;

import static com.company.robot.Direction.*;
import static com.company.robot.Instruction.*;
import static com.company.robot.MapTools.clockwise;
import static com.company.robot.MapTools.nextForwardPosition;

public class RoadBookCalculator {

    static RoadBook calculateRoadBook(Direction direction, Coordinates position, Coordinates destination, ArrayList<Instruction> instructions) {
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
            return calculateRoadBook(MapTools.clockwise(direction), position, destination, instructions);
        }
    }

    static RoadBook calculateRoadBook(LandSensor sensor, Direction direction, Coordinates position, Coordinates destination, List<Instruction> instructions, List<Coordinates> trace) throws LandSensorDefaillance, UndefinedRoadbookException {
        if (position.equals(destination)) return new RoadBook(InstructionListTool.compacte(instructions));
        List<Direction> directionList = new ArrayList<Direction>();
        if (destination.getX() < position.getX()) directionList.add(WEST);
        if (destination.getX() > position.getX()) directionList.add(Direction.EAST);
        if (destination.getY() > position.getY()) directionList.add(Direction.SOUTH);
        if (destination.getY() < position.getY()) directionList.add(Direction.NORTH);
        List<Direction> directionsToExplored = new ArrayList<Direction>(Arrays.asList(NORTH, SOUTH, EAST, WEST));
        directionsToExplored.remove(Direction.oppositeDirection(direction));
        directionList.remove(Direction.oppositeDirection(direction));
        while (!directionsToExplored.isEmpty()) {
            if (directionList.contains(direction) && sensor.isAccessible(nextForwardPosition(position, direction)) && !trace.contains(nextForwardPosition(position,direction))) {
                try {
                    directionsToExplored.remove(direction);
                    directionList.remove(direction);
                    Coordinates nextPos = nextForwardPosition(position, direction);
                    return calculateRoadBook(sensor, direction, nextPos, destination, InstructionListTool.concatene(instructions, FORWARD), InstructionListTool.concatene(trace, nextPos));
                } catch (UndefinedRoadbookException e) {
                }
            } else if (directionList.contains(direction)) { // !sensor.isAccessible(nextForwardPosition(position, direction)
                directionsToExplored.remove(direction);
                directionList.remove(direction);
                instructions.add(TURNRIGHT);
                direction = clockwise(direction);
            } else if (!directionList.isEmpty()) {
                instructions.add(TURNRIGHT);
                direction = clockwise(direction);
            } else if (directionList.isEmpty() && directionsToExplored.contains(direction) && sensor.isAccessible(nextForwardPosition(position, direction)) &&!trace.contains(nextForwardPosition(position,direction))) {
                try {
                    directionsToExplored.remove(direction);
                    Coordinates nextPos = nextForwardPosition(position, direction);
                    return calculateRoadBook(sensor, direction, nextPos, destination, InstructionListTool.concatene(instructions, FORWARD), InstructionListTool.concatene(trace, nextPos));
                } catch (UndefinedRoadbookException e) {
                }
            } else if (directionList.isEmpty() && directionsToExplored.contains(direction)) {
                directionsToExplored.remove(direction);
                instructions.add(TURNRIGHT);
                direction = clockwise(direction);
            } else if (directionList.isEmpty()) {
                instructions.add(TURNRIGHT);
                direction = clockwise(direction);
            }
        }
        throw new UndefinedRoadbookException();
    }


}