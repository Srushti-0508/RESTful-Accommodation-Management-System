/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GlobalDormRoom;

/**
 *
 * @author srushti
 */
public class Users {
    private int userId;
    private String name;
    private String email;
    
    public int getUserID(){return userId;}
    public void setUserID(int value){this.userId = value;}
    
    public String getName(){
        return name;
    }
    public void setName(String value){
        this.name =value;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String value){
        this.email = value;
    }
}
