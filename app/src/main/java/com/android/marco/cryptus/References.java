package com.android.marco.cryptus;

import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import java.util.Date;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Marco Mancuso on 13/06/2016.
 */
public class References {

    public static final String address = "http://192.168.0.11:8084";
    public static String id;
    public static String name;
    public static String indirizzo;
    public static String IPaddr;
    public static String email;


    public static StringBuffer getDate() {
        Date date = new Date();
        StringBuffer sb = new StringBuffer(date.toString());
        int from = sb.indexOf("G");
        int end = sb.indexOf("+");
        end+=7;
        sb.delete(from, end);
        return sb;
    }

}
