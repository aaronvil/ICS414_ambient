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

  private  float currentTravelTime;
  private int dataMax;
  private int dataMin;
  private int maxValue = 150;
  private int minValue = 0;
  private boolean dataIsGood;
  private int online = 0;
  private int playing = 0;
  private String userID = "76561198043369431";

  public SteamApi(int updateInterval) {
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
        //Object friendSummeryJSON = parser.parse(getHTML("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=D395BCB6A9C1101D98963107D2AA8AAA&steamids=" + steamid));

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
            if (game.equals("Mini Metro")) {
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

  @Override
  public void setMinMax(int min, int max) {
    dataMin = min;
    dataMax = max;
    if (dataMax < dataMin) dataMax = dataMin + 1;
  }

  @Override
  public void setIdealMinMax() { setMinMax(35, 75); }

  @Override
  public int getBrightnessValue() {
    return 100;
  }

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

  @Override
  public boolean isDataGood() {
    return dataIsGood;
  }

  @Override
  public int getMinValue() {
    return dataMin;
  }

  @Override
  public int getMaxValue() {
    return dataMax;
  }

  @Override
  public int getMax() {
    return maxValue;
  }

  @Override
  public int getMin() {
    return minValue;
  }
}
