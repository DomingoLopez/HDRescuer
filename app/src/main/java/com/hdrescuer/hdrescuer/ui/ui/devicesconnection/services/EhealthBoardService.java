package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services;

import android.content.Intent;
import android.util.Log;

import com.hdrescuer.hdrescuer.common.MyApp;
import com.hdrescuer.hdrescuer.data.EHealthBoardRepository;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class EhealthBoardService extends Thread {

    EHealthBoardRepository eHealthBoardRepository;
    InputStream inputStream;
    OutputStream outputStream;

    public static String STATUS;


    //Array temporal para manejar los bytes que se queden sin procesar
    byte[] tmp = null;

    public EhealthBoardService(EHealthBoardRepository eHealthBoardRepository, InputStream inputStream, OutputStream outputStream){

        this.eHealthBoardRepository = eHealthBoardRepository;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {

        while(EhealthBoardService.STATUS.equals("ACTIVO")){

            try{
                //Espera de cortesía para que se cargue algo el buffer
                int bytesDisponibles = this.inputStream.available();
                if(bytesDisponibles > 0){


                    byte[] bytesLeidos = new byte[bytesDisponibles];

                    this.inputStream.read(bytesLeidos);

                    //Añadimos los bytes restantes de la anterior iteración
                    byte[] bytesRecibidos;

                    if(tmp != null){
                        bytesDisponibles = tmp.length + bytesLeidos.length;
                        bytesRecibidos = new byte[bytesDisponibles];
                        System.arraycopy(tmp, 0, bytesRecibidos, 0, tmp.length);
                        System.arraycopy(bytesLeidos,0, bytesRecibidos, tmp.length, bytesLeidos.length);

                    }else{
                        bytesRecibidos = bytesLeidos;
                    }


                    //Recorremos los bytes hasta encontrar el delimitador mandado desde arduino para cada pack de datos
                    //En este caso he puesto un \n como delimitador. También habrá que parsear los datos

                    int inicio_cadena = 0;
                    int final_cadena = 0;
                    for(int i = 0; i<bytesDisponibles; i++){

                        byte mibyte = bytesRecibidos[i];
                        if(mibyte == 43) { //El + es 43 en ASCII, ASÍ que lo usaremos como delimitador de las cadenas de datos
                            final_cadena = i ;
                            byte[] bufferFinal = Arrays.copyOf(bytesRecibidos,(final_cadena - inicio_cadena));
                            String cadena = new String(bufferFinal);
                            parseData(cadena);

                            inicio_cadena = final_cadena+1; //+1 para que no coja el '+' que se ha quedado ahí en medio
                        }


                    }

                    if(bytesDisponibles > final_cadena){ //Quiere decir que nos hemos dejado Bytes sin procesar y en la siguiente lectura del buffer no estarán...
                        //Tenemos que añadirselos al array que procesaremos posteriormente
                        int diferencia = (bytesDisponibles - final_cadena) -1;
                        tmp = new byte[diferencia];
                        //Los copiamos al array de bytes temporal
                        System.arraycopy(bytesRecibidos, final_cadena+1, tmp, 0, diferencia);

                    }else{
                        tmp = null;
                    }


                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


    void parseData(String cadena){

        if(cadena.contains("#")){ //Damos el dato como bueno

            String cadena_resultante = cadena.replace("#","");

            String[] partes = cadena_resultante.split(":");

            if(partes.length == 2){ //Si hay dos partes...Si hay más es que algo ha ido mal
                //Formato-> tipoDato:valor

                if(partes[0].equals("pulse"))
                    this.eHealthBoardRepository.setBMP(Integer.parseInt(partes[1]));
                else if(partes[0].equals("oxigensaturation"))
                    this.eHealthBoardRepository.setOxBlood(Integer.parseInt(partes[1]));

            }

        }else{

        }

    }


}
