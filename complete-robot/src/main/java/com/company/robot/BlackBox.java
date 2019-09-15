package com.company.robot;

import java.util.ArrayList;
import java.util.List;

public class BlackBox {

    private List<CheckPoint> checkPointList;

    public BlackBox() {
        checkPointList = new ArrayList<CheckPoint>();
    }

    public void addCheckPoint(Coordinates position, Direction direction, boolean manualDirective) {
        checkPointList.add(new CheckPoint(position, direction, manualDirective));
    }

    public void addCheckPoint(CheckPoint checkPoint) {
        checkPointList.add(checkPoint);
    }

    public List<CheckPoint> getCheckPointList() {
        return checkPointList;
    }
}
