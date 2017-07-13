import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Test class for the Slider
 * Created by Ryan on 7/13/17.
 */
public class SliderTest {
    @Test
    public void setSliderPosition() throws Exception {
        //Create the window and set the background color
        EZ.initialize(1000, 1000);
        EZ.setBackgroundColor(Color.LIGHT_GRAY);
        Slider testSlider = new Slider(1000 / 2, 1000 / 2, 500, 50, 20, "Min Value", 0, 1000, 0);
        testSlider.setSliderPosition(250);
        assertEquals(testSlider.getSliderValue(), 0);
        testSlider.setSliderPosition(500);
        assertEquals(testSlider.getSliderValue(), 500);
        testSlider.setSliderPosition(750);
        assertEquals(testSlider.getSliderValue(), 1000);
        testSlider.setSliderPosition(1250);
        assertEquals(testSlider.getSliderValue(), 1000);
        testSlider.setSliderPosition(-250);
        assertEquals(testSlider.getSliderValue(), 0);

    }

    @Test
    public void setSliderPositionByValue() throws Exception {
        //Create the window and set the background color
        EZ.initialize(1000, 1000);
        EZ.setBackgroundColor(Color.LIGHT_GRAY);
        Slider testSlider = new Slider(1000 / 2, 1000 / 2, 500, 50, 20, "Min Value", 0, 1000, 0);
        testSlider.setSliderPositionByValue(0);
        assertEquals(testSlider.getSlider().getXCenter(), 250);
        testSlider.setSliderPositionByValue(500);
        assertEquals(testSlider.getSlider().getXCenter(), 500);
        testSlider.setSliderPositionByValue(1000);
        assertEquals(testSlider.getSlider().getXCenter(), 750);
        testSlider.setSliderPositionByValue(1250);
        assertEquals(testSlider.getSlider().getXCenter(), 750);
        testSlider.setSliderPositionByValue(-250);
        assertEquals(testSlider.getSlider().getXCenter(), 250);
    }

}