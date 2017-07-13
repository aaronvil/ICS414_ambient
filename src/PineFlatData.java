import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ryan on 7/11/17.
 */
public class PineFlatData {

    private int currentOutflowValue;
    private int currentInflowValue;
    private int userMax;
    private int userMin;
    private boolean dataIsGood;
    private List<PineFlowDataPoint> data = new ArrayList<>();

    private class PineFlowDataPoint {
        public Date date;
        public int outflowValue;
        public int inflowValue;

        public PineFlowDataPoint(Date d, int o, int i) {
            date = d;
            outflowValue = o;
            inflowValue = i;
        }
    }

    public PineFlatData(int max, int min) {
        this.userMax = max;
        this.userMin = min;
        currentInflowValue = 0;
        currentInflowValue = 0;
        dataIsGood = false;
        try {
            getData();
            setIdealMinMax();
        } catch(IOException e) {}
    }

    public void getData() throws IOException {

        List<PineFlowDataPoint> newData = new ArrayList<>();
        URL pineFlatData = new URL("http://www.spk-wc.usace.army.mil/fcgi-bin/hourly.py?report=pnf&textonly=true");
        BufferedReader br = new BufferedReader( new InputStreamReader( pineFlatData.openStream()));
        DateFormat df = new SimpleDateFormat("ddMMMyyyy");

        String currentLine;
        int lineCount = 0;

        while ((currentLine = br.readLine()) != null)
        {
            if (lineCount > 7 && lineCount < 56)
            {
                String[] formattedLine = currentLine.replaceAll("\\s+"," ").split(" ");
                Date d;
                int in, out;
                try{
                    d = df.parse(formattedLine[1]);
                    in =  Integer.parseInt(formattedLine[6]);
                    out = Integer.parseInt(formattedLine[7]);
                    PineFlowDataPoint dataPoint = new PineFlowDataPoint(d, in, out);
                    newData.add(dataPoint);
                } catch(ParseException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Incorrect data format: " + e.toString());
                }
            }
            lineCount++;
        }

        if (newData.size() > 0) {
            dataIsGood = true;
            this.data.clear();
            this.data = newData;
            currentOutflowValue = data.get(data.size() - 1).outflowValue;
            currentInflowValue = data.get(data.size() - 1).inflowValue;
        } else {
            dataIsGood = false;
        }

        br.close();
    }

    public int getOutflow() {
        return currentOutflowValue;
    }

    public int getInflow() {
        return currentInflowValue;
    }

    public boolean isDataGood() {
        return dataIsGood;
    }

    public int getUserMin() { return userMin; }
    public int getUserMax() { return userMax; }

    public void setMinMax(int min, int max) {
        userMin = min;
        userMax = max;
        if (userMax < userMin) userMax = userMin + 1;
    }

    public void setIdealMinMax() {
        int difference = Math.abs(currentOutflowValue - currentInflowValue);
        int average = (currentOutflowValue + currentInflowValue) / 2;
        int newUserMin = average - difference;
        int newUserMax = average + difference;
        setMinMax(newUserMin, newUserMax);
    }

    //Returns a value between 0-100 of the current brightness value
    public int getBrightnessValue() {
        int difference = Math.abs(currentOutflowValue - currentInflowValue);
        float normVal = (float)(difference) / (float)(userMax - userMin);
        return (int)(normVal * 100);
    }

    //Returns a value between 0-100 of the current color value
    public int getColorValue() {
        int difference = currentOutflowValue - currentInflowValue;
        //Outflow is greater
        if (difference > 0) {
            difference = Math.abs(difference);
            int maxRange = userMax - userMin + 1;
            float normVal = (float)difference / (float)maxRange;
            int value = (int)(normVal * 10);
            return value + 60;
        } else {
            difference = Math.abs(difference);
            int maxRange = userMax - userMin + 1;
            float normVal = (float)difference / (float)maxRange;
            int value = (int)(normVal * 10);
            return value + 90;
        }
    }



}
