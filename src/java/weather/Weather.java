/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weather;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author srushti
 */
public class Weather {
// Welcome7.java
    private String product;
    private String init;
    private Datasery[] dataseries;

    public String getProduct() { return product; }
    public void setProduct(String value) { this.product = value; }

    public String getInit() { return init; }
    public void setInit(String value) { this.init = value; }

    public Datasery[] getDataseries() { return dataseries; }
    public void setDataseries(Datasery[] value) { this.dataseries = value; }


// Datasery.java

public class Datasery {
    
    private long date;
   
    private String weather;
   @SerializedName("temp2m")
    private Temp2M temp2M;
    private long wind10MMax;

    public long getDate() { return date; }
    public void setDate(long value) { this.date = value; }

    public String getWeather() { return weather; }
    public void setWeather(String value) { this.weather = value; }

    public Temp2M getTemp2M() { return temp2M; }
    public void setTemp2M(Temp2M value) { this.temp2M = value; }

    public long getWind10MMax() { return wind10MMax; }
    public void setWind10MMax(long value) { this.wind10MMax = value; }
}

// Temp2M.java
public class Temp2M {
    private long max;
    private long min;

    public long getMax() { return max; }
    public void setMax(long value) { this.max = value; }

    public long getMin() { return min; }
    public void setMin(long value) { this.min = value; }
}

}
