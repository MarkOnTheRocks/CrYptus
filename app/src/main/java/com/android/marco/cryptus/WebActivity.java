package com.android.marco.cryptus;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Marco Mancuso on 21/09/2016.
 */

public class WebActivity extends Activity {

    private WebView mwebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mwebview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = mwebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mwebview.getSettings().setLoadWithOverviewMode(true);
        mwebview.getSettings().setUseWideViewPort(true);
        if(!isNetworkAvailable(this)) {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Your Internet Connection is not available at the moment. Please try again later.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
        mwebview.computeScroll();
        mwebview.loadUrl("https://crackstation.net");
    }

    public boolean isNetworkAvailable(Context context) {
        boolean value = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec.getActiveNetworkInfo().isConnected()) {
            value = true;
        }
        // Log.d ("1", Boolean.toString(value) );
        return value;
    }

}
