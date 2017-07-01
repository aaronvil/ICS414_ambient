import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Junit Test Class for Slider
 */
public class SliderTest {

    @Test
    public void setSliderPosition() throws Exception {
        EZ.initialize(500,500);
        Slider testSlider = new Slider(500/2, 500/2, 100, 400, 20, "TestDevice", 0.25f);
        testSlider.setSliderPosition(300);
        assertEquals(testSlider.getSlider().getXCenter(), 300);
        testSlider.setSliderPosition(25);
        assertEquals(testSlider.getSlider().getXCenter(), 50);
        testSlider.setSliderPosition(475);
        assertEquals(testSlider.getSlider().getXCenter(), 450);
    }

}