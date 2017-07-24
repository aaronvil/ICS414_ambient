/**
 * Created by Ryan on 7/24/2017.
 */
public interface DataSource {

    public void getData();

    public void setMinMax(int min, int max);

    public void setIdealMinMax();

    public int getBrightnessValue();

    public int getColorValue();

    public boolean isDataGood();

    public int getMinValue();

    public int getMaxValue();

    public int getMax();

    public int getMin();
}
