/**
 * Interface to implement different datasources
 * By Ryan Theriot
 */
public interface DataSource {

    /**
     * Obtain the data from your datasource and store it how you see fit
     */
    public void getData();

    /**
     * Set the minimum and maximum  values of the Datasource.
     * Used in conjunction with the sliders
     * @param min Minmimum value the user sets
     * @param max Maximum value the user sets
     */
    public void setMinMax(int min, int max);

    /**
     * Set the ideal min and max based on your data.
     */
    public void setIdealMinMax();

    /**
     * Get the brightness for the ambient device.
     * Should be a value between 0-100
     * @return A value between 0-100 representing a brightness
     */
    public int getBrightnessValue();

    /**
     * Get the color for the ambient device.
     * A value between 0-100.
     * Where 0 and 100 are RED and anything inbetween is a value in the rainbow.
     * @return A value between 0-100 representing a color
     */
    public int getColorValue();

    /**
     * A check to see if your datasource is good.
     * Should implement sometype of check to see if data is good.
     * @return A boolean representing if the data is good
     */
    public boolean isDataGood();

    /**
     * Get the minimum value the user set with the sliders
     * @return The minimum value the user set
     */
    public int getMinValue();

    /**
     * Get the maximum value the user set with the sliders.
     * @return The maximum value  the user set
     */
    public int getMaxValue();

    /**
     * The Maximum value the data can be.
     * Used to set the maximum value of the sliders
     * @return The maximum value of the data
     */
    public int getMax();

    /**
     * The minimum value of the data
     * Used to se the minimum value of the sliders
     * @return The minimum value of the data
     */
    public int getMin();
}
