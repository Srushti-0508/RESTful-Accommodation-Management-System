/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Distance;

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
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author srushti
 */
@Path("Distance")
public class DistanceCalc {
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of weatherService
     */
    public DistanceCalc() {
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("origins")String origins, @QueryParam("destinations")String destinations) {
        String response="";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("origins", origins);
        parameters.put("destinations", destinations);
        parameters.put("mode", "walking");
        parameters.put("key", "AIzaSyDajHZ2hhXzG7iYZ4IofvG3OPn32hRhSsE");

        // Convert parameters to String
       String convertedParamsToString = parameters.entrySet().stream()
       .map(entry -> entry.getKey() + "=" + entry.getValue())
       .collect(Collectors.joining("&"));
try{
        URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?" + convertedParamsToString);
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
//System.out.println(response);
//json deseralise
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  DistanceParam distanceParam = gson.fromJson(response, DistanceParam.class);
  
  
  for(int i = 0;i<distanceParam.getRows().length;i++){
  DistanceParam.Row row = distanceParam.getRows()[i];
  
  if(row==null){
      System.err.println("Row is null");
  }
//            for (DistanceParam.Element element : row.getElements()) {
//                System.out.println("Distance: " + element.getDistance().getText());
//                System.out.println("Duration: "+ element.getDuration().getText());
//            }         
  }
  return gson.toJson(distanceParam);
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
