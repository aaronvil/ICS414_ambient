import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * Junit Test Class for AmbientDevice
 */
public class AmbientDeviceTest {

    @Test
    public void setBrightness() throws Exception {
        EZ.initialize(500, 500);
        AmbientDevice testDevice = new AmbientDevice(100, 100, 100);
        testDevice.setBrightness(0.5f);
        assertEquals(testDevice.getBrightness(), 0.5f, 0.001f);
        testDevice.setBrightness(1.1f);
        assertEquals(testDevice.getBrightness(), 1.0f, 0.001f);
        testDevice.setBrightness(-0.1f);
        assertEquals(testDevice.getBrightness(), 0.0f, 0.001f);
    }

    @Test
    public void setColor() throws Exception {
        EZ.initialize(500, 500);
        AmbientDevice testDevice = new AmbientDevice(100, 100, 100);
        testDevice.setColor(0.5f);
        assertEquals(testDevice.getHue(), 0.5f, 0.001f);
        testDevice.setColor(1.1f);
        assertEquals(testDevice.getHue(), 1.0f, 0.001f);
        testDevice.setColor(-0.1f);
        assertEquals(testDevice.getHue(), 0.0f, 0.001f);
    }

}