
package com.hdrescuer.hdrescuer.retrofit.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Clase RequesServerUp. Que comprueba si el servidor está UP
 * @author Domingo López
 */
public class RequestServerUp {

    @SerializedName("cadena")
    @Expose
    private String cadena;


    /**
     * Constructor vacío
     * @author Domingo Lopez
     *
     */
    public RequestServerUp() {
    }

    /**
     * Constructor con paráemtros
     * @author Domingo Lopez
     * @param cadena
     */
    public RequestServerUp(String cadena) {
        super();
        this.cadena = cadena;
    }


    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }
}
