
package com.hdrescuer.hdrescuer.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("lastname")
    @Expose
    private String lastname;
   /* @SerializedName("last_monitoring")
    @Expose
    private String lastMonitoring;*/
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    /**
     * No args constructor for use in serialization
     * 
     */
    public User() {
    }

    /**
     * 
     * @param lastMonitoring
     * @param id
     * @param username
     */
    public User(String id, String username,String lastname, String lastMonitoring) {
        super();
        this.id = id;
        this.username = username;
        //this.lastMonitoring = lastMonitoring;
        this.lastname = lastname;
    }

    public User(User user) {
        super();
        this.id = user.getId();
        this.username = user.getUsername();
        //this.lastMonitoring = user.getLastMonitoring();
        this.lastname = user.getLastname();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.username = lastname;
    }

    /*public String getLastMonitoring() {
        return lastMonitoring;
    }

    public void setLastMonitoring(String lastMonitoring) {
        this.lastMonitoring = lastMonitoring;
    }*/

}
