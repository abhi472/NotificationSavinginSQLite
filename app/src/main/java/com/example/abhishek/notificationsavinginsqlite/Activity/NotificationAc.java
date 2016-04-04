package com.example.abhishek.notificationsavinginsqlite.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.abhishek.notificationsavinginsqlite.Adapters.RecyclePageAdapter;
import com.example.abhishek.notificationsavinginsqlite.Callbacks.ICallBack;
import com.example.abhishek.notificationsavinginsqlite.DatabaseFiles.NotStatus;
import com.example.abhishek.notificationsavinginsqlite.Models.Content;
import com.example.abhishek.notificationsavinginsqlite.Models.NotContent;
import com.example.abhishek.notificationsavinginsqlite.R;
import com.example.abhishek.notificationsavinginsqlite.Singletons.ApiMAnager;
import com.facebook.stetho.Stetho;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotificationAc extends AppCompatActivity implements ICallBack {

    ArrayList<NotContent> val = new ArrayList<>();
    ObjectMapper om = new ObjectMapper();
    NotStatus helper;
    Uri uri;
    NotStatus notStatus = new NotStatus(this);
    Cursor cursor;
    String url = "https://gist.githubusercontent.com/abhi472/f7381ecb42e7e8c1d1f1ccc78403ac1f/raw/ad432913f7709f24f306b0b40f9dcc2945a97ee3/file";
    ArrayList<NotContent> notif = new ArrayList<>();
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        rv = (RecyclerView) findViewById(R.id.recycle);
        ApiMAnager.getInstance().sendReq(this, url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            helper = new NotStatus(NotificationAc.this);
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            uri = NotStatus.CONTENT_URI;

            for (int i = 0; i < notif.size(); i++) {
                values.put(NotStatus.TITLE, notif.get(i).title);
                values.put(NotStatus.DESC, notif.get(i).alert);
                values.put(NotStatus.URI, notif.get(i).uri);
                values.put(NotStatus.STOREID, notif.get(i).storeId);
                values.put(NotStatus.IMAGE, notif.get(i).img);
                values.put(NotStatus.EXPDATE, notif.get(i).expdate);
                getApplicationContext().getContentResolver().insert(uri, values);
            }


        }
        if (id == R.id.one) {
            try {
                updateUI();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(RecyclerView rv, ArrayList<NotContent> cust) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(new RecyclePageAdapter(this, cust));
    }

    private void updateUI() throws ParseException {
        uri = NotStatus.CONTENT_URI;
        cursor = this.getContentResolver().query(uri, null, null, null, null);
        ArrayList<NotContent> expired = new ArrayList<>();
        NotContent nc = new NotContent();
        ArrayList<NotContent> live = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date d = new Date(); //current date is taken from system here
        if (cursor.moveToFirst()) {
            do {
                nc = new NotContent();
                //Toast.makeText(this,""+d,Toast.LENGTH_SHORT).show();
                //saving list according to date here i am filtering the live contests and populating arraylist with expired content
                if (sdf.parse(cursor.getString(cursor.getColumnIndex(NotStatus.EXPDATE))).compareTo(d) > 0) {
                    nc.title = cursor.getString(cursor.getColumnIndex(NotStatus.TITLE));
                    nc.alert = cursor.getString(cursor.getColumnIndex(NotStatus.DESC));
                    nc.storeId = cursor.getString(cursor.getColumnIndex(NotStatus.STOREID));
                    nc.img = cursor.getString(cursor.getColumnIndex(NotStatus.IMAGE));
                    nc.uri = cursor.getString(cursor.getColumnIndex(NotStatus.URI));
                    nc.expdate = cursor.getString(cursor.getColumnIndex(NotStatus.EXPDATE));
                    expired.add(nc);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        setupRecyclerView(rv, expired);// different recyclervierws can have different ArrayLists




    }


    @Override
    public void onResult(String result) {
        Content noti = null;
        String reqresult = result.trim();

        try {
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser jsonParser = jsonFactory.createJsonParser(reqresult);
            noti = om.readValue(jsonParser, Content.class);            // txtShowJson.setText(cust.getCatn());
        } catch (Exception e) {
            e.printStackTrace();
        }
        notif = noti.contest;

    }

    @Override
    public void onError(String error) {

    }
}
