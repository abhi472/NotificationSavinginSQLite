package com.example.abhishek.notificationsavinginsqlite.DatabaseFiles;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;


/**
 * Created by abhishek on 29/3/16.
 */
public class NotProvider extends ContentProvider {

    private SQLiteDatabase database;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(NotStatus.AUTHORITY,NotStatus.Table_Name,NotStatus.CONTENT_TYPE_DIR);
        uriMatcher.addURI(NotStatus.AUTHORITY,NotStatus.Table_Name+"/#",NotStatus.CONTENT_TYPE_ITEM);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        NotStatus notStatus = new NotStatus(context);
        database = notStatus.getWritableDatabase();
        return (database != null);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(NotStatus.Table_Name);

        switch (uriMatcher.match(uri)) {
            case NotStatus.CONTENT_TYPE_DIR:
                break;

            case NotStatus.CONTENT_TYPE_ITEM:
                qb.appendWhere(NotStatus.COLUMN_ID+ " = "+ uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: " + uri);
        }
        if (sortOrder == null){
            /**
             * By default sort on student names
             */
            sortOrder = NotStatus.TITLE;
        }


        //return database.rawQuery("select * from "+NotStatus.Table_Name,null);
         return qb.query(database,projection,selection,selectionArgs,null,null,null);

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case NotStatus.CONTENT_TYPE_DIR:
                return NotStatus.CONTENT_TYPE;

            case NotStatus.CONTENT_TYPE_ITEM:
                return NotStatus.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != NotStatus.CONTENT_TYPE_DIR) {
            throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        long id = database.insert(NotStatus.Table_Name,null,values);

        if (id>0) {
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException("Error inserting into table: "+NotStatus.Table_Name);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted = 0;

        switch (uriMatcher.match(uri)) {
            case NotStatus.CONTENT_TYPE_DIR:
                database.delete(NotStatus.Table_Name,selection,selectionArgs);
                break;

            case NotStatus.CONTENT_TYPE_ITEM:
                String where = NotStatus.COLUMN_ID + " = " + uri.getLastPathSegment();
                if (!selection.isEmpty()) {
                    where += " AND "+selection;
                }

                deleted = database.delete(NotStatus.Table_Name,where,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        return deleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated = 0;

        switch (uriMatcher.match(uri)) {
            case NotStatus.CONTENT_TYPE_DIR:
                database.update(NotStatus.Table_Name, values, selection, selectionArgs);
                break;

            case NotStatus.CONTENT_TYPE_ITEM:
                String where = NotStatus.COLUMN_ID + " = " + uri.getLastPathSegment();
                if (!selection.isEmpty()) {
                    where += " AND "+selection;
                }
                updated = database.update(NotStatus.Table_Name,values,where,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        return updated;
    }
}
