
package com.hdrescuer.hdrescuer.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDetails implements Serializable
{
    @SerializedName("id")
    @Expose
    private String id;
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
    private final static long serialVersionUID = 9212788480303221900L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserDetails() {
    }

    /**
     *
     * @param address
     * @param id
     * @param gender
     * @param city
     * @param phone2
     * @param weight
     * @param cp
     * @param lastname
     * @param password
     * @param phone
     * @param email
     * @param age
     * @param username
     * @param height
     */
    public UserDetails(String id, String username, String lastname, String email, String password, String gender, Integer age, String height, Integer weight, Integer phone, Integer phone2, String city, String address, Integer cp) {
        super();
        this.id = id;
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

}