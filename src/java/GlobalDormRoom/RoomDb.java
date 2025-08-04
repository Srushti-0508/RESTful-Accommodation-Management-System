/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GlobalDormRoom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import weather.DayWeather;
import weather.Weather;
import weather.WeatherService;
/**
 *
 * @author srushti
 */
@Path("Room")
public class RoomDb {
    @Context
    private UriInfo context;
      private final WeatherService weatherService = new WeatherService();
    public RoomDb(){
        
    }
    Gson gson; 
    
    public Connection connect(){ // Start the Database Connection
        Connection conn = null;
        try{
            String url= "jdbc:sqlite:C:\\Users\\srush\\OneDrive - Nottingham Trent University\\Service Centric\\RESTService2\\test.db"; //using absolute path.
            try{
                Class.forName("org.sqlite.JDBC");    // Adapted from https://www.codejava.net/java-se/jdbc/connect-to-sqlite-via-jdbc
            }catch(Exception ex){
                ex.printStackTrace();
            }
            conn=DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established");
            try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");  // to enable foreign key  //code taken from https://www.mathworks.com/matlabcentral/answers/270561-how-to-enable-foreign-key-for-sqlite-database
            //System.out.println("Foreign keys enabled.");
        }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public List<Rooms> SelectAll(){ // fetch all room data from the database and store them in the list

        List<Rooms> roomList = new ArrayList<>();
     Connection conn = connect(); 
     if(conn!=null){
        String sql= "SELECT * FROM Rooms";
            try(Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
 
                while(rs.next()){                                               // Adapted from https://www.codejava.net/java-se/jdbc/connect-to-sqlite-via-jdbc                                                               
                Rooms rooms= new Rooms();
                rooms.setRoomID(rs.getInt("RoomID")); 
                rooms.setName(rs.getString("Name"));
                rooms.setLocation(rs.getString("Location"));
                rooms.setLongitude(rs.getFloat("Longitude"));
                rooms.setLatitude(rs.getFloat("Latitude"));
                rooms.setPrice_per_month(rs.getDouble("Price_per_month"));
                rooms.setSpoken_lang(rs.getString("Spoken_lang"));
                rooms.setAvailability_date(rs.getString("Availability_date"));
                rooms.setShared_with(rs.getInt("Shared_with"));
                roomList.add(rooms);   // add the room values to the list. 
            }
            rs.close();
            stmt.close();
            conn.close();
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
    }else{
       System.err.println("connection to database failed"); 
        }
     roomList.forEach(roomlist ->{
        float longitude = roomlist.getLongitude(); 
        float latitude = roomlist.getLatitude();

    //System.out.println("Retrieving weather for Latitude: " + latitude + ", Longitude: " + longitude);
//            String weatherJson = weatherService.getJson(
//                       (float)roomlist.getLatitude(),
//                       (float)roomlist.getLongitude()

            String weatherJson = weatherService.getJson(longitude, latitude);  //using each room location, the weather data will be fetch from 7 timer.
            
            gson = new GsonBuilder().setPrettyPrinting().create();
            DayWeather dayweather = gson.fromJson(weatherJson, DayWeather.class);  //deserialise the weather data to be able to megre it in room data. 
                roomlist.setWeather(dayweather); // Add weather details to the room details. 
            });
        return roomList;
    }
    /**
     * Retrieves representation of an instance of math.MathOperations
     * @return an instance of java.lang.String
     */
  
   @GET
   @Produces(MediaType.APPLICATION_JSON)  
   public Response getRooms(){  // Fetch all the room data and serealise it. 
        List<Rooms> roomList = SelectAll(); 
        
        //Serealise the data
        gson = new GsonBuilder().setPrettyPrinting().create();
        String RoomJson = gson.toJson(roomList);
         //System.out.println(RoomJson);
            return Response.status(Response.Status.CREATED)
                   .entity(RoomJson)
                   .build();     
     //      return RoomJson;
    }

    @GET
    @Path("{roomId}")
    @Produces(MediaType.APPLICATION_JSON) // get room data based on ID  
    public String getRoomsByID(@PathParam("roomId") int roomId) {  
        List<Rooms> roomList = SelectAll();
        Rooms roomById = null;
        //String roomName;
        for(Rooms room:roomList){
            if(room.getRoomID()==roomId){
                roomById = room;
                break;
            }
        }
        gson = new GsonBuilder().setPrettyPrinting().create();
        String Jsonrep = gson.toJson(roomById);
        return Jsonrep; 
    }

    private boolean checkRoomAvail(int roomId) throws SQLException{  //helps to check if room is available or not. 
            Connection conn= connect();
         try{   
            String check_room_avail = "SELECT * FROM Application WHERE roomId = ?  AND status = 'applied'"; 
            PreparedStatement check_stmt3 = conn.prepareStatement(check_room_avail);
            check_stmt3.setInt(1, roomId);
            ResultSet roomFound = check_stmt3.executeQuery();
            
            return !roomFound.next();  
            
         }finally{
             if(conn!=null){
                       conn.close();
                   }
         }   
}
    
  @GET                                                  //endpoint to search available rooms.
   @Path("searchAvailRooms")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getAvailRooms() throws SQLException{
      try{ 
       List<Rooms> roomList = SelectAll();
       List<Rooms> roomAvail = new ArrayList<>();
       for(Rooms room:roomList){
           boolean RoomAvailable = checkRoomAvail(room.getRoomID());  //using checkRoomAvail method 
           if(RoomAvailable){
               roomAvail.add(room);
           }
       }
       if(roomAvail.isEmpty()){   //if the list is empty meaning no available rooms. 
           Response.status(Response.Status.BAD_REQUEST)
                   .entity("{\"message\":\"No Available Rooms\"}")
                   .build();
       }
       return Response.status(Response.Status.OK)
                   .entity(new Gson().toJson(roomAvail)) // serealize it
                   .build();
      }catch(SQLException e){
       return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\":\"Error Seraching Rooms\"}")
                .build();
      }
   }
    /**
     * PUT method for updating or creating an instance of MathOperations
     * @param content representation for the resource
     * @throws java.sql.SQLException
     */
    
    @POST
    @Path("applyRoom")                                              //To make  ROOM APPLICATIONS 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response applyRooms(String content) throws SQLException {
        
    // Deseralise Json request body sent by client. 
    gson = new GsonBuilder().setPrettyPrinting().create();
    Room_app apply = gson.fromJson(content, Room_app.class);
    int roomId = apply.getRoomID();
    int userId = apply.getUserID();
    Connection conn = connect(); 
         
    conn.setAutoCommit(false);
        
        try{
            
           // if user re-apply for the same room when the application status is still applied. 
            String check_application = "SELECT * FROM Application WHERE roomId = ? AND userId = ?  AND status = 'applied'"; 

             //Adapated from: https://www.geeksforgeeks.org/how-to-use-preparedstatement-in-java/
             
            PreparedStatement check = conn.prepareStatement(check_application);
            check.setInt(1, roomId);
            check.setInt(2, userId);
            ResultSet rs_application = check.executeQuery();
            
           if(rs_application.next()){
               return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Application already exists.\"}")
               .build();
           }
           
            //check if particular user already have an application
            String check_user = "SELECT * FROM Application WHERE userId = ?  AND status = 'applied'"; 
            PreparedStatement check_stmt = conn.prepareStatement(check_user);
            check_stmt.setInt(1, userId);
            ResultSet rs = check_stmt.executeQuery();
            
           if(rs.next()){
               return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"You can only apply one Application at a time.\"}")
               .build();
           }
           
//           String check_room = "SELECT * FROM Application WHERE roomId = ?  AND status = 'applied'"; 
//            PreparedStatement check_stmt2 = conn.prepareStatement(check_room);
//            check_stmt2.setInt(1, roomId);
//            ResultSet rs2 = check_stmt2.executeQuery();
           
            //check if the room is available to apply
           if(!checkRoomAvail(roomId)){
               return Response.status(Response.Status.BAD_REQUEST)
               .entity("{\"error\":\"Room is not available.\"}")
               .build();
           }
           
           //CREATE new application. 
           String app_sql= "INSERT INTO Application (roomId, userId, status) " + "VALUES (?,?,'applied');"; 
                // String app_sql = "INSERT INTO Application roomID" + roomID + "UserID" + userID; 
           
            PreparedStatement app_stmt = conn.prepareStatement(app_sql);
            app_stmt.setInt(1, roomId);
            app_stmt.setInt(2, userId);
            app_stmt.executeUpdate();   
            conn.commit();
            
           return Response.status(Response.Status.OK)
               .entity("{\"message\":\"Application Successful.\"}")
               .build();
           
        }catch(SQLException e){
                System.out.println(e.getMessage());
               return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Application UnSuccessfull. Either Room or User doesn't exists\"}")
               .build();
         }finally{
             if(conn!=null){
                 conn.close();
             }
         }
        }


    @GET
    @Path("viewHistory/{userId}")                                                 // Display the current and previous Application stauts only.                                                                          
    @Produces(MediaType.APPLICATION_JSON)                                                   //applied or Cancelled status.
    public Response viewHistory(@PathParam("userId") int userId) throws SQLException {
    Connection conn = connect();
   
    try{
                String ID_check = "SELECT userId FROM Application WHERE userId = ?";  //If no applications are made by the users.
                PreparedStatement check = conn.prepareStatement(ID_check);
                check.setInt(1, userId);
                ResultSet rs_Id = check.executeQuery();
            
           if(!rs_Id.next()){
               return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"You have not made any application yet.\"}")
               .build();
           }
           
            String history_sql= "SELECT roomId, status FROM Application WHERE userId = ?";  //display Room Id and Status for that room. 
            PreparedStatement hst_stmt2 = conn.prepareStatement(history_sql);
            hst_stmt2.setInt(1, userId);
            ResultSet rs3 = hst_stmt2.executeQuery();
            List<Room_app> ApplicationHist = new ArrayList<>();
            
            while(rs3.next()){
                Room_app room_app= new Room_app();
                room_app.setRoomID(rs3.getInt("RoomID"));  
                room_app.setStatus(rs3.getString("Status"));
                ApplicationHist.add(room_app);
            }
            
            if(ApplicationHist.isEmpty()){   
                return Response.status(Response.Status.BAD_REQUEST)
               .entity("{\"error\":\"No data found for this ID.\"}")  
               .build();
            }

           // Serialise Json Data
           gson = new GsonBuilder().setPrettyPrinting().create();
            String jrep = gson.toJson(ApplicationHist);
               return Response.status(Response.Status.CREATED)
               .entity(jrep)
               .build();
               
    }catch(SQLException e){
        e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
               .entity("{\"error\":\"Error Database\"}")
               .build();
    } finally{
        if(conn!=null){
            conn.close();
        }
    }    
}
    @POST
    @Path("cancelRoom")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cancelApplication(String cancelcontent) throws SQLException {
            // Deseralise Json body request sent by client.
            gson = new GsonBuilder().setPrettyPrinting().create();
            Room_app cancel = gson.fromJson(cancelcontent, Room_app.class);
            int roomId = cancel.getRoomID();
            int userId = cancel.getUserID();
    
            Connection conn = connect();
            conn.setAutoCommit(false);
    
    if(conn != null){
        try{
            String cancelapp_sql= "UPDATE Application SET status = 'cancelled' WHERE roomId = ? AND userId = ? AND status = 'applied'";  
           
            PreparedStatement cancelapp_stmt = conn.prepareStatement(cancelapp_sql);
            cancelapp_stmt.setInt(1, roomId);
            cancelapp_stmt.setInt(2, userId);
            int update_stmt = cancelapp_stmt.executeUpdate();
            
            if(update_stmt > 0){  
                conn.commit();
                return Response.status(Response.Status.OK)
               .entity("{\"message\":\"Application Cancelled Successfully.\"}")
               .build();
            } else{
                return Response.status(Response.Status.BAD_REQUEST)
               .entity("{\"error\":\"No Application cancelled.\"}")
               .build();
            }
         }catch(SQLException e){
                System.out.println(e.getMessage());
               return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
               .entity("{\"error\":\"Error Cancelling the application.\"}")
               .build();
         } 
    }
             conn.close();  
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
               .entity("{\"error\":\"Failed to connect to database.\"}")
               .build();
    }
    
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
