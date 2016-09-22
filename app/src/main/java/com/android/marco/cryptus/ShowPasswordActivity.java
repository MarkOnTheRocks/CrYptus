package com.android.marco.cryptus;

/**
 * Created by marco on 18/08/2016.
 */
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.marco.cryptus.Database.DBHelper;

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
    RadioButton[] arrrb;
    SeekBar skb;
    //String methodname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Sono in attività ShowPasswordActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpassword);
        instr = (TextView) findViewById(R.id.textViewInstrSeek);
        skb = (SeekBar) findViewById(R.id.seekBar);
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
                skb.setProgress(0);
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
                skb.setProgress(0);
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
                skb.setProgress(0);
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
                skb.setProgress(0);
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
                skb.setProgress(0);
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
                skb.setProgress(0);
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
                skb.setProgress(0);
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
        System.out.println(mydb.numberOfRows());
        String[] arr = new String[5];
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            sitetocheck = extras.getString("site");
            System.out.println(sitetocheck);

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
                    this.password.setFocusable(false);
                    this.password.setClickable(false);
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
            int Value = extras.getInt("id");
            if(Value>0){
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
                        skb.setProgress(0);
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
                        skb.setProgress(0);
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
                        skb.setProgress(0);
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
                        skb.setProgress(0);
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
                        skb.setProgress(0);
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
                        skb.setProgress(0);
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
                        skb.setProgress(0);
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
                                System.out.println(c.getColumnCount());
                                int idtodelete = 0;
                                while(c.moveToNext()) {
                                    idtodelete = Integer.parseInt(c.getString(0));
                                }
                                System.out.println("Id da eliminare: " + idtodelete);
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

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            String algo="";
            for(int i = 0; i<8; i++) {
                if(arrrb[i].isChecked()) {
                    algo = arrrb[i].getText().toString();
                    System.out.println(algo);
                }
            }
            if(algo.equals("")) {
                return;
            }
            if(Value>0){
                if(mydb.updatePassword(site.getText().toString(), algo, email.getText().toString(), password.getText().toString(), date.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),DbActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertPassword(site.getText().toString(), algo, email.getText().toString(), password.getText().toString(), date.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),DbActivity.class);
                startActivity(intent);
            }
        }
    }
}