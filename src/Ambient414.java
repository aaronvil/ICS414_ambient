import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Created by Ryan on 6/26/2017.
 */
public class Ambient414 {

    // Final Static Variables for EZWindow
    public static final int W_WIDTH = 800;
    public static final int W_HEIGHT = 800;

    public static void main(String[] args) {

        EZ.initialize(W_WIDTH, W_HEIGHT);
        EZ.setBackgroundColor(Color.LIGHT_GRAY);

        EZText title = EZ.addText(W_WIDTH / 2, 150, "Ambient Device", Color.BLACK, 50);

        AmbientDevice device = new AmbientDevice(W_WIDTH / 2, W_HEIGHT / 2 - 50, 250);

        Slider colorSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 100, 50, W_WIDTH - 200, 20, "Color", 0.5f);
        Slider brightnessSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 200, 50, W_WIDTH - 200, 20, "Brightness", 0.5f);

        int mouseX;
        int mouseY;
        boolean leftMouseDown;
        boolean exit = false;

        while (!exit) {

            EZ.refreshScreen();
            mouseX = EZInteraction.getXMouse();
            mouseY = EZInteraction.getYMouse();
            leftMouseDown = EZInteraction.isMouseLeftButtonDown();

            if (EZInteraction.wasKeyPressed(KeyEvent.VK_ESCAPE))
                exit = true;

            if (leftMouseDown) {
                if (colorSlider.getSlider().isPointInElement(mouseX, mouseY)) {
                    colorSlider.setSliderPosition(mouseX);
                    device.setColor(colorSlider.getSliderValue());
                }

                if (brightnessSlider.getSlider().isPointInElement(mouseX, mouseY)) {
                    brightnessSlider.setSliderPosition(mouseX);
                    device.setBrightness(brightnessSlider.getSliderValue());
                }
            }
        }

        System.exit(0);
    }

}

