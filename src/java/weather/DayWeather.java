/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weather;

/**
 *
 * @author srushti
 */
public class DayWeather {
    private String weather;
    private long max;
    private long min;
    
    public DayWeather(String weather, long max, long min){
        this.weather = weather;
        this.max = max;
        this.min = min;
    }
    
    public String getWeather() { return weather; }
    public void setWeather(String value) { this.weather = value; }
    
    public long getMax() { return max; }
    public void setMax(long value) { this.max = value; }

    public long getMin() { return min; }
    public void setMin(long value) { this.min = value; }
}
