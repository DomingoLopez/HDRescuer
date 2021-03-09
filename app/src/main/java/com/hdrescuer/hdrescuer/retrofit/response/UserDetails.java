
package com.hdrescuer.hdrescuer.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetails {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private Integer phone;
    @SerializedName("last_monitoring")
    @Expose
    private String lastMonitoring;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserDetails() {
    }

    /**
     * 
     * @param gender
     * @param phone
     * @param lastMonitoring
     * @param weight
     * @param id
     * @param age
     * @param email
     * @param username
     * @param height
     */
    public UserDetails(Integer id, String username, Integer age, Double height, Integer weight, String gender, String email, Integer phone, String lastMonitoring) {
        super();
        this.id = id;
        this.username = username;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getLastMonitoring() {
        return lastMonitoring;
    }

    public void setLastMonitoring(String lastMonitoring) {
        this.lastMonitoring = lastMonitoring;
    }

}
