
package com.hdrescuer.hdrescuer.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Clase User con sus Getters y setters
 * @author Domingo Lopez
 */
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
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("session_id")
    @Expose
    private String session_id;
    @SerializedName("timestamp_ini")
    @Expose
    private Date timestamp_ini;
    @SerializedName("total_time")
    @Expose
    private Integer total_time;

    /**
     * Constructor sin parámetros
     * @author Domingo Lopez
     */
    public User() {
    }

    /**
     * Cosntructor con parámetros
     * @author Domingo Lopez
     * @param id
     * @param username
     * @param lastname
     * @param session_id
     * @param timestamp_ini
     * @param total_time
     */
    public User(String id, String username,String lastname, String session_id, Date timestamp_ini, Integer total_time) {
        super();
        this.id = id;
        this.username = username;
        this.lastname = lastname;
        this.session_id = session_id;
        this.timestamp_ini = timestamp_ini;
        this.total_time = total_time;
    }

    public User(User user) {
        super();
        this.id = user.id;
        this.username = user.username;
        this.lastname = user.lastname;
        this.session_id = user.session_id;
        this.timestamp_ini = user.timestamp_ini;
        this.total_time = user.total_time;
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


    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public Date getTimestamp_ini() {
        return timestamp_ini;
    }

    public void setTimestamp_ini(Date timestamp_ini) {
        this.timestamp_ini = timestamp_ini;
    }

    public Integer getTotal_time() {
        return total_time;
    }

    public void setTotal_time(Integer total_time) {
        this.total_time = total_time;
    }
}
