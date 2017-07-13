import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class to obtain data from the US Army Corps of Engineers for the Pine Flat for Kings River
 * http://www.spk-wc.usace.army.mil/fcgi-bin/hourly.py?report=pnf
 * Created by Ryan on 7/11/17.
 */
public class PineFlatData {

    private int currentOutflowValue;
    private int currentInflowValue;
    private int dataMax;
    private int dataMin;
    private boolean dataIsGood;
    private List<PineFlowDataPoint> data = new ArrayList<>();

    /**
     * InnerClass for data points
     */
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

    /**
     * Constructor for the PineFlat data
     */
    public PineFlatData() {
        currentInflowValue = 0;
        currentInflowValue = 0;
        dataIsGood = false;
        try {
            getData();
        } catch (IOException e) {
        }
    }

    /**
     * Method to obtain the Data from the source
     *
     * @throws IOException
     */
    public void getData() throws IOException {

        //Create a new ArrayList, incase the data is bad.
        List<PineFlowDataPoint> newData = new ArrayList<>();
        URL pineFlatData = new URL("http://www.spk-wc.usace.army.mil/fcgi-bin/hourly.py?report=pnf&textonly=true");
        BufferedReader br = new BufferedReader(new InputStreamReader(pineFlatData.openStream()));
        DateFormat df = new SimpleDateFormat("ddMMMyyyy");

        //Read the text file line by line
        String currentLine;
        int lineCount = 0;
        while ((currentLine = br.readLine()) != null) {
            //This isn't the most ideal situation but, lines 8-55 are the actual data
            if (lineCount > 7 && lineCount < 56) {
                //Remove all white space from the data and put into an array
                String[] formattedLine = currentLine.replaceAll("\\s+", " ").split(" ");
                Date d;
                int in, out;
                //Try to create a new data point, will throw exception if data has --NR--
                try {
                    d = df.parse(formattedLine[1]);
                    in = Integer.parseInt(formattedLine[6]);
                    out = Integer.parseInt(formattedLine[7]);
                    PineFlowDataPoint dataPoint = new PineFlowDataPoint(d, in, out);
                    newData.add(dataPoint);
                } catch (ParseException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Incorrect data format: " + e.toString());
                }
            }
            lineCount++;
        }

        //Close buffer reader
        br.close();

        //Check to see if the data was good and set the outflow and inflow
        if (newData.size() > 0) {
            dataIsGood = true;
            this.data.clear();
            this.data = newData;
            currentOutflowValue = data.get(data.size() - 1).outflowValue;
            currentInflowValue = data.get(data.size() - 1).inflowValue;
        } else {
            dataIsGood = false;
        }
    }

    /**
     * Set the min and max data values
     *
     * @param min the minimum value the data should be
     * @param max the maximum vaue the data should be
     */
    public void setMinMax(int min, int max) {
        dataMin = min;
        dataMax = max;
        if (dataMax < dataMin) dataMax = dataMin + 1;
    }

    /**
     * A helper method to set an ideal min and max data values based on the current readings
     */
    public void setIdealMinMax() {
        int difference = Math.abs(currentOutflowValue - currentInflowValue);
        int average = (currentOutflowValue + currentInflowValue) / 2;
        int newUserMin = average - difference;
        int newUserMax = average + difference;
        setMinMax(newUserMin, newUserMax);
    }

    /**
     * Returns a value between 0-100 of the current brightness value
     *
     * @return An int between 0-100 of the current brightness value
     */
    public int getBrightnessValue() {
        int difference = Math.abs(currentOutflowValue - currentInflowValue);
        float normVal = (float) (difference) / (float) (dataMax - dataMin);
        return (int) (normVal * 100);
    }

    /**
     * Returns a value between 0-100 of the current color value
     *
     * @return An int between 0-100 of the current color value
     */
    public int getColorValue() {
        int difference = currentOutflowValue - currentInflowValue;
        //Outflow is greater
        if (difference > 0) {
            difference = Math.abs(difference);
            int maxRange = dataMax - dataMin + 1;
            float normVal = (float) difference / (float) maxRange;
            int value = (int) (normVal * 10);
            return value + 60;
        } else {
            difference = Math.abs(difference);
            int maxRange = dataMax - dataMin + 1;
            float normVal = (float) difference / (float) maxRange;
            int value = (int) (normVal * 10);
            return value + 90;
        }
    }

    public boolean isDataGood() {
        return dataIsGood;
    }

    public int getMinValue() {
        return dataMin;
    }

    public int getMaxValue() {
        return dataMax;
    }


}
