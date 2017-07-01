import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Ambient project for ICS414 Summer 2017.
 * Created by Ryan Theriot and Aaron Jhumar Villanueva.
 *
 * Dylan Kobayashi 's EZGraphics library used for UI and graphics.
 */
public class Ambient414 {

    // Final Static Variables for EZWindow
    public static final int W_WIDTH = 800;
    public static final int W_HEIGHT = 800;

    public static void main(String[] args) {

        //Create the window and set the background color
        EZ.initialize(W_WIDTH, W_HEIGHT);
        EZ.setBackgroundColor(Color.LIGHT_GRAY);

        //Create the title, ambient device (virtual lamp), color slider and brightness slider
        EZText title = EZ.addText(W_WIDTH / 2, 150, "Ambient Device", Color.BLACK, 50);
        AmbientDevice device = new AmbientDevice(W_WIDTH / 2, W_HEIGHT / 2 - 50, 250);
        Slider colorSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 100, 50, W_WIDTH - 200, 20, "Color", 0.5f);
        Slider brightnessSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 200, 50, W_WIDTH - 200, 20, "Brightness", 0.5f);

        //Variables ot be used within the main loop of program
        int mouseX;
        int mouseY;
        boolean leftMouseDown;
        boolean exit = false;

        //Main program loop
        while (!exit) {

            //Refresh screen and variable values
            EZ.refreshScreen();
            mouseX = EZInteraction.getXMouse();
            mouseY = EZInteraction.getYMouse();
            leftMouseDown = EZInteraction.isMouseLeftButtonDown();

            //Exit program if user presses ESC
            if (EZInteraction.wasKeyPressed(KeyEvent.VK_ESCAPE))
                exit = true;

            //If the mouse button is down user maybe adjust slider
            if (leftMouseDown) {

                //If mouse within color slider adjust slider and lamp color
                if (colorSlider.getSlider().isPointInElement(mouseX, mouseY)) {
                    colorSlider.setSliderPosition(mouseX);
                    device.setColor(colorSlider.getSliderValue());
                }

                //If mouse within brightness slider adjust slider and lamp brightness
                if (brightnessSlider.getSlider().isPointInElement(mouseX, mouseY)) {
                    brightnessSlider.setSliderPosition(mouseX);
                    device.setBrightness(brightnessSlider.getSliderValue());
                }
            }
        } //End of main program loop

        System.exit(0);

    }
}
