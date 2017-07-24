import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class to obtain data from the US Army Corps of Engineers for the Pine Flat for Kings River
 * http://www.spk-wc.usace.army.mil/fcgi-bin/hourly.py?report=pnf
 * Created by Ryan Theriot and Aaron Jhumar Villanueva.
 */
public class PineFlatWaterFlow implements DataSource {

    private int currentOutflowValue;
    private int currentInflowValue;
    private int dataMax;
    private int dataMin;
    private int maxValue = 25000;
    private int minValue = 0;
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
     *
     * @param updateInterval Update interval between data retrievals in minutes.
     */
    public PineFlatWaterFlow(int updateInterval) {
        currentInflowValue = 0;
        currentOutflowValue = 0;
        dataIsGood = false;
        getData();
        //Setup a timer to update data every 30 minutes.
        //First data retrieval should be performed in data's constructor (ie: PineFlatData)
        //Used the following tutorial (http://singletonjava.blogspot.com/2016/02/java-timer-example.html)
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getData();
            }
        };

        long interval = 1000 * 60 * updateInterval;
        timer.schedule(task, interval, interval);
    }

    /**
     * Method to obtain the Data from the source
     *
     * @throws IOException
     */
    public void getData() {

        //Create a new ArrayList, incase the data is bad.
        List<PineFlowDataPoint> newData = new ArrayList<>();
        URL pineFlatData;
        BufferedReader br;
        DateFormat df = new SimpleDateFormat("ddMMMyyyy");
        try {
            pineFlatData = new URL("http://www.spk-wc.usace.army.mil/fcgi-bin/hourly.py?report=pnf&textonly=true");
            br = new BufferedReader(new InputStreamReader(pineFlatData.openStream()));
        } catch (IOException e) {
            System.out.println(e.toString());
            return;
        }

        //Read the text file line by line
        String currentLine;
        int lineCount = 0;
        try {
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
        } catch (IOException e) {
            System.out.println(e.toString());
            return;
        }

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
        return 100;
    }

    /**
     * Returns a value between 0-100 of the current color value
     *
     * @return An int between 0-100 of the current color value
     */
    public int getColorValue() {
        int difference = currentOutflowValue - currentInflowValue;
        int maxRange = dataMax - dataMin + 1;
        float normVal = (float) difference / (float) maxRange;
        normVal = Math.max(-1.0f, Math.min(1.0f, normVal)); //Dont let normval go above 1.0 or below -1.0
        int value = (int) (normVal * 20);
        return 20 + value; //Middle color is yellow
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

    public int getMax() {
        return maxValue;
    }

    public int getMin() {
        return minValue;
    }

}
