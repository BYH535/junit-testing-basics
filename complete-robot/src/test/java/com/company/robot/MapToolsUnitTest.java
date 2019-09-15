package com.company.robot;

import org.junit.Assert;
import org.junit.Test;

import static com.company.robot.Direction.*;

public class MapToolsUnitTest {
    @Test
    public void testNextForwardPositionNorth() {
        Coordinates position = MapTools.nextForwardPosition(new Coordinates(0, 0), NORTH);
        Assert.assertEquals(0, position.getX());
        Assert.assertEquals(-1, position.getY());
    }

    @Test
    public void testNextForwardPositionSouth() {
        Coordinates position = MapTools.nextForwardPosition(new Coordinates(0, 0), SOUTH);
        Assert.assertEquals(0, position.getX());
        Assert.assertEquals(1, position.getY());
    }

    @Test
    public void testNextForwardPositionEast() {
        Coordinates position = MapTools.nextForwardPosition(new Coordinates(0, 0), EAST);
        Assert.assertEquals(1, position.getX());
        Assert.assertEquals(0, position.getY());
    }

    @Test
    public void testNextForwardPositionWest() {
        Coordinates position = MapTools.nextForwardPosition(new Coordinates(0, 0), WEST);
        Assert.assertEquals(-1, position.getX());
        Assert.assertEquals(0, position.getY());
    }

    @Test
    public void testNextBackwardPositionNorth() {
        Coordinates position = MapTools.nextBackwardPosition(new Coordinates(0, 0), NORTH);
        Assert.assertEquals(0, position.getX());
        Assert.assertEquals(1, position.getY());
    }

    @Test
    public void testNextBackwardPositionSouth() {
        Coordinates position = MapTools.nextBackwardPosition(new Coordinates(0, 0), SOUTH);
        Assert.assertEquals(0, position.getX());
        Assert.assertEquals(-1, position.getY());
    }

    @Test
    public void testNextBackwardPositionEast() {
        Coordinates position = MapTools.nextBackwardPosition(new Coordinates(0, 0), EAST);
        Assert.assertEquals(-1, position.getX());
        Assert.assertEquals(0, position.getY());
    }

    @Test
    public void testNextBackwardPositionWest() {
        Coordinates position = MapTools.nextBackwardPosition(new Coordinates(0, 0), WEST);
        Assert.assertEquals(1, position.getX());
        Assert.assertEquals(0, position.getY());
    }

    @Test
    public void testCounterclockwise() {
        Assert.assertEquals(WEST, MapTools.counterclockwise(NORTH));
        Assert.assertEquals(SOUTH, MapTools.counterclockwise(WEST));
        Assert.assertEquals(NORTH, MapTools.counterclockwise(EAST));
        Assert.assertEquals(EAST, MapTools.counterclockwise(SOUTH));
    }

    @Test
    public void testClockwise() {
        Assert.assertEquals(WEST, MapTools.clockwise(SOUTH));
        Assert.assertEquals(SOUTH, MapTools.clockwise(EAST));
        Assert.assertEquals(NORTH, MapTools.clockwise(WEST));
        Assert.assertEquals(EAST, MapTools.clockwise(NORTH));
    }


}
