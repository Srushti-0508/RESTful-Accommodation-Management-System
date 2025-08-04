/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GlobalDormRoom;

import weather.DayWeather;
import weather.Weather;

/**
 *
 * @author srushti
 */
public class Rooms {
private int roomId;
private String name;
private String location;
private float latitude;
private float longitude;
private double price_per_month;
private String spoken_lang;
private String availability_date;
private int shared_with;
private DayWeather weather;


    public int getRoomID(){return roomId;}
    public int setRoomID(int value){this.roomId = value; return 0;}
    
    public String getName(){return name;}
    public void setName(String value){this.name = value; }
    
    public String getLocation(){return location;}
    public void setLocation(String value){this.location = value; }
    
    public float getLatitude(){return latitude;}
    public void setLatitude(float value){this.latitude = value; }
    
    public float getLongitude(){return longitude;}
    public void setLongitude(float value){this.longitude = value; }
    
    public double getPrice_per_month(){return price_per_month;}
    public void setPrice_per_month(double value){this.price_per_month = value; }
    
    public String getSpoken_lang(){return spoken_lang;}
    public void setSpoken_lang(String value){this.spoken_lang = value; }
    
    public String getAvailability_date(){return availability_date;}
    public void setAvailability_date(String value){this.availability_date = value; }
    
    public int getShared_with(){return shared_with;}
    public void setShared_with(int value){this.shared_with = value; } 

    public DayWeather getWeather(){return weather;}
    public void setWeather(DayWeather weather){this.weather = weather;}
}
