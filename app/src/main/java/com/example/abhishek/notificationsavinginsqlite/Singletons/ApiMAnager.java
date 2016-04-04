package com.example.abhishek.notificationsavinginsqlite.Singletons;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.abhishek.notificationsavinginsqlite.Callbacks.ICallBack;

/**
 * Created by abhishek on 1/4/16.
 */
public class ApiMAnager {


    ICallBack iCallBack;
    android.app.Fragment context;

    private static ApiMAnager instance;

    public static ApiMAnager getInstance() {
        if (instance == null) {
            instance = new ApiMAnager();
        }
        return instance;
    }

    public static ApiMAnager getInstance(android.app.Fragment context) {
        //if (instance == null) {
        instance = new ApiMAnager(context);
        //}
        return instance;
    }

    ApiMAnager() {

    }

    ApiMAnager(android.app.Fragment c) {
        this.context = c;
    }

    /**
     * Call from Fragment only
     *
     * @param requestUrl
     */
    public void sendReq(String requestUrl) {
        iCallBack = (ICallBack) context;
        StringRequest stringRequest = new StringRequest(requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                iCallBack.onResult(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iCallBack.onResult(error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest, "stringReq");
    }

    /**
     * Call from Activity only
     *
     * @param context
     * @param requestUrl
     */
    public void sendReq(Context context, String requestUrl) {
        iCallBack = (ICallBack) context;

        StringRequest stringRequest = new StringRequest(requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                iCallBack.onResult(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                iCallBack.onResult(error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
