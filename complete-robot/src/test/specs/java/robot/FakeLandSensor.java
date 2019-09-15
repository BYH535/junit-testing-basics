package robot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FakeLandSensor extends LandSensor{


    public FakeLandSensor(Random random) {
        super(random);
    }

    public void setLand(Coordinates coordinates, Land land) {
        carte.put(coordinates, land);
    }


}
