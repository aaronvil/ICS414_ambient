import java.awt.Color;

/**
 * Created by Ryan on 6/26/2017.
 */
public class AmbientDevice {

    private EZCircle lamp;
    private float hue;
    private float saturation;
    private float brightness;

    private Color lampColor;

    public AmbientDevice(int xPos, int yPos, int Radius) {
        hue = 180.0f;
        saturation = 1.0f;
        brightness = 1.0f;
        lampColor = Color.getHSBColor(hue, saturation, brightness);
        lamp = EZ.addCircle(xPos, yPos, Radius, Radius, lampColor, true);
    }

    public EZCircle getLamp() {
        return this.lamp;
    }

    public void setBrightness(float value) {
        if (value > 1.0f) value = 1.0f;
        if (value < 0.0f) value = 0.0f;
        this.brightness = value;
        lampColor = Color.getHSBColor(hue, saturation, value);
        lamp.setColor(lampColor);
    }

    public void setColor(float value) {
        if (value > 1.0f) value = 1.0f;
        if (value < 0.0f) value = 0.0f;
        this.hue = value;
        lampColor = Color.getHSBColor(value, saturation, brightness);
        lamp.setColor(lampColor);
    }

}