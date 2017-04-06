package aruwali.googlemapdemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by AmitRuwali on 05-04-2017.
 */

public class GPSDatabase {
    private Context context;
    private DbHelper dbHelper;
    public final String DBNAME="gps1";
    public final int DBVERSION=3;
    public SQLiteDatabase db;
    public final String LATITUDE="LATITUDE";
    public final String LONGITUTE="LONGITUTE";
    public final String LOCID="locationId";
    public final String TABLENAME="location";
    public final String CREATERDB="create table location(locationId integer primary key autoincrement,LATITUDE text not null, LONGITUTE text not null);";
    //const
    public GPSDatabase(Context context){
        this.context=context;
        dbHelper=new DbHelper(context);
    }
    public class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context){
            super(context,DBNAME,null,DBVERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(CREATERDB);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }
    public long insertRow(String latitute, String longitute){
        ContentValues value=new ContentValues();
        value.put(LATITUDE, latitute);
        value.put(LONGITUTE, longitute);
        long val = db.insert(TABLENAME,null,value);
        if(val==1)
            Log.d("INSERT","Inserted iun DBV");
        else
            Log.d("INSERT","unable to Inserted iun DBV");

        return val;

    }
    public Cursor getAllRows(){
        Cursor cursor=db.query(TABLENAME, new String[]{LOCID,LATITUDE,LONGITUTE}, null,null, null, null, null);
        return cursor;
    }

    public boolean isLocationAlreadyVisited(String lat, String longitute) {
        boolean found = false;
        //SELECT LATITUDE, LONGITUTE FROM location WHERE  LATITUDE=? and LONGITUTE=?
        Cursor locationInfoCur = db.query(TABLENAME, new String[]{LATITUDE,LONGITUTE}, " LATITUDE=? and LONGITUTE=?", new String[] { lat.substring(4), longitute.substring(4) }, null, null, null);

        if (locationInfoCur != null && locationInfoCur.moveToFirst()) {
            found = true;
        }

        return found;
    }

    public void open() throws SQLException{
        db=  dbHelper.getWritableDatabase();

        Log.d("OPN","OPEN DB...............");
        //return true;
    }
    public void close(){
        dbHelper.close();
        //return true;
    }
}
