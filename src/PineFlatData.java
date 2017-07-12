import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by ryan on 7/11/17.
 */
public class PineFlatData {

    private int currentOutflowValue = 8609;
    private int currentInflowValue = 7695;
    private int userMax;
    private int userMin;

    public PineFlatData(int max, int min) {
        this.userMax = max;
        this.userMin = min;
        try {
            getData();
        } catch(IOException e) {}
    }

    public void getData() throws IOException {

        URL pineFlatData = new URL("http://www.spk-wc.usace.army.mil/fcgi-bin/hourly.py?report=pnf&textonly=true");
        BufferedReader br = new BufferedReader( new InputStreamReader( pineFlatData.openStream()));

        String currentLine;

        String finalLine = "";
        int blankLineCount = 0;

        while ((currentLine = br.readLine()) != null)
        {
            if (currentLine.length() == 1) blankLineCount++;
            if (blankLineCount == 2) finalLine = currentLine;
        }

        //Remove all the whitepace with regex and then split all elements with just one space
        String[] output = finalLine.replaceAll("\\s+"," ").split(" ");

        try {
            currentOutflowValue = Integer.parseInt(output[6]);
            currentInflowValue = Integer.parseInt(output[7]);
        }catch(NumberFormatException e) {}

        br.close();
    }

    public int getOutflow() {
        return currentOutflowValue;
    }

    public int getInflow() {
        return currentInflowValue;
    }

    public void setMinMax(int min, int max) {
        this.userMin = min;
        this.userMax = max;
    }

    public float getBrightnessValue() {
        int value = (userMax - (currentOutflowValue - currentInflowValue)) / (userMax - userMin);
        return value;
    }

    public float getColorValue() {
        int value = (userMax - (currentOutflowValue - currentInflowValue)) / (userMax - userMin);
        return value;
    }

}
