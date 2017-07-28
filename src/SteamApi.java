import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * Created by ajvil_000 on 7/27/2017.
 */
public class SteamApi implements DataSource {

  private int dataMax;
  private int dataMin;
  private int maxValue = 150;
  private int minValue = 0;
  private boolean dataIsGood;
  private int online = 0;
  private int playing = 0;
  private String userID = "76561198043369431";
  private String gameMatch = "Mini Metro";

  /**
   * Constructor
   * @param updateInterval - interval that it updates the data.
   */
  public SteamApi(int updateInterval) {
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
   * Gets the data from steam api that goes through friends list and see who is online and if they are playing a certain game. If friends are online, variable online increases. If they
   * are playing the gameMatch, then playing increases.
   */
  @Override
  public void getData() {
    JSONParser parser = new JSONParser();

    try {
      URL friendListURL = new URL("http://api.steampowered.com/ISteamUser/GetFriendList/v0001/?key=D395BCB6A9C1101D98963107D2AA8AAA&steamid="+ userID + "&relationship=friend");
      HttpURLConnection con = (HttpURLConnection) friendListURL.openConnection();
      con.setRequestMethod("GET");
      BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      JSONObject friendListData = (JSONObject) parser.parse(br);


      JSONObject friendsList = (JSONObject) friendListData.get("friendslist");
      JSONArray friends = (JSONArray) friendsList.get("friends");
      //Gets the list of steam id
      for (int i = 0; i < friends.size(); i++) {
        JSONObject id = (JSONObject) friends.get(i);
        String friendID = (String) id.get("steamid");

        URL summeryURL = new URL("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=D395BCB6A9C1101D98963107D2AA8AAA&steamids=" + friendID);
        HttpURLConnection summeryCon = (HttpURLConnection) summeryURL.openConnection();
        summeryCon.setRequestMethod("GET");
        BufferedReader summeryBr = new BufferedReader(new InputStreamReader(summeryCon.getInputStream()));
        JSONObject friendSummeryJSON = (JSONObject) parser.parse(summeryBr);

        JSONObject friendDataJSON = (JSONObject) friendSummeryJSON;
        JSONObject friendData = (JSONObject) friendDataJSON.get("response");
        JSONArray friendInfo = (JSONArray) friendData.get("players");
        JSONObject info = (JSONObject) friendInfo.get(0);
        Long state = (Long) info.get("personastate");
        if (state == 1) {
          online++;
          String game = (String) info.get("gameextrainfo");
          if (game != null) {
            if (game.equals(gameMatch)) {
              playing++;
            }
          }
        }
      }
    } catch (IOException | ParseException e) {
      System.out.println("Steam Data Unavailable");
      dataIsGood = false;
    }
  }

  /**
   * Sets the min and max
   * @param min Minmimum value the user sets
   * @param max Maximum value the user sets
   */
  @Override
  public void setMinMax(int min, int max) {
    dataMin = min;
    dataMax = max;
    if (dataMax < dataMin) dataMax = dataMin + 1;
  }

  /**
   * sets min and max to ideal
   */
  @Override
  public void setIdealMinMax() { setMinMax(35, 75); }

  /**
   * sets the brightness value of the color
   * @return brightness, stuck at 100 since only color changes.
   */
  @Override
  public int getBrightnessValue() {
    return 100;
  }

  /**
   * set color value
   * @return the color value based on status
   */
  @Override
  public int getColorValue() {
    if(online > 0) {
      if (playing > 0){
        //friend is playing mini metro
        return 80;
      }
      //friends are online
      return 40;
    }
    //no friends online.
    return 0;
  }

  /**
   * tells if the data retrieved is good.
   * @return true if data had not error in retrieving else false
   */
  @Override
  public boolean isDataGood() {
    return dataIsGood;
  }

  /**
   * Gets the min value
   * @return the min value of the data
   */
  @Override
  public int getMinValue() {
    return dataMin;
  }

  /**
   * gets the max value
   * @return max value of data
   */
  @Override
  public int getMaxValue() {
    return dataMax;
  }

  /**
   * gets user max
   * @return return user max
   */
  @Override
  public int getMax() {
    return maxValue;
  }

  /**
   * gets user min
   * @return returns user min
   */
  @Override
  public int getMin() {
    return minValue;
  }
}
