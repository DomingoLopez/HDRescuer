
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
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
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
    private Integer phone;
    @SerializedName("phone2")
    @Expose
    private Integer phone2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("cp")
    @Expose
    private Integer cp;

    //Atributos de la última sesión
    @SerializedName("session_id")
    @Expose
    private String session_id;
    @SerializedName("timestamp_ini")
    @Expose
    private Date timestamp_ini;
    @SerializedName("timestamp_fin")
    @Expose
    private Date timestamp_fin;
    @SerializedName("total_time")
    @Expose
    private Integer total_time;
    @SerializedName("e4band")
    @Expose
    private boolean e4band;
    @SerializedName("ticwatch")
    @Expose
    private boolean ticwatch;
    @SerializedName("ehealthboard")
    @Expose
    private boolean ehealthboard;


    private final static long serialVersionUID = 9212788480303221900L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserDetails() {
    }

    public UserDetails(String id, String username, String lastname, String email, String password, String gender, Integer age, String height, Integer weight, Integer phone, Integer phone2, String city, String address, Integer cp) {
        super();
        this.id = id;
        this.createdAt = null;
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
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
        this.session_id = null;
        this.timestamp_ini = null;
        this.timestamp_fin = null;
        this.total_time = null;
        this.e4band = false;
        this.ticwatch = false;
        this.ehealthboard = false;
    }


    public UserDetails(String id, String username, String lastname, String email, String password, String gender, Integer age, String height, Integer weight, Integer phone, Integer phone2, String city, String address, Integer cp, Date createdAt,
                       String session_id, Date timestamp_ini, Date timestamp_fin, Integer total_time, boolean e4band, boolean ticwatch, boolean ehealthboard ) {
        super();
        this.id = id;
        this.createdAt = createdAt;
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
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
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
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
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getPhone2() {
        return phone2;
    }

    public void setPhone2(Integer phone2) {
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

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public Date getTimestamp_fin() {
        return timestamp_fin;
    }

    public void setTimestamp_fin(Date timestamp_fin) {
        this.timestamp_fin = timestamp_fin;
    }

    public Integer getTotal_time() {
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
}