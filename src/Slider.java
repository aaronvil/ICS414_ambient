import java.awt.Color;

/**
 * Slider class used to create EZGraphics based UI slider objects.
 * Created by Ryan Theriot and Aaron Jhumar Villanueva.
 */
public class Slider {

    private int value;
    private float normalizedValue;
    private int length;
    private int xCenter, yCenter, xMin, xMax;
    private int minValue, maxValue;

    private EZRectangle sliderBackground;
    private EZCircle sliderKnob;
    private Color knobColor = new Color(0.33333334f, 0.64705884f, 1.0f, 1.0f);

    private EZText valueText;

    /**
     * Constructor for an EZGraphics based UI Slider.
     * Horizontal slider only currently.
     * @param xPosition X Coordinate position of the slider. This is the center of device.
     * @param yPosition Y Coordinate position of the slider. This is the center of device.
     * @param sliderLength Length of the whole slider
     * @param label Label text. Centered and above the slider
     * @param startValue Starting value of the slider.
     */
    public Slider(int xPosition, int yPosition, int sliderLength, String label, int minVal, int maxVal, int startValue) {
        xCenter = xPosition;
        yCenter = yPosition;
        length = sliderLength;
        maxValue = maxVal;
        minValue = minVal;
        value = startValue;

        if (value < minValue) value = minValue;
        if (value > maxValue) value = maxValue;
        normalizedValue = (float)(value - minValue) / (float)(maxValue - minValue);

        xMin = xCenter - (length / 2);
        xMax = xCenter + (length / 2);
        int knobXPos = xMin + (int)(normalizedValue * length);

        sliderBackground = EZ.addRectangle(xPosition, yPosition, this.length, 10, Color.gray, true);

        sliderKnob = EZ.addCircle(knobXPos, yCenter, 50, 50, knobColor, true);
        valueText = EZ.addText(knobXPos, yCenter, String.valueOf(value), Color.white, 20);
        EZ.addText(xCenter, yCenter - 50, label, Color.BLACK, (int) (20 * 1.25f));
    }

    /**
     * Getter for the slider value
     * @return The slider's current value
     */
    public int getSliderValue() {
        return value;
    }

    /**
     * Set the slider position on the screen.
     * This method is used to also set the slider value.
     * @param xPosition The x coordinate for the slider to be set to
     */
    public void setSliderPosition(int xPosition) {
        if (xPosition > xMax) xPosition = xMax;
        if (xPosition < xMin) xPosition = xMin;
        sliderKnob.translateTo(xPosition, sliderKnob.getYCenter());
        valueText.translateTo(xPosition, valueText.getYCenter());
        normalizedValue = (float)(xPosition - xMin) / (float)(xMax - xMin);
        float newValue = (float)(maxValue - minValue) * normalizedValue;
        setSliderValue((int)newValue);
    }

    /**
     * Method to se the position of the slider by value
     * Need to know the min and max values of your data to be used
     * @param updatedValue The new value the slider should be associated with
     */
    public void setSliderPositionByValue(int updatedValue) {
        if (updatedValue < minValue) updatedValue = minValue;
        if (updatedValue > maxValue) updatedValue = maxValue;
        normalizedValue = (float)(updatedValue - minValue) / (float)(maxValue - minValue);
        int knobXPos = xMin + (int)(normalizedValue * length);
        setSliderPosition(knobXPos);
    }

    /**
     * Private class to set the slider text value on the slider knob
     * Text will displays values between 0.0 and 100.0
     * @param value The value to set the knob to
     */
    private void setSliderValue(int value) {
        if (value > maxValue) value = maxValue;
        if (value < minValue) value = minValue;
        this.value = value;
        valueText.setMsg(String.valueOf(value));
    }

    public void resetSlider(int minVal, int maxVal, int currentValue) {
        maxValue = maxVal;
        minValue = minVal;
        value = currentValue;
        if (value < minValue) value = minValue;
        if (value > maxValue) value = maxValue;
        normalizedValue = (float)(value - minValue) / (float)(maxValue - minValue);
        xMin = xCenter - (length / 2);
        xMax = xCenter + (length / 2);
        int knobXPos = xMin + (int)(normalizedValue * length);
        setSliderPosition(knobXPos);
    }

    /**
     * Getter for the slider object
     * @return the slider knob object
     */
    public EZCircle getSlider() {
        return sliderKnob;
    }


}
