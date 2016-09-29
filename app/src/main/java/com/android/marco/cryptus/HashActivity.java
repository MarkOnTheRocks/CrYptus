package com.android.marco.cryptus;

/**
 * Created by Marco on 09/04/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class HashActivity extends Activity {

    String TAG = "Response";
    Button bt;
    RadioButton rdbt1, rdbt2, rdbt3, rdbt4, rdbt5, rdbt6;
    EditText input;
    TextView textView;
    String getInput;
    String methodName;
    SoapPrimitive res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setContentView(R.layout.soap);
        getWindow().setAllowEnterTransitionOverlap(false);
        bt = (Button) findViewById(R.id.bt);
        input = (EditText) findViewById(R.id.cel);
        input.setText("Give me a seed");
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText("");
            }
        });
        rdbt1 = (RadioButton)findViewById(R.id.radioButton1);
        rdbt2 = (RadioButton)findViewById(R.id.radioButton2);
        rdbt3 = (RadioButton)findViewById(R.id.radioButton3);
        rdbt4 = (RadioButton)findViewById(R.id.radioButton4);
        rdbt5 = (RadioButton)findViewById(R.id.radioButton5);
        rdbt6 = (RadioButton)findViewById(R.id.radioButton6);
        textView = (TextView)findViewById(R.id.textView);
        rdbt1.setChecked(true);
        methodName = (String)rdbt1.getText();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input.getText().toString().equals("")) {
                    Toast.makeText(HashActivity.this, "Please fill the space with a non-empty sequence", Toast.LENGTH_SHORT).show();
                }
                else {
                    getInput = input.getText().toString();
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                }
            }
        });

        rdbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodName = (String) rdbt1.getText();
                rdbt2.setChecked(false);
                rdbt3.setChecked(false);
                rdbt4.setChecked(false);
                rdbt5.setChecked(false);
                rdbt6.setChecked(false);
            }
        });

        rdbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodName = (String) rdbt2.getText();
                rdbt1.setChecked(false);
                rdbt3.setChecked(false);
                rdbt4.setChecked(false);
                rdbt5.setChecked(false);
                rdbt6.setChecked(false);
            }
        });

        rdbt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodName = (String) rdbt3.getText();
                rdbt1.setChecked(false);
                rdbt2.setChecked(false);
                rdbt4.setChecked(false);
                rdbt5.setChecked(false);
                rdbt6.setChecked(false);
            }
        });

        rdbt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodName = (String) rdbt4.getText();
                rdbt1.setChecked(false);
                rdbt2.setChecked(false);
                rdbt3.setChecked(false);
                rdbt5.setChecked(false);
                rdbt6.setChecked(false);
            }
        });

        rdbt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodName = (String) rdbt5.getText();
                rdbt1.setChecked(false);
                rdbt2.setChecked(false);
                rdbt4.setChecked(false);
                rdbt3.setChecked(false);
                rdbt6.setChecked(false);
            }
        });

        rdbt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodName = (String) rdbt6.getText();
                rdbt1.setChecked(false);
                rdbt2.setChecked(false);
                rdbt3.setChecked(false);
                rdbt4.setChecked(false);
                rdbt5.setChecked(false);
            }
        });

    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            calculate();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            if (res != null) {
                Toast.makeText(HashActivity.this, "Response" + " " + res.toString(), Toast.LENGTH_LONG).show();
                textView.setText(res.toString());
            }
            else {
                Toast.makeText(HashActivity.this, "Something went wrong, check your connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void calculate() {
        String SOAP_ACTION = References.address+"/CrYptusServer/CrYptusServer/SHA1";
        String METHOD_NAME = this.methodName;
        String NAMESPACE = "http://server/";
        String URL = References.address+"/CrYptusServer/CrYptusServer?wsdl";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("text", getInput);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = false;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);
            res = (SoapPrimitive) soapEnvelope.getResponse();
            //System.out.println(res.toString());
            //System.out.println("Ho chiesto la risposta");
            //System.out.println(resultString.toString());
            Log.i(TAG, "Result Celsius: " + res);
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
    }

}
