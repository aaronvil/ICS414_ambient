import java.awt.Color;

/**
 * Created by Ryan on 6/26/2017.
 */
public class Ambient414 {

    public static void main(String []args) {

        EZ.initialize(800,800);
        AmbientDevice device = new AmbientDevice();

        EZText title = EZ.addText(400,100, "Ambient Device", Color.black, 50);

        int mouseX;
        int mouseY;
        boolean leftMousePressed;

        boolean exit = false;

        while (!exit) {

            EZ.refreshScreen();
            mouseX = EZInteraction.getXMouse();
            mouseY = EZInteraction.getYMouse();
            leftMousePressed = EZInteraction.wasMouseLeftButtonPressed();


            if (EZInteraction.wasKeyPressed('x'))
                exit = true;

            if (leftMousePressed)
            {
               if (device.getColorSlider().isPointInElement(mouseX, mouseY)){

                   boolean leftMouseDown = EZInteraction.isMouseLeftButtonDown();
                   while (leftMouseDown && device.getColorSlider().isPointInElement(mouseX, mouseY)) {

                       EZ.refreshScreen();

                       mouseX = EZInteraction.getXMouse();
                       mouseY = EZInteraction.getYMouse();

                       if (mouseX < 100) mouseX = 100;
                       if (mouseX > EZ.getWindowWidth() - 100)  mouseX = EZ.getWindowWidth() - 100;

                       device.getColorSlider().translateTo(mouseX, device.getColorSlider().getYCenter());

                       float deviceColor = (device.getColorSlider().getXCenter() - 100.0f) / (EZ.getWindowWidth() - 100.0f - 100.0f);
                       device.setColor(deviceColor);

                       leftMouseDown = EZInteraction.isMouseLeftButtonDown();
                   }

               }

                if (device.getBrightnessSlider().isPointInElement(mouseX, mouseY)){

                    boolean leftMouseDown = EZInteraction.isMouseLeftButtonDown();
                    while (leftMouseDown && device.getBrightnessSlider().isPointInElement(mouseX, mouseY)) {

                        EZ.refreshScreen();

                        mouseX = EZInteraction.getXMouse();
                        mouseY = EZInteraction.getYMouse();

                        if (mouseX < 100) mouseX = 100;
                        if (mouseX > EZ.getWindowWidth() - 100)  mouseX = EZ.getWindowWidth() - 100;

                        device.getBrightnessSlider().translateTo(mouseX, device.getBrightnessSlider().getYCenter());

                        float deviceBrightness = (device.getBrightnessSlider().getXCenter() - 100.0f) / (EZ.getWindowWidth() - 100.0f - 100.0f);
                        device.setBrightness(deviceBrightness);

                        leftMouseDown = EZInteraction.isMouseLeftButtonDown();
                    }

                }

            }

        }

        System.exit(0);
    }

}

