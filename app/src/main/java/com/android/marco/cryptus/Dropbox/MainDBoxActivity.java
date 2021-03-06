package com.android.marco.cryptus.Dropbox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.marco.cryptus.Database.DBHelper;
import com.android.marco.cryptus.LoginActivity;
import com.android.marco.cryptus.PrincipalActivity;
import com.android.marco.cryptus.R;
import com.android.marco.cryptus.ShowPasswordActivity;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco Mancuso on 04/10/2016.
 */

public class MainDBoxActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE = 101;
    public static String ACCESS_TOKEN;
    private String TAG = "Response";

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

        FloatingActionButton fabup = (FloatingActionButton) findViewById(R.id.fabup);
        FloatingActionButton fabdown = (FloatingActionButton) findViewById(R.id.fabdown);
        fabup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
        fabdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
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
        DBHelper db = new DBHelper(this);
        String[] pass = db.getPasswords();
        FileOutputStream fos;
        try {
            File f = new File("/data/user/0/com.android.marco.cryptus/files/passwordtoup.txt");
            if(f.exists()) {
                //System.out.println("trovato duplicato");
                f.delete();
            }
            fos = openFileOutput("passwordtoup.txt", Context.MODE_PRIVATE);
            for(int i = 0; i<pass.length; i++) {
                StringBuffer k = new StringBuffer(pass[i]);
                StringBuffer appo = new StringBuffer("sito: '");
                int start = k.indexOf(":");
                appo.append(k.subSequence(0, start));
                appo.append("'; password: '");
                appo.append(k.subSequence(start + 4, k.length()));
                appo.append("'\n");
                fos.write(appo.toString().getBytes());
            }
            fos.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        File file = new File("/data/user/0/com.android.marco.cryptus/files/passwordtoup.txt");
        new UploadTask(DropBoxClient.getClient(ACCESS_TOKEN), file, getApplicationContext()).execute();
    }


    private void download() {
        if (ACCESS_TOKEN == null) {
            return;
        }
        DBHelper db = new DBHelper(this);
        String[] pass = db.getPasswords();

        new DownloadTask(DropBoxClient.getClient(ACCESS_TOKEN), getApplicationContext()).execute();
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


    class UploadTask extends AsyncTask {

        private final ProgressDialog dialog = new ProgressDialog(MainDBoxActivity.this);

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            this.dialog.setMessage("Processing the upload...");
            this.dialog.show();
        }

        private DbxClientV2 dbxClient;
        private File file;
        private Context context;

        UploadTask(DbxClientV2 dbxClient, File file, Context context) {
            this.dbxClient = dbxClient;
            this.file = file;
            this.context = context;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                // Upload to Dropbox
                InputStream inputStream = new FileInputStream(file);
                dbxClient.files().uploadBuilder("/" + file.getName()) //Path in the user's Dropbox to save the file.
                        .withMode(WriteMode.OVERWRITE) //always overwrite existing file
                        .uploadAndFinish(inputStream);
                Log.d("Upload Status", "Success");
            }
            catch (DbxException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dialog.dismiss();
            Toast.makeText(context, "Passwords uploaded successfully", Toast.LENGTH_SHORT).show();
        }
    }

    class DownloadTask extends AsyncTask {

        private final ProgressDialog dialog = new ProgressDialog(MainDBoxActivity.this);

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            this.dialog.setMessage("Processing the download...");
            this.dialog.show();
        }

        private DbxClientV2 dbxClient;
        private Context context;

        DownloadTask(DbxClientV2 dbxClient, Context context) {
            this.dbxClient = dbxClient;
            this.context = context;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                // Download from Dropbox
                File file = new File("/data/user/0/com.android.marco.cryptus/files/password.txt");
                OutputStream outputStream = new FileOutputStream(file);
                StringBuffer sb = new StringBuffer(file.getName());
                int c = sb.lastIndexOf("t");
                sb.replace(c-3,c, "toup.txt");
                dbxClient.files().download("/passwordtoup.txt").download(outputStream); //Path in the user's Dropbox to save the file.
                Log.d("Upload Status", "Success");
                DBHelper db = new DBHelper(this.context);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                List<String> lsite = new ArrayList<>();
                List<String> lpass = new ArrayList<>();
                String s = br.readLine();
                while(s!=null) {
                    sb = new StringBuffer(s);
                    c = sb.indexOf(";");
                    String a = sb.substring(7, c-1);
                    String b = sb.substring(c+13, sb.length()-1);
                    lsite.add(a);
                    lpass.add(b);
                    s = br.readLine();
                }
                int len = lsite.size();
                String[] arrs = new String[len];
                arrs = lsite.toArray(arrs);
                String[] arrp = new String[len];
                arrp = lpass.toArray(arrp);
                db.addPasswords(arrs, arrp);
            }
            catch (DbxException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            dialog.dismiss();
            Toast.makeText(context, "Passwords downloaded successfully", Toast.LENGTH_SHORT).show();
        }
    }
}




