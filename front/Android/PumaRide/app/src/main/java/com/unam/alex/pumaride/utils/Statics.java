package com.unam.alex.pumaride.utils;

/**
 * Created by alex on 31/10/16.
 */
public class Statics {
    private static Statics ourInstance = new Statics();

    public static Statics getInstance() {
        return ourInstance;
    }

    private Statics() {
    }
    public static String SERVER_BASE_URL = "http://pumaride.codingo.mx/";
    public static String AUXILIAR_SERVER_BASE_URL = "http://35.164.20.251:8000/";
    public static String CHAT_SERVER_BASE_URL = "http://184.173.94.0:12439/";
}
