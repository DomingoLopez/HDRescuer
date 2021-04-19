
package com.hdrescuer.hdrescuer.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase ResponseAuth (Respuesta tras la autorización). Contiene el token de los usuarios
 * @author Domingo Lopez
 */
public class ResponseAuth {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("created")
    @Expose
    private String created;

    /**
     * Constructor sin parámetros
     * @author Domingo Lopez
     * 
     */
    public ResponseAuth() {
    }

    /**
     * Constructor con parámetros
     * @author Domingo Lopez
     * @param created
     * @param email
     * @param token
     * @param username
     */
    public ResponseAuth(String token, String username, String email, String created) {
        super();
        this.token = token;
        this.username = username;
        this.email = email;
        this.created = created;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
