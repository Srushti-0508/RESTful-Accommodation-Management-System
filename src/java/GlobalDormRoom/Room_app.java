/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GlobalDormRoom;

/**
 *
 * @author srushti
 */
public class Room_app {
    private int applicationId;
    private int roomId;
    private int userId;
    private String status;
    
    public int getApplicationID(){return applicationId;}
    public int setApplicationID(int value){this.applicationId = value; return 0;} 
    
    public int getRoomID(){return roomId;}
    public int setRoomID(int value){this.roomId = value; return 0;}
    
    public int getUserID(){return userId;}
    public int setUserID(int value){this.userId = value; return 0;}
    
    public String getStatus(){return status;}
    public void setStatus(String value){this.status =value;}
}
