package robot;

import com.company.simple.robot.Battery;
import com.company.simple.robot.InsufficientChargeException;

import org.junit.Assert;
import org.junit.Test;


public class BatteryUnitTest {
    
    @Test
    public void testCharche(){
        Battery battery = new Battery();

        battery.charge();

        Assert.assertEquals(110, battery.getChargeLevel(), 0.01);
    }

    @Test
    public void testSetUp(){
        Battery battery = new Battery();

        battery.setUp();

        Assert.assertEquals(100, battery.getChargeLevel(), 0.01);        
    }

    @Test(expected = InsufficientChargeException.class)
    public void testUseEnergy() throws InsufficientChargeException{
        Battery battery = new Battery();

        battery.use(10);

        Assert.assertEquals(90, battery.getChargeLevel(), 0.01);   
        
        battery.use(100);

    }

    @Test
    public void testTimeToSufficientCharge() throws InsufficientChargeException{
        Battery battery = new Battery();

        battery.use(50);

       Assert.assertEquals(5000, battery.timeToSufficientCharge(100));
    }

    @Test
    public void testCanDeliver(){
        Battery battery = new Battery();

        Assert.assertEquals(true, battery.canDeliver(80));
        Assert.assertEquals(false, battery.canDeliver(800));
    }
}
