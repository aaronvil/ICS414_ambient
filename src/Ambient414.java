import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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
        EZText warning = EZ.addText(W_WIDTH / 2, 200, "Data Unavailable", Color.red, 35);
        AmbientDevice device = new AmbientDevice(W_WIDTH / 2, W_HEIGHT / 2 - 50, 250);
        Slider minSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 100, W_WIDTH - 200, 50, 20, "Min Value", 0, 25000, 12000);
        Slider maxSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 200, W_WIDTH - 200, 50, 20, "Max Value", 0, 25000, 18000);

        //Data Class for PineFlatData
        PineFlatData pineFlatData = new PineFlatData(minSlider.getSliderValue(), maxSlider.getSliderValue());

        //Variables ot be used within the main loop of program
        int mouseX;
        int mouseY;
        boolean leftMouseDown;
        boolean exit = false;

        //Setup a timer to update data every 5 minutes.
        //Used the following tutorial (http://singletonjava.blogspot.com/2016/02/java-timer-example.html)
        //First data retrieval should be performed in data's constructor (ie: PineFlatData)
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    pineFlatData.getData();
                } catch(IOException e) {};
            }
        };
        long fiveMins = 1000 * 60 * 5;
        timer.schedule(task, fiveMins, fiveMins);

        minSlider.setSliderPositionByValue(pineFlatData.getUserMin());
        maxSlider.setSliderPositionByValue(pineFlatData.getUserMax());

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
                    minSlider.setSliderPosition(mouseX);
                    device.setColor(pineFlatData.getBrightnessValue());
                    if (maxSlider.getSlider().getXCenter() < minSlider.getSlider().getXCenter()) {
                        maxSlider.setSliderPosition(minSlider.getSlider().getXCenter() + 1);
                    }
                }

                //If mouse within brightness slider adjust slider and lamp brightness
                if (maxSlider.getSlider().isPointInElement(mouseX, mouseY)) {
                    maxSlider.setSliderPosition(mouseX);
                    if (minSlider.getSlider().getXCenter() > maxSlider.getSlider().getXCenter()) {
                        minSlider.setSliderPosition(maxSlider.getSlider().getXCenter() - 1);
                    }
                }
                pineFlatData.setMinMax(minSlider.getSliderValue(), maxSlider.getSliderValue());
            }

            //Set Device Color to Orange if Data is bad
            //Show warning text just for debugging
            if(!pineFlatData.isDataGood()) {
                device.setBrightness(pineFlatData.getBrightnessValue());
                device.setColor(pineFlatData.getColorValue());
                warning.hide();
            }
            else {
                device.setColor(70);
                warning.show();
            }

        } //End of main program loop

        System.exit(0);

    }
}
