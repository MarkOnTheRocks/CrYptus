package com.android.marco.cryptus;

/**
 * Created by marco on 18/08/2016.
 */
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.android.marco.cryptus.Database.DBHelper;
import java.util.ArrayList;

public class DbActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        System.out.println("Sono in attività DbActivity");
        mydb = new DBHelper(this);
        ArrayList array_list = mydb.getAllPasswords();
        System.out.println("Numero di passwords: " + array_list.size());
        final ArrayList<String> temp = new ArrayList();
        temp.addAll(array_list);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                int id_To_Search = arg2+1;
                String site = temp.get(arg2);
                System.out.println(site);
                System.out.println("il valore di idtos è: " + id_To_Search);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_To_Search);
                dataBundle.putString("site", site);
                Intent intent = new Intent(getApplicationContext(),ShowPasswordActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.db_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.item1:Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                Intent intent = new Intent(getApplicationContext(),ShowPasswordActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
    */

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, PrincipalActivity.class);
        startActivity(i);
    }
}