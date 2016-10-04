package com.android.marco.cryptus.Dropbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.marco.cryptus.Database.DBHelper;
import com.android.marco.cryptus.LoginActivity;
import com.android.marco.cryptus.PrincipalActivity;
import com.android.marco.cryptus.R;
import com.dropbox.core.v2.users.FullAccount;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Marco Mancuso on 04/10/2016.
 */

public class MainDBoxActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE = 101;
    public static String ACCESS_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!tokenExists()) {
            Intent intent = new Intent(MainDBoxActivity.this, LoginDBoxActivity.class);
            startActivity(intent);
        }
        ACCESS_TOKEN = retrieveAccessToken();
        getUserAccount();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
    }



    protected void getUserAccount() {
        if (ACCESS_TOKEN == null) {
            return;
        }
        new UserAccountTask(DropBoxClient.getClient(ACCESS_TOKEN), new UserAccountTask.TaskDelegate() {
            @Override
            public void onAccountReceived(FullAccount account) {
                //Print account's info
                Log.d("User", account.getEmail());
                Log.d("User", account.getName().getDisplayName());
                Log.d("User", account.getAccountType().name());
                updateUI(account);
            }
            @Override
            public void onError(Exception error) {
                Log.d("User", "Error receiving account details.");
            }
        }).execute();
    }

    private void updateUI(FullAccount account) {
        ImageView profile = (ImageView) findViewById(R.id.imageView);
        TextView name = (TextView) findViewById(R.id.name_textView);
        TextView email = (TextView) findViewById(R.id.email_textView);
        name.setText(account.getName().getDisplayName());
        email.setText(account.getEmail());
        Picasso.with(this).load(account.getProfilePhotoUrl()).resize(200, 200).into(profile);
    }

    private void upload() {
        if (ACCESS_TOKEN == null) {
            return;
        }

        //Select image to upload
        /*Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Upload to Dropbox"), IMAGE_REQUEST_CODE);
        */
        DBHelper db = new DBHelper(this);
        String[] pass = db.getPasswords();
        FileOutputStream fos;
        try {
            fos = openFileOutput("password.txt", Context.MODE_PRIVATE);
            for(int i = 0; i<pass.length; i++) {
                StringBuffer k = new StringBuffer(pass[i]);
                StringBuffer appo = new StringBuffer("sito: '");
                int start = k.indexOf(":");
                appo.append(k.subSequence(0, start));
                appo.append("'; password: '");
                appo.append(k.subSequence(start + 4, k.length()));
                appo.append("'\n");
                fos.write(appo.toString().getBytes());
                fos.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;
        // Check which request we're responding to
        if (requestCode == IMAGE_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //Image URI received
                File file = new File(UriToPath.getPath(getApplication(), data.getData()));
                if (file != null) {
                    //Initialize UploadTask
                    new UploadTask(DropBoxClient.getClient(ACCESS_TOKEN), file, getApplicationContext()).execute();
                }
            }
        }
    }

    private boolean tokenExists() {
        SharedPreferences prefs = getSharedPreferences("com.android.marco.cryptus.Dropbox", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);
        return accessToken != null;
    }

    private String retrieveAccessToken() {
        //check if ACCESS_TOKEN is stored on previous app launches
        SharedPreferences prefs = getSharedPreferences("com.android.marco.cryptus.Dropbox", Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);
        if (accessToken == null) {
            Log.d("AccessToken Status", "No token found");
            return null;
        } else {
            //accessToken already exists
            Log.d("AccessToken Status", "Token exists");
            return accessToken;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
    }
}


