package com.hdrescuer.hdrescuer.ui.ui.devicesconnection.services;

import com.hdrescuer.hdrescuer.data.EHealthBoardRepository;

import java.io.InputStream;
import java.io.OutputStream;



public class EhealthBoardService extends Thread {

    EHealthBoardRepository eHealthBoardRepository;
    InputStream inputStream;
    OutputStream outputStream;

    public static String STATUS;

    public EhealthBoardService(EHealthBoardRepository eHealthBoardRepository, InputStream inputStream, OutputStream outputStream){

        this.eHealthBoardRepository = eHealthBoardRepository;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {

    }
}
