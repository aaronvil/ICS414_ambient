import java.awt.Color;
import java.awt.event.KeyEvent;

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

        //Setup PineFlat Flow Source
        PineFlatWaterFlow pineFlatFlow = new PineFlatWaterFlow(5);
        pineFlatFlow.setIdealMinMax();
        EZRectangle flowSelector = EZ.addRectangle(100, 50, 100, 50, new Color(80, 239, 174), true);
        EZ.addText(100, 50, "Flow", Color.white, 20);

        //Setup PineFlat Tamp Source
        PineFlatWaterTemp pineFlatTemp = new PineFlatWaterTemp(5);
        pineFlatTemp.setIdealMinMax();
        EZRectangle tempSelector = EZ.addRectangle(300, 50, 100, 50, new Color(122, 149, 239), true);
        EZ.addText(300, 50, "Temp", Color.white, 20);

        //Setup PineFlat Third Source
        EZRectangle thirdSelector = EZ.addRectangle(500, 50, 100, 50, new Color(239, 80, 120), true);
        EZ.addText(500, 50, "Third", Color.white, 20);

        //Setup PineFlat Fourth Source
        BingTraffic bingTraffic = new BingTraffic(5);
        bingTraffic.setIdealMinMax();
        EZRectangle bingSelector = EZ.addRectangle(700, 50, 100, 50, new Color(239, 205, 66), true);
        EZ.addText(700, 50, "Bing Traffic", Color.white, 20);


        //Set the current data source to Pine Flat
        DataSource currentSource;
        currentSource = pineFlatFlow;

        //Set up sliders so the user's can set the Min and Max Values
        Slider minSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 125, W_WIDTH - 200, "Min Value", currentSource.getMin(), currentSource.getMax(), currentSource.getMinValue());
        Slider maxSlider = new Slider(W_WIDTH / 2, W_HEIGHT - 225, W_WIDTH - 200, "Max Value", currentSource.getMin(), currentSource.getMax(), currentSource.getMaxValue());

        //Setup button the user can press to set ideal values
        EZRectangle setIdealMinMaxButton = EZ.addRectangle(W_WIDTH / 2, W_HEIGHT - 50, 200, 50, new Color(0, 89, 239), true);
        EZ.addText(W_WIDTH / 2, W_HEIGHT - 50, "Reset Sliders", Color.white, 20);

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

            //If the mouse button is down
            if (leftMouseDown) {

                if (flowSelector.isPointInElement(mouseX, mouseY)) {
                    currentSource = pineFlatFlow;
                    minSlider.resetSlider(currentSource.getMin(), currentSource.getMax(), currentSource.getMinValue());
                    maxSlider.resetSlider(currentSource.getMin(), currentSource.getMax(), currentSource.getMaxValue());
                }

                if (tempSelector.isPointInElement(mouseX, mouseY)) {
                    currentSource = pineFlatTemp;
                    minSlider.resetSlider(currentSource.getMin(), currentSource.getMax(), currentSource.getMinValue());
                    maxSlider.resetSlider(currentSource.getMin(), currentSource.getMax(), currentSource.getMaxValue());
                }

                if (bingSelector.isPointInElement(mouseX, mouseY)) {
                    currentSource = bingTraffic;
                    minSlider.resetSlider(currentSource.getMin(), currentSource.getMax(), currentSource.getMinValue());
                    maxSlider.resetSlider(currentSource.getMin(), currentSource.getMax(), currentSource.getMaxValue());
                }

                //If mouse within color slider adjust slider and lamp color
                if (minSlider.getSlider().isPointInElement(mouseX, mouseY)) {
                    minSlider.setSliderPosition(mouseX);
                    device.setColor(currentSource.getBrightnessValue());
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
                currentSource.setMinMax(minSlider.getSliderValue(), maxSlider.getSliderValue());

                //Check if the Rest Sliders button has been selected
                //This resets the sliders to an ideal position
                if (setIdealMinMaxButton.isPointInElement(mouseX, mouseY)) {
                    currentSource.setIdealMinMax();
                    minSlider.setSliderPositionByValue(currentSource.getMinValue());
                    maxSlider.setSliderPositionByValue(currentSource.getMaxValue());
                }
            }

            //Set Device color to GREEN if outflow is greater
            //Set Device color to RED if inflow is greater
            //Set Device color to Yellow if inflow==outflow
            //Set to BLUE if Data is bad
            //Show warning text just for debugging
            if (currentSource.isDataGood()) {
                //Removed brightness for now
                device.setBrightness(currentSource.getBrightnessValue());
                device.setColor(currentSource.getColorValue());
                warning.hide();
            } else {
                device.setColor(70);
                warning.show();
            }

        } //End of main program loop

        System.exit(0);

    }
}
