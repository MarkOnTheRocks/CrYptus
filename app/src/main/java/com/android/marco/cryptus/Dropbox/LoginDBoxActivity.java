package com.android.marco.cryptus.Dropbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.marco.cryptus.R;
import com.dropbox.core.android.Auth;

/**
 * Created by Marco Mancuso on 04/10/2016.
 */

public class LoginDBoxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dbox);
        Button SignInButton = (Button) findViewById(R.id.sign_in_button);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.startOAuth2Authentication(getApplicationContext(), getString(R.string.APP_KEY));

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAccessToken();

    }

    public void getAccessToken() {
        String accessToken = Auth.getOAuth2Token(); //generate Access Token
        //MainDBoxActivity.ACCESS_TOKEN = accessToken;
        if (accessToken != null) {
            //Store accessToken in SharedPreferences
            SharedPreferences prefs = getSharedPreferences("com.android.marco.cryptus.Dropbox", Context.MODE_PRIVATE);
            prefs.edit().putString("access-token", accessToken).apply();
            System.out.println("Ho avuto accesso");
            //Proceed to MainActivity
            Intent intent = new Intent(LoginDBoxActivity.this, MainDBoxActivity.class);
            startActivity(intent);
        }
    }
}

