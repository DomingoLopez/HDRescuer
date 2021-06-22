
package com.hdrescuer.hdrescuer.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase UserDetails, Serializable con sus Getters y setters
 * @author Domingo Lopez
 */
public class UserDetails implements Serializable
{
    @SerializedName("user_id")
    @Expose
    private int user_id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("phone2")
    @Expose
    private String phone2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("cp")
    @Expose
    private String cp;

    //Atributos de la última sesión
    @SerializedName("session_id")
    @Expose
    private int session_id;
    @SerializedName("timestamp_ini")
    @Expose
    private String timestamp_ini;
    @SerializedName("timestamp_fin")
    @Expose
    private String timestamp_fin;
    @SerializedName("total_time")
    @Expose
    private long total_time;
    @SerializedName("e4band")
    @Expose
    private boolean e4band;
    @SerializedName("ticwatch")
    @Expose
    private boolean ticwatch;
    @SerializedName("ehealthboard")
    @Expose
    private boolean ehealthboard;
    @SerializedName("description")
    @Expose
    private String description;


    private final static long serialVersionUID = 9212788480303221900L;

    /**
     * Constructor sin parámetros
     * @author Domingo Lopez
     *
     */
    public UserDetails() {
    }

    /**
     * Cosntructor con parámetros
     * @author Domingo Lopez
     * @param user_id
     * @param username
     * @param lastname
     * @param email
     * @param gender
     * @param age
     * @param height
     * @param weight
     * @param phone
     * @param phone2
     * @param city
     * @param address
     * @param cp
     */

    public UserDetails(int user_id, String username, String lastname, String email, String gender, Integer age, String height, Integer weight, String phone, String phone2, String city, String address, String cp, String createdAt,
                       int session_id, String timestamp_ini, String timestamp_fin, long total_time, boolean e4band, boolean ticwatch, boolean ehealthboard, String description ) {
        super();
        this.user_id = user_id;
        this.createdAt = createdAt;
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.phone = phone;
        this.phone2 = phone2;
        this.city = city;
        this.address = address;
        this.cp = cp;

        //Atributos de la última sesión
        this.session_id = session_id;
        this.timestamp_ini = timestamp_ini;
        this.timestamp_fin = timestamp_fin;
        this.total_time = total_time;
        this.e4band = e4band;
        this.ticwatch = ticwatch;
        this.ehealthboard = ehealthboard;
        this.description = description;
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
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public String getTimestamp_ini() {
        return timestamp_ini;
    }

    public void setTimestamp_ini(String timestamp_ini) {
        this.timestamp_ini = timestamp_ini;
    }

    public String getTimestamp_fin() {
        return timestamp_fin;
    }

    public void setTimestamp_fin(String timestamp_fin) {
        this.timestamp_fin = timestamp_fin;
    }

    public long getTotal_time() {
        return total_time;
    }

    public void setTotal_time(Integer total_time) {
        this.total_time = total_time;
    }

    public boolean isE4band() {
        return e4band;
    }

    public void setE4band(boolean e4band) {
        this.e4band = e4band;
    }

    public boolean isTicwatch() {
        return ticwatch;
    }

    public void setTicwatch(boolean ticwatch) {
        this.ticwatch = ticwatch;
    }

    public boolean isEhealthboard() {
        return ehealthboard;
    }

    public void setEhealthboard(boolean ehealthboard) {
        this.ehealthboard = ehealthboard;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}