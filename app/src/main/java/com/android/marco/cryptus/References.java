package com.android.marco.cryptus;

import java.util.Date;

/**
 * Created by Marco Mancuso on 13/06/2016.
 */
public class References {

    public static final String address = "http://192.168.0.4:8084";
    public static String id;
    public static String name;
    public static String indirizzo;
    public static String IPaddr;


    public static String getDate() {
        Date date = new Date();
        return date.toString();
    }

}
