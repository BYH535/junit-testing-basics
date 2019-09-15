import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import robot.*;

import java.util.Random;

@RunWith(ConcordionRunner.class)
public class CartographieFixture {

    FakeLandSensor sensor = new FakeLandSensor(new Random());

    public void setCarte(String terrain) {
        String[] split = terrain.split(",");
        int x = Integer.valueOf(split[0]);
        int y = Integer.valueOf(split[1]);

        Coordinates coordinates = new Coordinates(x, y);
        sensor.setLand(coordinates, landFromString(split[2]));

    }

    private Land landFromString(String terrain) {
        if (terrain.equals("s")) return Land.Sable;
        if (terrain.equals("r")) return Land.Roche;
        if (terrain.equals("t")) return Land.Terre;
        if  (terrain.equals("b")) return Land.Boue;
        if  (terrain.endsWith("i")) return Land.Infranchissable;
        return Land.Terre;
    }

    public void setRobotCarte(String terrain) {
        setCarte(terrain);

    }

}
