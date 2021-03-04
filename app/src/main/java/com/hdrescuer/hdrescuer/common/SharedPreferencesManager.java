package com.hdrescuer.hdrescuer.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.hdrescuer.hdrescuer.R;

/**
 * Clase de Preferencias compartidas, en la que declaramos un método estático que devuelve una instancia de SharedPreferences
 * y sobre el que podemos "escribir" en un fichero las preferencias de nuestra aplicación.
 * Aquí guardaremos el token que recibe el usuario al hacer loguin
 */
public class SharedPreferencesManager {

    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";

    private SharedPreferencesManager(){}

    private static SharedPreferences getSharedPreferences(){

       return MyApp.getContext().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static void setSomeStringValue(String dataLabel, String dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        editor.putString(dataLabel,dataValue);
        editor.commit();

    }

    public static void setSomeBooleanValue(String dataLabel, Boolean dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        editor.putBoolean(dataLabel,dataValue);
        editor.commit();

    }

    public static String getSomeStringValue(String dataLabel){
        return getSharedPreferences().getString(dataLabel, null);
    }

    public static Boolean getSomeBooleanValue(String dataLabel){
        return getSharedPreferences().getBoolean(dataLabel, false);
    }

}
