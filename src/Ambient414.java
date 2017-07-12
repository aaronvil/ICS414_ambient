import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Ambient project for ICS414 Summer 2017.
 * Created by Ryan Theriot and Aaron Jhumar Villanueva.
 * <p>
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
        Slider minSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 100, 50, W_WIDTH - 200, 20, "Min Value", 0.25f, 25000);
        Slider maxSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 200, 50, W_WIDTH - 200, 20, "Max Value", 0.75f, 25000);

        //Data Class for PineFlatData
        PineFlatData dataTest = new PineFlatData((int) (100 * minSlider.getSliderValue()), (int) (100 * maxSlider.getSliderValue()));

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
                if (minSlider.getSlider().isPointInElement(mouseX, mouseY)) {
                    if (maxSlider.getSlider().getXCenter() < minSlider.getSlider().getXCenter()) {
                        maxSlider.setSliderPosition(minSlider.getSlider().getXCenter() + 5);
                    }
                    minSlider.setSliderPosition(mouseX);
                    device.setColor(minSlider.getSliderValue());

                }

                //If mouse within brightness slider adjust slider and lamp brightness
                if (maxSlider.getSlider().isPointInElement(mouseX, mouseY)) {
                    if (minSlider.getSlider().getXCenter() > maxSlider.getSlider().getXCenter()) {
                        minSlider.setSliderPosition(maxSlider.getSlider().getXCenter() - 5);
                    }
                    maxSlider.setSliderPosition(mouseX);
                    device.setBrightness(maxSlider.getSliderValue());
                }

                dataTest.setMinMax((int)minSlider.getNormalizedSliderValue(), (int)maxSlider.getNormalizedSliderValue());
            }

            System.out.println(dataTest.getBrightnessValue());
        } //End of main program loop

        System.exit(0);

    }
}
