import java.awt.Color;

/**
 * Slider class used to create EZGraphics based UI slider objects.
 * Created by Ryan Theriot and Aaron Jhumar Villanueva.
 */
public class Slider {

    private float value;
    private int normalizedValue;
    private int length;
    private int xPos, yPos, xMin, xMax;

    private EZRectangle sliderBackground;
    private EZCircle sliderKnob;
    private Color knobColor = new Color(0.33333334f, 0.64705884f, 1.0f, 1.0f);

    private EZText valueText;

    /**
     * Constructor for an EZGraphics based UI Slider.
     * Horizontal slider only currently.
     * @param xPosition X Coordinate position of the slider. This is the center of device.
     * @param yPosition Y Coordinate position of the slider. This is the center of device.
     * @param radius Radius of the slider knob
     * @param length Length of the whole slider
     * @param fontSize Font size of the text within the slider
     * @param label Label text. Centered and above the slider
     * @param startValue Starting value of he slider
     */
    public Slider(int xPosition, int yPosition, int radius, int length, int fontSize, String label, float startValue, int normValue) {
        xPos = xPosition;
        yPos = yPosition;
        this.length = length;
        value = startValue;
        this.normalizedValue = normValue;

        if (value > 1.0f) value = 1.0f;
        if (value < 0.0f) value = 0.0f;

        xMin = xPos - (length / 2);
        xMax = xPos + (length / 2);
        int knobXPos = xMin + (int) (value * length);

        sliderBackground = EZ.addRectangle(xPosition, yPosition, this.length, 10, Color.gray, true);
        sliderKnob = EZ.addCircle(knobXPos, yPos, radius, radius, knobColor, true);
        valueText = EZ.addText(knobXPos, yPos, String.valueOf((int) (value * normalizedValue)), Color.white, fontSize);
        EZ.addText(knobXPos, yPos - radius, label, Color.BLACK, (int) (fontSize * 1.25f));
    }

    /**
     * Getter for the slider value
     * @return The slider's current value
     */
    public float getSliderValue() {
        return value;
    }

    public float getNormalizedSliderValue() { return value * normalizedValue; }

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
        float newValue = ((float) xPosition - xMin) / (xMax - xMin);
        setSliderValue(newValue);
    }

    /**
     * Private class to set the slider text value on the slider knob
     * Text will displays values between 0.0 and 100.0
     * @param value The value to set the knob to
     */
    private void setSliderValue(float value) {
        if (value > 1.0f) this.value = 1.0f;
        if (value < 0.0f) this.value = 0.0f;
        this.value = value;
        valueText.setMsg(String.format("%.1f", value * normalizedValue));
    }

    /**
     * Getter for the slider object
     * @return the slider knob object
     */
    public EZCircle getSlider() {
        return sliderKnob;
    }


}
