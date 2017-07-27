import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ryan on 7/27/2017.
 */
public class BingTraffic implements DataSource {

    private String bingAPI = "AnczajqYYh4VoTuTW-aqhiIJ8bise3nOOHyNoRCfd2SQvJcnIcJm0GIvJMfUMrwV";
    private String origin = "4911a+mokapea+pl+ewa+beach";
    private String destination = "University%20of%20Hawaii%20at%20Manoa";
    private float currentTravelTime;
    private int dataMax;
    private int dataMin;
    private int maxValue = 150;
    private int minValue = 0;
    private boolean dataIsGood;

    public BingTraffic(int updateInterval) {
        currentTravelTime = 0;
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

    public void getData() {

        try {
            URL url = new URL(getTrafficUrl(origin, destination));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(br);
            JSONArray jsonArray = (JSONArray) jsonObject.get("resourceSets");
            jsonObject = (JSONObject) jsonArray.get(0);
            jsonArray = (JSONArray) jsonObject.get("resources");
            jsonObject = (JSONObject) jsonArray.get(0);

            System.out.println(jsonObject.get("travelDurationTraffic"));
            currentTravelTime = Float.parseFloat(jsonObject.get("travelDurationTraffic").toString());
            currentTravelTime /= 60.0f;
            dataIsGood = true;

        }catch (IOException | ParseException e) {
            System.out.println("Bing Data Unavailable");
            dataIsGood = false;
        }

    }

    private String getTrafficUrl (String org, String dest) {
        String trafficURL = "http://dev.virtualearth.net/REST/V1/Routes/Driving?wp.0=" + org +
                            "&wp.1="+ dest +
                            "&avoid=minimizeTolls&key=" + bingAPI;
        return trafficURL;
    }

    public int getColorValue() {
        if (dataMin > currentTravelTime) return 40;
        if (dataMax < currentTravelTime) return 0;
        float normVal = (currentTravelTime - dataMin) / (float) (dataMax - dataMin + 1);
        normVal = Math.max(0.0f, Math.min(1.0f, normVal)); //Dont let normval go above 1.0 or below 0.0
        int value = (int) (normVal * 40);
        return 40 - value;
    }

    public int getBrightnessValue() {
        return 100;
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
    public void setIdealMinMax() { setMinMax(35, 75); }

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
