package com.hdrescuer.hdrescuer.common;

import android.app.Application;
import android.content.Context;

/**
 * Clase MyApp que contiene el contexto de la aplicación
 * @author Domingo Lopez
 */
public class MyApp  extends Application {

    private static MyApp instance;

    /**
     * Método de clase que devuelve el propio objeto. Patrón singleton
     * @author Domingo Lopez
     * @return MyApp
     */
    public static MyApp getInstance(){
        return instance;
    }

    /**
     * Método que devuelve el propio objeto, el contexto
     * @author Domingo Lopez
     * @return Context
     */
    public static Context getContext(){
        return instance;
    }


    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }



}
