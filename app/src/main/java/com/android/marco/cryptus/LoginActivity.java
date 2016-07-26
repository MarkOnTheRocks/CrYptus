package com.android.marco.cryptus;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Marco on 19/02/2016.
 */
public class LoginActivity extends FragmentActivity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    public static String nome;
    public static String id;
    public static String email;
    public static Uri profileImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends", "public_profile", "email");
        final Intent intent = new Intent(this, PrincipalActivity.class);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,GraphResponse response) {
                        if (response != null) {
                            try {
                                String mFbid = object.getString("id");
                                System.out.println("id: " + mFbid);
                                String mFullname = object.getString("name");
                                System.out.println("nome completo: " + mFullname);
                                email = object.getString("email");
                                System.out.println("Ecco la mail da onc " + email);

                                }
                            catch (JSONException e) {

                            }
                        }
                    }
                });
                Bundle bundle=new Bundle();
                bundle.putString("fields","id,email,name");
                request.setParameters(bundle);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("Operazione abortita");
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("Eccezione!!");
                info.setText("Login attempt failed.");
            }
        });


        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if (currentProfile != null) {
                    nome = currentProfile.getName();
                    id = currentProfile.getId();
                    profileImage = currentProfile.getProfilePictureUri(50,50);
                    System.out.println(profileImage);
                    info.setText("Correttamente loggato come: " + nome);
                    startActivity(intent);
                }
                else {
                    info.setText("Ora sei sloggato. Alle tue password ci penso io");

                }

            }
        };


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();

    }
}


