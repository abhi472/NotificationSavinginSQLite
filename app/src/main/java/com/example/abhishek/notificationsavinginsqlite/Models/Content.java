package com.example.abhishek.notificationsavinginsqlite.Models;

import com.example.abhishek.notificationsavinginsqlite.Models.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by abhishek on 1/4/16.
 */
public class Content implements Serializable {

    public String api_key;
    public String message;
    public String errorMsg;
    public ArrayList<NotContent> contest ;

}
