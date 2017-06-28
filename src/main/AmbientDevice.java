
import java.awt.Color;
/**
 * Created by Ryan on 6/26/2017.
 */
public class AmbientDevice {

    public EZCircle lamp;

    public EZRectangle colorSlider;
    public EZRectangle brightnessSlider;
    private Color sliderColor = new Color(0.33333334f, 0.64705884f, 1.0f, 1.0f);

    private float hue;
    private float saturation;
    private float brightness;

    private Color lampColor;

    public AmbientDevice() {

        hue = 180.0f;
        saturation = 1.0f;
        brightness = 1.0f;
        lampColor = Color.getHSBColor(hue, saturation, brightness);

        lamp = EZ.addCircle(EZ.getWindowWidth()/2, EZ.getWindowHeight()/2, 200, 200, lampColor, true);


        EZ.addRectangle(EZ.getWindowWidth()/2, EZ.getWindowHeight() - 100, EZ.getWindowWidth() - 200, 10, Color.gray, true);
        colorSlider = EZ.addRectangle(EZ.getWindowWidth()/2, EZ.getWindowHeight() - 100, 50, 50, sliderColor, true);

        EZ.addRectangle(EZ.getWindowWidth()/2, EZ.getWindowHeight() - 200, EZ.getWindowWidth() - 200, 10, Color.gray, true);
        brightnessSlider = EZ.addRectangle(EZ.getWindowWidth()/2, EZ.getWindowHeight() - 200, 50, 50, sliderColor, true);
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
        this.hue = value;
        lampColor = Color.getHSBColor(value, saturation, brightness);
        lamp.setColor(lampColor);
    }

    public EZRectangle getColorSlider() {
        return this.colorSlider;
    }

    public EZRectangle getBrightnessSlider() {
        return this.brightnessSlider;
    }

}