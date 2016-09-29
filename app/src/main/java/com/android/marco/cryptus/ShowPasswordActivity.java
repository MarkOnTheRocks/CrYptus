package com.android.marco.cryptus;

/**
 * Created by marco on 18/08/2016.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.marco.cryptus.Database.DBHelper;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ShowPasswordActivity extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    TextView site;
    TextView email;
    TextView password, tpass;
    TextView date;
    TextView alg, talg, instr;
    RadioButton rb0, rb7, rb8, rb9, rb10, rb11, rb12, rb13;
    int id_To_Update = 0;
    String sitetocheck;
    Button saver;
    int Value;
    RadioButton[] arrrb;
    SeekBar skb;
    StringBuffer position = new StringBuffer("");
    private SoapPrimitive res;
    private String outofpass;
    private String TAG = "Response";
    private String input;
    private String methodNamet;
    PopupWindow fPopupWindowStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //System.out.println("Sono in attività ShowPasswordActivity");
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        getWindow().setAllowEnterTransitionOverlap(false);
        super.onCreate(savedInstanceState);
        fPopupWindowStates = new PopupWindow();
        setContentView(R.layout.activity_showpassword);
        instr = (TextView) findViewById(R.id.textViewInstrSeek);
        skb = (SeekBar) findViewById(R.id.seekBar);
        skb.setProgress(10);
        skb.setMax(40);
        skb.computeScroll();
        site = (EditText) findViewById(R.id.editTextSite);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        tpass = (TextView) findViewById(R.id.textViewPass);
        password.setVisibility(View.INVISIBLE);
        tpass.setVisibility(View.INVISIBLE);
        date = (EditText) findViewById(R.id.editTextDate);
        date.setText(References.getDate());
        this.date.setFocusable(false);
        this.date.setClickable(false);
        alg = (EditText) findViewById(R.id.editTextAlg);
        talg = (TextView) findViewById(R.id.textViewAlg);
        alg.setVisibility(View.INVISIBLE);
        talg.setVisibility(View.INVISIBLE);
        saver = (Button)findViewById(R.id.button1);
        rb0 = (RadioButton) findViewById(R.id.radioButton);
        rb0.setChecked(true);
        rb7 = (RadioButton) findViewById(R.id.radioButton7);
        rb8 = (RadioButton) findViewById(R.id.radioButton8);
        rb9 = (RadioButton) findViewById(R.id.radioButton9);
        rb10 = (RadioButton) findViewById(R.id.radioButton10);
        rb11 = (RadioButton) findViewById(R.id.radioButton11);
        rb12 = (RadioButton) findViewById(R.id.radioButton12);
        rb13 = (RadioButton) findViewById(R.id.radioButton13);
        arrrb = new RadioButton[] {rb0, rb7, rb8, rb9, rb10, rb11, rb12, rb13};
        skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                //StringBuffer s = new StringBuffer(instr.getText().toString());
                String s = concatener(progress);
                //System.out.println(progress);
                instr.setText(s);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
        rb0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this.methodname = rb0.getText().toString();
                skb.setProgress(10);
                skb.setMax(40);
                tpass.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                skb.setVisibility(View.VISIBLE);
                instr.setVisibility(View.VISIBLE);
                rbSelector(rb0);
            }
        });
        rb7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //methodname = rb7.getText().toString();
                skb.setProgress(10);
                skb.setMax(64);
                tpass.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                skb.setVisibility(View.VISIBLE);
                instr.setVisibility(View.VISIBLE);
                rbSelector(rb7);
            }
        });
        rb8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //methodname = rb8.getText().toString();
                skb.setProgress(10);
                tpass.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                skb.setVisibility(View.VISIBLE);
                instr.setVisibility(View.VISIBLE);
                rbSelector(rb8);

            }
        });
        rb9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //methodname = rb9.getText().toString();
                skb.setProgress(10);
                skb.setMax(32);
                tpass.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                skb.setVisibility(View.VISIBLE);
                instr.setVisibility(View.VISIBLE);
                rbSelector(rb9);
            }
        });
        rb10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //methodname = rb10.getText().toString();
                skb.setProgress(10);
                skb.setMax(50);
                tpass.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                skb.setVisibility(View.VISIBLE);
                instr.setVisibility(View.VISIBLE);
                rbSelector(rb10);
            }
        });
        rb11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skb.setProgress(10);
                skb.setMax(34);
                tpass.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                skb.setVisibility(View.VISIBLE);
                instr.setVisibility(View.VISIBLE);
                //methodname = rb11.getText().toString();
                rbSelector(rb11);
            }
        });
        rb12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //methodname = rb12.getText().toString();
                skb.setProgress(10);
                skb.setMax(50);
                tpass.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                skb.setVisibility(View.VISIBLE);
                instr.setVisibility(View.VISIBLE);
                rbSelector(rb12);
            }
        });
        rb13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //methodname = rb13.getText().toString();
                tpass.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);
                skb.setVisibility(View.INVISIBLE);
                instr.setVisibility(View.INVISIBLE);
                rbSelector(rb13);
            }
        });
        mydb = new DBHelper(this);
        //System.out.println(mydb.numberOfRows());
        String[] arr = new String[5];
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            sitetocheck = extras.getString("site");
            //System.out.println(sitetocheck);
            if(Value>0){
                Cursor rs = mydb.getDatafromSite(sitetocheck);
                id_To_Update = Value;
                    if(rs.moveToFirst()) {
                        arr[0] = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_SITE));
                        arr[1] = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_EMAIL));
                        arr[2] = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_PASSWORD));
                        arr[3] = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_DATE));
                        arr[4] = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_ALG));
                    }
                else {
                        Toast.makeText(this, "Empty database", Toast.LENGTH_LONG);
                    }
                    if (!rs.isClosed()) {
                        rs.close();
                    }
                    talg.setVisibility(View.VISIBLE);
                    tpass.setVisibility(View.VISIBLE);
                    saver.setVisibility(View.INVISIBLE);
                    this.site.setText(arr[0]);
                    this.site.setFocusable(false);
                    this.site.setClickable(false);
                    this.email.setText((CharSequence) arr[1]);
                    this.email.setFocusable(false);
                    this.email.setClickable(false);
                    this.password.setVisibility(View.VISIBLE);
                    this.password.setText((CharSequence) arr[2]);
                    this.password.setFocusable(true);
                    this.password.setEnabled(true);
                    this.password.setTextIsSelectable(true);
                    this.date.setText((CharSequence) arr[3]);
                    this.date.setFocusable(false);
                    this.date.setClickable(false);
                    this.alg.setVisibility(View.VISIBLE);
                    this.alg.setText((CharSequence) arr[4]);
                    this.alg.setFocusable(false);
                    this.alg.setClickable(false);
                    this.rb0.setVisibility(View.INVISIBLE);
                    this.rb7.setVisibility(View.INVISIBLE);
                    this.rb8.setVisibility(View.INVISIBLE);
                    this.rb9.setVisibility(View.INVISIBLE);
                    this.rb10.setVisibility(View.INVISIBLE);
                    this.rb11.setVisibility(View.INVISIBLE);
                    this.rb12.setVisibility(View.INVISIBLE);
                    this.rb13.setVisibility(View.INVISIBLE);
                    skb.setVisibility(View.INVISIBLE);
                    this.instr.setVisibility(View.INVISIBLE);
            }
        }
    }




    private String concatener(int number) {
        if(number>0) {
            String a = "Length of your desired password: ";
            String res = a.concat(number + "");
            return res;
        }
        else {
            return "Choose the length of your desired password";
        }
    }

    public void rbSelector(RadioButton rb) {
        for(int i = 0; i<arrrb.length; i++) {
            RadioButton radioButton = arrrb[i];
            if(!radioButton.getText().toString().equals(rb.getText().toString())) {
                radioButton.setChecked(false);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            this.Value = extras.getInt("id");
            if(this.Value>0){
                getMenuInflater().inflate(R.menu.show_password, menu);
            }

            else{
                getMenuInflater().inflate(R.menu.db_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.Edit_Password:
                rb0.setChecked(true);
                skb.setProgress(10);
                skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // TODO Auto-generated method stub
                        //StringBuffer s = new StringBuffer(instr.getText().toString());
                        String s = concatener(progress);
                        //System.out.println(progress);
                        instr.setText(s);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // TODO Auto-generated method stub
                    }
                });
                rb0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //this.methodname = rb0.getText().toString();
                        skb.setProgress(10);
                        skb.setMax(40);
                        tpass.setVisibility(View.INVISIBLE);
                        password.setVisibility(View.INVISIBLE);
                        skb.setVisibility(View.VISIBLE);
                        instr.setVisibility(View.VISIBLE);
                        rbSelector(rb0);
                    }
                });
                rb7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //methodname = rb7.getText().toString();
                        skb.setProgress(10);
                        skb.setMax(64);
                        tpass.setVisibility(View.INVISIBLE);
                        password.setVisibility(View.INVISIBLE);
                        skb.setVisibility(View.VISIBLE);
                        instr.setVisibility(View.VISIBLE);
                        rbSelector(rb7);
                    }
                });
                rb8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //methodname = rb8.getText().toString();
                        skb.setProgress(10);
                        tpass.setVisibility(View.INVISIBLE);
                        password.setVisibility(View.INVISIBLE);
                        skb.setVisibility(View.VISIBLE);
                        instr.setVisibility(View.VISIBLE);
                        rbSelector(rb8);

                    }
                });
                rb9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //methodname = rb9.getText().toString();
                        skb.setProgress(10);
                        skb.setMax(32);
                        tpass.setVisibility(View.INVISIBLE);
                        password.setVisibility(View.INVISIBLE);
                        skb.setVisibility(View.VISIBLE);
                        instr.setVisibility(View.VISIBLE);
                        rbSelector(rb9);
                    }
                });
                rb10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //methodname = rb10.getText().toString();
                        skb.setProgress(10);
                        skb.setMax(50);
                        tpass.setVisibility(View.INVISIBLE);
                        password.setVisibility(View.INVISIBLE);
                        skb.setVisibility(View.VISIBLE);
                        instr.setVisibility(View.VISIBLE);
                        rbSelector(rb10);
                    }
                });
                rb11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        skb.setProgress(10);
                        skb.setMax(34);
                        tpass.setVisibility(View.INVISIBLE);
                        password.setVisibility(View.INVISIBLE);
                        skb.setVisibility(View.VISIBLE);
                        instr.setVisibility(View.VISIBLE);
                        //methodname = rb11.getText().toString();
                        rbSelector(rb11);
                    }
                });
                rb12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //methodname = rb12.getText().toString();
                        skb.setProgress(10);
                        skb.setMax(50);
                        tpass.setVisibility(View.INVISIBLE);
                        password.setVisibility(View.INVISIBLE);
                        skb.setVisibility(View.VISIBLE);
                        instr.setVisibility(View.VISIBLE);
                        rbSelector(rb12);
                    }
                });
                rb13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //methodname = rb13.getText().toString();
                        skb.setProgress(10);
                        skb.setMax(50);
                        tpass.setVisibility(View.VISIBLE);
                        password.setVisibility(View.VISIBLE);
                        skb.setVisibility(View.INVISIBLE);
                        instr.setVisibility(View.INVISIBLE);
                        rbSelector(rb13);
                    }
                });
                for(int i = 0; i<8; i++) {
                    arrrb[i].setVisibility(View.VISIBLE);
                }
                instr.setVisibility(View.VISIBLE);
                skb.setVisibility(View.VISIBLE);
                tpass.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                talg.setVisibility(View.INVISIBLE);
                alg.setVisibility(View.INVISIBLE);
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                site.setEnabled(true);
                site.setFocusableInTouchMode(true);
                site.setClickable(false);
                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);
                password.setEnabled(true);
                password.setFocusableInTouchMode(true);
                password.setClickable(true);
                date.setText(References.getDate());
                alg.setEnabled(true);
                alg.setFocusableInTouchMode(true);
                alg.setClickable(true);
                saver.setText("SAVE UPDATES");
                return true;

            case R.id.Delete_Password:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deletePassword).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Cursor c = mydb.getIDfromSite(sitetocheck);
                                //System.out.println(c.getColumnCount());
                                int idtodelete = 0;
                                while(c.moveToNext()) {
                                    idtodelete = Integer.parseInt(c.getString(0));
                                }
                                //System.out.println("Id da eliminare: " + idtodelete);
                                mydb.deletePassword(idtodelete);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),DbActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        mydb.close();
    }
    */

    private class AsyncCallWSI extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(ShowPasswordActivity.this);

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
            this.dialog.setMessage("Processing...");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            //getPostion();
            calculate();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (res != null) {
                Toast.makeText(ShowPasswordActivity.this, "Response" + " " + res.toString(), Toast.LENGTH_LONG).show();
                //textView.setText(res.toString());
                StringBuffer s = new StringBuffer(res.toString().substring(0, skb.getProgress()));
                if(Value>0){
                    if(mydb.updatePassword(site.getText().toString(), methodNamet, email.getText().toString(), s.toString(), date.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),DbActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(mydb.insertPassword(site.getText().toString(), methodNamet, email.getText().toString(), s.toString(), date.getText().toString())){
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(),DbActivity.class);
                    startActivity(intent);
                }
                dialog.dismiss();
            }
            else {
                Toast.makeText(ShowPasswordActivity.this, "Qualcosa è andato male, controlla la tua connessione", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }
    }

    public void calculate() {
        String SOAP_ACTION = References.address+"/CrYptusServer/CrYptusServer/SHA1";
        String METHOD_NAME = this.methodNamet;
        String NAMESPACE = "http://server/";
        String URL = References.address+"/CrYptusServer/CrYptusServer?wsdl";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("text", input);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = false;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            this.res = (SoapPrimitive) soapEnvelope.getResponse();
            this.outofpass = res.toString();
            //System.out.println(res.toString());
            //System.out.println("Ho chiesto la risposta");
            //System.out.println(resultString.toString());
            Log.i(TAG, "Result Password: " + res);
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    public static String randomGenerator() {
        String s = References.getDate().toString();
        String id = References.id;
        String name = References.name;
        int num = s.length() + id.length() + name.length();
        char arr[] = new char[num];
        for(int i = 0; i<s.length(); i++) {
            arr[i] = s.charAt(i);
        }
        for(int i = s.length(); i<s.length() + id.length(); i++) {
            arr[i] = id.charAt(i-s.length());
        }
        for(int i = s.length() + id.length(); i<s.length() + id.length() + name.length(); i++) {
            arr[i] = name.charAt(i-s.length()-id.length());
        }
        char[] arrb = new char[arr.length];
        for(int i = 0; i<arrb.length; i++) {
            int k = (int)(Math.random()*Math.pow(10, 2));
            k = (k%95)+32;
            arrb[i] = (char)k;
        }
        StringBuffer res = new StringBuffer("");
        for(int i = 0; i<arrb.length*2; i++) {
            if(i%2==0) {
                res.append(arr[i/2]);
            }
            else {
                res.append(arrb[i/2]);
            }
        }
        res.append(';');
        return res.toString();
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            String algo="";
            for(int i = 0; i<8; i++) {
                if(arrrb[i].isChecked()) {
                    algo = arrrb[i].getText().toString();
                    //System.out.println(algo);
                }
            }
            if(algo.equals("")) {
                return;
            }
            if(site.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Insert site", Toast.LENGTH_SHORT).show();
                return;
            }
            if(rb13.isChecked() && password.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "This option requires you to insert a password", Toast.LENGTH_LONG).show();
                return;
            }

            if(rb13.isChecked()) {
                if(Value>0){
                    if(mydb.updatePassword(site.getText().toString(), rb13.getText().toString(), email.getText().toString(), password.getText().toString(), date.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),DbActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(mydb.insertPassword(site.getText().toString(), rb13.getText().toString(), email.getText().toString(), password.getText().toString(), date.getText().toString())){
                        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(getApplicationContext(),DbActivity.class);
                    startActivity(intent);
                }
            }
            else {
                methodNamet = algo;
                if(rb12.isChecked()) {
                    methodNamet = "cryptus";
                }
                input = randomGenerator();
                AsyncCallWSI task = new AsyncCallWSI();
                task.execute();
            }
        }
    }
}