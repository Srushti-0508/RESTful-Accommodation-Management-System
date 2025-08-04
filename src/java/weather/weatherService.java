/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package weather;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author srushti
 */
@Path("weather")
public class WeatherService{
   // private static String response;

        // TODO code application logic here
 
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of weatherService
     */
    public WeatherService() {
    }

    /**
     * Retrieves representation of an instance of weather.weatherService
     * @return an instance of java.lang.String
     */
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("lon")float longitude, @QueryParam("lat")float latitude ){
        //System.out.println("Received Longitude: " + longitude + ", Latitude: " + latitude);
        //TODO return proper representation object
        String response = "";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lon", String.valueOf(longitude));   //convert float value to string. 
        parameters.put("lat", String.valueOf(latitude));
        parameters.put("lang", "en");
        parameters.put("unit", "metric");
        parameters.put("output", "json");

        // Convert parameters to String
       String convertedParamsToString = parameters.entrySet().stream()
       .map(entry -> entry.getKey() + "=" + entry.getValue())
       .collect(Collectors.joining("&"));
try{
        URL url = new URL("https://www.7timer.info/bin/civillight.php?" + convertedParamsToString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
       
        String line = in.readLine();
        // Read the entire response line by line
        while( line != null )
            {
                response += line + "\r\n";
                line = in.readLine();
            }
        in.close(); 
}catch(Exception e){
    e.printStackTrace();
}  

//json deseralise
  Gson gson = new GsonBuilder().setPrettyPrinting().create();

  Weather weather = gson.fromJson(response, Weather.class);
 
   
   // System.out.println(weather.getProduct() + weather.getInit());
    //System.out.println(" Weather "+ datasery.getDate() + datasery.getWeather());
    
   //for(int i= 0;i< weather.getDataseries().length;i++){
if(weather.getDataseries().length > 0){
        Weather.Datasery datasery = weather.getDataseries()[0];
        
        DayWeather dayweather = new DayWeather(
                datasery.getWeather(), 
                datasery.getTemp2M().getMax(), 
                datasery.getTemp2M().getMin());
        
         //System.out.println("Date: " + datasery.getDate());
         //System.out.println("Weather: " + datasery.getWeather());
         return gson.toJson(dayweather);
         
   }else{
    return null;
}
        
     //return serialise data
       // return gson.toJson(weather.getDataseries());// only extract date and weather, temp,windmax
       
    }

    /**
     * PUT method for updating or creating an instance of weatherService
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
         
    }
       
}


