import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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

        //Create the title, ambient device (virtual lamp)
        EZText title = EZ.addText(W_WIDTH / 2, 125, "Ambient Device", Color.BLACK, 50);
        EZText warning = EZ.addText(W_WIDTH / 2, 200, "Data Unavailable", Color.red, 35);
        AmbientDevice device = new AmbientDevice(W_WIDTH / 2, W_HEIGHT / 2 - 50, 250);

        //Data Class for PineFlatData that retrieves the PineFlat data
        PineFlatData pineFlatData = new PineFlatData();

        //Set up sliders so the user's can set the Min and Max Values
        //minVal and maxVal were given by the teacher for this data set
        Slider minSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 125, W_WIDTH - 200, 50, 20, "Min Value", 0, 25000, 0);
        Slider maxSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 225, W_WIDTH - 200, 50, 20, "Max Value", 0, 25000, 25000);

        //Set the values of sliders by the min and max values for the data
        pineFlatData.setIdealMinMax();
        minSlider.setSliderPositionByValue(pineFlatData.getMinValue());
        maxSlider.setSliderPositionByValue(pineFlatData.getMaxValue());

        //Setup button the user can press to set ideal values
        EZRectangle setIdealMinMaxButton = EZ.addRectangle(W_WIDTH / 2, W_HEIGHT - 50, 200, 50, new Color(0, 89, 239), true);
        EZ.addText(W_WIDTH / 2, W_HEIGHT - 50, "Reset Sliders", Color.white, 20);

        //Setup a timer to update data every 30 minutes.
        //First data retrieval should be performed in data's constructor (ie: PineFlatData)
        //Used the following tutorial (http://singletonjava.blogspot.com/2016/02/java-timer-example.html)
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    pineFlatData.getData();
                } catch (IOException e) {
                }
                ;
            }
        };
        long thirtyMins = 1000 * 60 * 30;
        timer.schedule(task, thirtyMins, thirtyMins);

        //Variables to be used within the main loop of program
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
                //Set the min and max of data set
                pineFlatData.setMinMax(minSlider.getSliderValue(), maxSlider.getSliderValue());

                //Check if the Rest Sliders button has been selected
                //This resets the sliders to an ideal position
                if (setIdealMinMaxButton.isPointInElement(mouseX, mouseY)) {
                    pineFlatData.setIdealMinMax();
                    minSlider.setSliderPositionByValue(pineFlatData.getMinValue());
                    maxSlider.setSliderPositionByValue(pineFlatData.getMaxValue());
                }
            }

            //Set Device color to GREEN if outflow is greater
            //Set Device color to RED if inflow is greater
            //Set Device color to Yellow if inflow==outflow
            //Set to BLUE if Data is bad
            //Show warning text just for debugging
            if (pineFlatData.isDataGood()) {
                //Removed brightness for now
                //device.setBrightness(pineFlatData.getBrightnessValue());
                device.setColor(pineFlatData.getColorValue());
                warning.hide();
            } else {
                device.setColor(70);
                warning.show();
            }

        } //End of main program loop

        System.exit(0);

    }
}
