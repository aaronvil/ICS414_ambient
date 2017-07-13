import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Test class for the Ambient Device
 * Created by Ryan on 7/13/17.
 */
public class AmbientDeviceTest {

    @Test
    public void setBrightness() throws Exception {
        EZ.initialize(500, 500);
        EZ.setBackgroundColor(Color.LIGHT_GRAY);
        AmbientDevice testDevice = new AmbientDevice(250, 250, 100);
        testDevice.setBrightness(0);
        assertEquals(testDevice.getBrightness(), 0.0f, 0.05f);
        testDevice.setBrightness(50);
        assertEquals(testDevice.getBrightness(), 0.5f, 0.05f);
        testDevice.setBrightness(100);
        assertEquals(testDevice.getBrightness(), 1.0f, 0.05f);
        testDevice.setBrightness(-50);
        assertEquals(testDevice.getBrightness(), 0.0f, 0.05f);
        testDevice.setBrightness(150);
        assertEquals(testDevice.getBrightness(), 1.0f, 0.05f);
    }

    @Test
    public void setColor() throws Exception {
        EZ.initialize(500, 500);
        EZ.setBackgroundColor(Color.LIGHT_GRAY);
        AmbientDevice testDevice = new AmbientDevice(250, 250, 100);
        testDevice.setColor(0);
        assertEquals(testDevice.getHue(), 0.0f, 0.05f);
        testDevice.setColor(50);
        assertEquals(testDevice.getHue(), 0.5f, 0.05f);
        testDevice.setColor(100);
        assertEquals(testDevice.getHue(), 1.0f, 0.05f);
        testDevice.setColor(-50);
        assertEquals(testDevice.getHue(), 0.0f, 0.05f);
        testDevice.setColor(150);
        assertEquals(testDevice.getHue(), 1.0f, 0.05f);
    }

}