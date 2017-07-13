import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the PineFlatData class
 * Created by Ryan on 7/13/17.
 */
public class PineFlatDataTest {

    @Test
    public void setIdealMinMax() throws Exception {
        PineFlatData testPineFlat = new PineFlatData();
        int testOut = testPineFlat.getCurrentOutflowValue();
        int testIn = testPineFlat.getCurrentInflowValue();
        testPineFlat.setIdealMinMax();
        int idealVal = ((testOut + testIn) / 2) - (Math.abs(testOut - testIn));
        assertEquals(testPineFlat.getMinValue(), idealVal);
        idealVal = ((testOut + testIn) / 2) + (Math.abs(testOut - testIn));
        assertEquals(testPineFlat.getMaxValue(), idealVal);


    }

}