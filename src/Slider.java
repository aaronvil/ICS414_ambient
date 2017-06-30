import java.awt.Color;

/**
 * Created by Ryan on 6/29/2017.
 */
public class Slider {

    private float value;
    private int length;
    private int xPos, yPos, xMin, xMax;

    private EZRectangle sliderBackground;
    private EZCircle sliderKnob;
    private Color knobColor = new Color(0.33333334f, 0.64705884f, 1.0f, 1.0f);

    private EZText valueText;


    public Slider(int xPosition, int yPosition, int radius, int length, int fontSize, String label, float startValue) {
        xPos = xPosition;
        yPos = yPosition;
        this.length = length;
        value = startValue;

        if (value > 1.0f) value = 1.0f;
        if (value < 0.0f) value = 0.0f;

        xMin = xPos - (length / 2);
        xMax = xPos + (length / 2);
        int knobXPos = xMin + (int) (value * length);

        sliderBackground = EZ.addRectangle(xPosition, yPosition, this.length, 10, Color.gray, true);
        sliderKnob = EZ.addCircle(knobXPos, yPos, radius, radius, knobColor, true);
        valueText = EZ.addText(knobXPos, yPos, String.valueOf((int) (value * 100)), Color.white, fontSize);
        EZ.addText(knobXPos, yPos - radius, label, Color.BLACK, (int) (fontSize * 1.25f));
    }

    public float getSliderValue() {
        return value;
    }

    public void setSliderPosition(int mouseX) {
        if (mouseX > xMax) return;
        if (mouseX < xMin) return;
        sliderKnob.translateTo(mouseX, sliderKnob.getYCenter());
        valueText.translateTo(mouseX, valueText.getYCenter());
        float newValue = ((float) mouseX - xMin) / (xMax - xMin);
        setSliderValue(newValue);
    }

    private void setSliderValue(float value) {
        if (value > 1.0f) this.value = 1.0f;
        if (value < 0.0f) this.value = 0.0f;
        this.value = value;
        valueText.setMsg(String.format("%.1f", value * 100));
    }

    public EZCircle getSlider() {
        return sliderKnob;
    }


}
