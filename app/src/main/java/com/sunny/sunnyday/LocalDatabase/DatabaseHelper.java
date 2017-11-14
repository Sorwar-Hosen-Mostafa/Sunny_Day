package com.sunny.sunnyday.LocalDatabase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alamgir on 04/07/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "sunnydaydatabase";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SavedArticleDAO.CREATE_SAVED_ARTICLE_TABLE);
            db.execSQL(SavedArticleDAO.CREATE_ALREADY_READ_ARTICLE_TABLE);
            db.execSQL(SavedArticleDAO.CREATE_PERIOD_HISTORY_TABLE);
        }
        catch (SQLException e){
            Log.e("SQL ERROR",e.getMessage().toString());
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ SavedArticleDAO.SAVED_ARTICLE_TABLE);
        db.execSQL("drop table if exists "+ SavedArticleDAO.ALREADY_READ_ARTICLE_TABLE);
        db.execSQL("drop table if exists "+ SavedArticleDAO.PERIOD_HISTORY_TABLE);
        onCreate(db);
    }
}
