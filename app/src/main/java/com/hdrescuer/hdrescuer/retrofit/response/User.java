
package com.hdrescuer.hdrescuer.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("last_monitoring")
    @Expose
    private String lastMonitoring;

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
    public User(Integer id, String username, String lastMonitoring) {
        super();
        this.id = id;
        this.username = username;
        this.lastMonitoring = lastMonitoring;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastMonitoring() {
        return lastMonitoring;
    }

    public void setLastMonitoring(String lastMonitoring) {
        this.lastMonitoring = lastMonitoring;
    }

}
