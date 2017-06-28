import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ryan on 6/27/17.
 */
public class AmbientDeviceTest {

    private AmbientDevice device = new AmbientDevice("Lamp");
    @Test
    public void getName() throws Exception {
        assertEquals("Lamp", device.getName());
    }

}