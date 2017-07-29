import java.awt.Color;

/**
 * A virtual representation of an ambient device lamp.
 * Created by Ryan Theriot and Aaron Jhumar Villanueva.
 */
public class AmbientDevice {

    private EZCircle lamp;
    private float hue;
    private float saturation;
    private float brightness;
    private Color lampColor;

    /**
     * Constructor class of the Ambient Device
     * @param xPos   X Coordinate position of the lamp. This is the center of device.
     * @param yPos   Y Coordinate position of the lamp. This is the center of device.
     * @param Radius The radius of the lamp.
     */
    public AmbientDevice(int xPos, int yPos, int Radius) {
        hue = 180.0f;
        saturation = 1.0f;
        brightness = 1.0f;
        lampColor = Color.getHSBColor(hue, saturation, brightness);
        lamp = EZ.addCircle(xPos, yPos, Radius, Radius, lampColor, true);
    }

    /**
     * Getter for the lamp EZCircle Object of the ambient device
     * @return lamp
     */
    public EZCircle getLamp() {
        return this.lamp;
    }

    /**
     * Setter for the brightness of the lamp with a value between 0-100
     * @param value Brightness of lamp
     */
    public void setBrightness(int value) {
        if (value > 100) value = 100;
        if (value < 0) value = 0;
        this.brightness = ((float) value / 100);
        lampColor = Color.getHSBColor(hue, saturation, this.brightness);
        lamp.setColor(lampColor);
    }

    /**
     * Setter for the color of the lamp with a value between 0-100
     * @param value Color of lamp
     */
    public void setColor(int value) {
        if (value > 100) value = 100;
        if (value < 0) value = 0;
        this.hue = ((float) value / 100);
        lampColor = Color.getHSBColor(this.hue, saturation, brightness);
        lamp.setColor(lampColor);
    }

    public float getBrightness() {
        return this.brightness;
    }

    public float getHue() {
        return this.hue;
    }

}