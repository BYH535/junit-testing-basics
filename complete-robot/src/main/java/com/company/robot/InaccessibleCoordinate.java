package com.company.robot;


public class InaccessibleCoordinate extends Exception {
    private Coordinates coordinate;

    public InaccessibleCoordinate(Coordinates coordinate) {
        this.coordinate = coordinate;
    }
}
