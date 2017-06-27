package main;

import java.awt.Color;

/**
 * Created by Ryan on 6/26/2017.
 */
public class Ambient414 {

    public static void main(String []args) {

        EZ.initialize(800,800);
        EZ.addCircle(400, 400,200, 200, new Color(0.0f, 1.0f, 0.0f, 0.75f), true);
        EZ.addCircle(300, 400,100, 100, new Color(1.0f, 0.0f, 0.0f, 0.5f), true);
        EZ.addCircle(500, 400,100, 100, new Color(0.0f, 0.0f, 1.0f, 0.5f), true);
        EZ.addCircle(400, 300,100, 100, new Color(1.0f, 0.0f, 1.0f, 0.5f), true);
        EZ.addCircle(400, 500,100, 100, new Color(0.0f, 1.0f, 1.0f, 0.5f), true);

    }

}
