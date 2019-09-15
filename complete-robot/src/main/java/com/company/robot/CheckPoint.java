package com.company.robot;

class CheckPoint {

    public final Coordinates position;
    public final Direction direction;
    public final boolean manualDirective;

    public CheckPoint(Coordinates position, Direction direction, boolean manualDirective) {
        this.position = position;
        this.direction = direction;
        this.manualDirective = manualDirective;
    }

}
