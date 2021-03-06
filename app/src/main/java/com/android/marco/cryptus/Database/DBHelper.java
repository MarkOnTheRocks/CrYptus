package com.android.marco.cryptus.Database;

/**
 * Created by marco on 18/08/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String REGISTRY_TABLE_NAME = "registry";
    public static final String REGISTRY_COLUMN_ID = "id";
    public static final String REGISTRY_COLUMN_SITE = "site";
    public static final String REGISTRY_COLUMN_EMAIL = "email";
    public static final String REGISTRY_COLUMN_PASSWORD = "password";
    public static final String REGISTRY_COLUMN_DATE = "date";
    public static final String REGISTRY_COLUMN_ALG = "alg";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table registry " + "(id integer primary key, site text unique,alg text,email text, password text,date text)");
        //db.execSQL("VACUUM");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS registry");
        onCreate(db);
    }

    public boolean insertPassword (String site, String alg, String email, String password,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("site", site);
        contentValues.put("alg", alg);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("date", date);
        db.insert("registry", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from registry where id="+id+"", null );
        return res;
    }

    public Cursor getIDfromSite(String site) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select id from registry where site=?", new String[] {site});
        //rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
        return res;
    }

    public Cursor getDatafromSite(String site) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from registry where site=?", new String[] {site});
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, REGISTRY_TABLE_NAME);
        return numRows;
    }

    public boolean updatePassword (Integer id, String site, String alg, String email, String password,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("site", site);
        contentValues.put("alg", alg);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("date", date);
        db.update("registry", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean updatePassword (String site, String alg, String email, String password,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("site", site);
        contentValues.put("alg", alg);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("date", date);
        db.update("registry", contentValues, "site = ? ", new String[] { site } );
        return true;
    }

    public void deletePassword (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("registry", "id = ? ", new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllPasswords() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from registry", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(REGISTRY_COLUMN_SITE)));
            res.moveToNext();
        }
        return array_list;
    }

    public String[] getPasswords() {
        SQLiteDatabase db = this.getReadableDatabase();
        int c = numPasswords();
        Cursor res = db.rawQuery("select * from registry", null);
        res.moveToFirst();
        String[] result = new String[c];
        int i = 0;
        while(res.isAfterLast()==false) {
            String a = res.getString(res.getColumnIndex(REGISTRY_COLUMN_SITE));
            String b = res.getString(res.getColumnIndex(REGISTRY_COLUMN_PASSWORD));
            StringBuffer t = new StringBuffer("");
            t.append(a);
            t.append("::::");
            t.append(b);
            result[i] = t.toString();
            res.moveToNext();
            i++;
        }
        return result;
    }

    public void addPasswords(String[] arrs, String[] pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i<arrs.length; i++) {
            contentValues.put("site", arrs[i]);
            contentValues.put("date", "not available");
            contentValues.put("password", pass[i]);
            db.insert("registry", null, contentValues);
        }
    }



    public int numPasswords() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from registry", null);
        return res.getCount();
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.close();
    }
}
