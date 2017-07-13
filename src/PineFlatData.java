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
        } catch(IOException e) {}
    }

    public void getData() throws IOException {

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
                    data.add(dataPoint);
                } catch(ParseException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Incorrect data format" + e.toString());
                }
            }
            lineCount++;
        }

        if (data.size() > 0) {
            dataIsGood = true;
            return;
        }

        currentOutflowValue = data.get(data.size() - 1).outflowValue;
        currentInflowValue = data.get(data.size() - 1).inflowValue;



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
