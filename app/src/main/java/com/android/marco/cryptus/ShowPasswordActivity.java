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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.marco.cryptus.Database.DBHelper;

public class ShowPasswordActivity extends Activity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    TextView site ;
    TextView email;
    TextView password;
    TextView date;
    TextView alg;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpassword);
        site = (TextView) findViewById(R.id.editTextSite);
        email = (TextView) findViewById(R.id.editTextEmail);
        password = (TextView) findViewById(R.id.editTextPassword);
        date = (TextView) findViewById(R.id.editTextDate);
        alg = (TextView) findViewById(R.id.editTextAlg);
        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String site = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_SITE));
                String email = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_EMAIL));
                String password = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_PASSWORD));
                String date = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_DATE));
                String alg = rs.getString(rs.getColumnIndex(DBHelper.REGISTRY_COLUMN_ALG));

                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                this.site.setText(site);
                this.site.setFocusable(false);
                this.site.setClickable(false);

                this.email.setText((CharSequence)email);
                this.email.setFocusable(false);
                this.email.setClickable(false);

                this.password.setText((CharSequence)password);
                this.password.setFocusable(false);
                this.password.setClickable(false);

                this.date.setText((CharSequence)date);
                this.date.setFocusable(false);
                this.date.setClickable(false);

                this.alg.setText((CharSequence)alg);
                this.alg.setFocusable(false);
                this.alg.setClickable(false);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
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
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                site.setEnabled(true);
                site.setFocusableInTouchMode(true);
                site.setClickable(true);


                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                password.setEnabled(true);
                password.setFocusableInTouchMode(true);
                password.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                alg.setEnabled(true);
                alg.setFocusableInTouchMode(true);
                alg.setClickable(true);

                return true;
            case R.id.Delete_Password:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deletePassword)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deletePassword(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),DbActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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
            if(Value>0){
                if(mydb.updatePassword(id_To_Update,site.getText().toString(), alg.getText().toString(), email.getText().toString(), password.getText().toString(), date.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),DbActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertPassword(site.getText().toString(), alg.getText().toString(), email.getText().toString(), password.getText().toString(), date.getText().toString())){
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