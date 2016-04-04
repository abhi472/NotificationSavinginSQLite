package com.example.abhishek.notificationsavinginsqlite.DatabaseFiles;

import android.content.ContentResolver;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Aylar-HP on 12/10/2015.
 */
public class NotStatus extends SQLiteOpenHelper {


    public static final String Table_Name = "notifications";
    public static final String COLUMN_ID = "_id";
    public static final String TITLE = "title";
    public static final String DESC = "desc";
    public static final String URI = "uri";
    public static final String STOREID = "storeid";
    public static final String IMAGE = "img";
    public static final String EXPDATE = "expDate";
    public static final String AUTHORITY = "com.example.abhishek.notification";
    public static final Uri    CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Table_Name);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.abhishek.notficationsavinginsqlite";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.abhishek.notificationsavinginsqlite";
    //public static final String DATABASE_NAME = "notification.db";
    public static final int DATABASE_VERSION = 1;
    public static final int CONTENT_TYPE_DIR =1;
    public static final int CONTENT_TYPE_ITEM=2;
   // private static String DB_PATH = "/data/data/com.example.abhishek.notificationsavinginsqlite/databases/";
    private static String DB_NAME = "notification.db";

    public NotStatus(Context context) {

        super(context, DB_NAME, null,DATABASE_VERSION);
       // DB_PATH = myContext.getFilesDir().getPath() +"com.example.abhishek.notificationsavinginsqlite/databases/";
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                "CREATE TABLE "+Table_Name+" ("+"_id integer primary key autoincrement," +TITLE +" TEXT,"+
                        DESC+" TEXT,"+URI+" TEXT,"+STOREID+" TEXT,"+IMAGE+" TEXT,"+EXPDATE+" TEXT)";

        Log.d("TaskDBHelper", "Query to form table: " + sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS "+Table_Name);
        onCreate(sqlDB);
    }}