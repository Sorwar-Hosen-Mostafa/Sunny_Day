package com.sunny.sunnyday.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sunny.sunnyday.Model.Article;
import com.sunny.sunnyday.Model.History;

import java.util.ArrayList;

/**
 * Created by Alamgir on 04/07/2017.
 */

public class SavedArticleDAO {

    public static final String SAVED_ARTICLE_TABLE = "Saved_Article";
    public static final String ALREADY_READ_ARTICLE_TABLE = "Already_Read_Article";

    public static final String COLUMN_ARTICLE_ID = "article_id";

    public static final String PERIOD_HISTORY_TABLE = "Period_History_table";
    public static final String HISTORY_ID = "History_Id";
    public static final String HISTORY_MONTH = "History_Month";
    public static final String HISTORY_YEAR = "History_Year";
    public static final String HISTORY_START_DATE = "History_Start_Date";
    public static final String HISTORY_END_DATE = "History_End_Date";




    public static final String CREATE_SAVED_ARTICLE_TABLE = "create table "+SAVED_ARTICLE_TABLE +
            "( "+COLUMN_ARTICLE_ID+" text primary key);";

    public static final String CREATE_ALREADY_READ_ARTICLE_TABLE = "create table "+ALREADY_READ_ARTICLE_TABLE +
            "( "+COLUMN_ARTICLE_ID+" text primary key);";

    public static final String CREATE_PERIOD_HISTORY_TABLE = "create table "+ PERIOD_HISTORY_TABLE +
            "( "+HISTORY_ID+" text primary key, "+
            HISTORY_MONTH+" integer, "+
            HISTORY_YEAR+" integer, "+
            HISTORY_START_DATE+" text, "+
            HISTORY_END_DATE +" text);";


    private DatabaseHelper databaseHelper;
    public SQLiteDatabase db ;

    public SavedArticleDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
        open();
    }
    public void open() {
        db = databaseHelper.getWritableDatabase();
    }
    public void close(){
        db.close();
    }
    public long insert_saved_article(Article article){
        this.open();
        ContentValues values = new ContentValues();
        putContentValues(values,article);
        long id = db.insert(SAVED_ARTICLE_TABLE,COLUMN_ARTICLE_ID,values);
        this.close();
        return id;
    }
    public long insert_already_read_article(Article article){
        this.open();
        ContentValues values = new ContentValues();
        putContentValues(values,article);
        long id = db.insert(ALREADY_READ_ARTICLE_TABLE,COLUMN_ARTICLE_ID,values);
        this.close();
        return id;
    }



    private void putContentValues(ContentValues values, Article article) {
        values.put(COLUMN_ARTICLE_ID,article.getId());
    }




    public ArrayList<Article> getSavedArticles(){
        this.open();
        ArrayList<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM "+ SAVED_ARTICLE_TABLE +" ";

        Cursor cursor = db.rawQuery(
                query,
                null
        );

        cursor.moveToFirst();
        if(cursor !=null && cursor.getCount()>0){
            for (int i =0;i<cursor.getCount();i++){
                Article article = new Article();
                article.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_ID)));
                articles.add(article);
                cursor.moveToNext();
            }
        }
        cursor.close();

        this.close();
        return articles;
    }
    public ArrayList<Article> getAlreadySavedArticles(){
        this.open();
        ArrayList<Article> articles = new ArrayList<>();
        String query = "SELECT * FROM "+ ALREADY_READ_ARTICLE_TABLE +" ";

        Cursor cursor = db.rawQuery(
                query,
                null
        );

        cursor.moveToFirst();
        if(cursor !=null && cursor.getCount()>0){
            for (int i =0;i<cursor.getCount();i++){
                Article article = new Article();
                article.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ARTICLE_ID)));
                articles.add(article);
                cursor.moveToNext();
            }
        }
        cursor.close();

        this.close();
        return articles;
    }

    public boolean delete_article_id(String article_id) {
        this.open();
        db.delete(SAVED_ARTICLE_TABLE, COLUMN_ARTICLE_ID + " = ?", new String[]{article_id});
        int deleteId = db.delete(SAVED_ARTICLE_TABLE, COLUMN_ARTICLE_ID + " = ?", new String[]{article_id});
        this.close();
        if (deleteId > 0) {
            return true;
        } else {
            return false;
        }
    }

    public long insert_history(History history){
        this.open();
        ContentValues values = new ContentValues();
        putContentValuesHistory(values,history);
        Log.e("database",values.toString());
        long id = db.insert(PERIOD_HISTORY_TABLE,HISTORY_ID,values);
        this.close();
        return id;
    }

    private void putContentValuesHistory(ContentValues values, History history) {
        values.put(HISTORY_ID,history.getHistoryId());
        values.put(HISTORY_MONTH,history.getHistoryMonth());
        values.put(HISTORY_YEAR,history.getHistoryYear());
        values.put(HISTORY_START_DATE,history.getHistoryStartDate());
        values.put(HISTORY_END_DATE,history.getHistoryEndDate());
    }

    public ArrayList<History> getHistory(){
        this.open();
        ArrayList<History> histories = new ArrayList<>();
        String query = "SELECT * FROM "+ PERIOD_HISTORY_TABLE +" ";

        Cursor cursor = db.rawQuery(
                query,
                null
        );

        cursor.moveToFirst();
        if(cursor !=null && cursor.getCount()>0){
            for (int i =0;i<cursor.getCount();i++){
                History history = new History();
                history.setHistoryId(cursor.getString(cursor.getColumnIndex(HISTORY_ID)));
                history.setHistoryMonth(cursor.getInt(cursor.getColumnIndex(HISTORY_MONTH)));
                history.setHistoryYear(cursor.getInt(cursor.getColumnIndex(HISTORY_YEAR)));
                history.setHistoryStartDate(cursor.getString(cursor.getColumnIndex(HISTORY_START_DATE)));
                history.setHistoryEndDate(cursor.getString(cursor.getColumnIndex(HISTORY_END_DATE)));
                histories.add(history);
                cursor.moveToNext();
            }
        }
        cursor.close();

        this.close();
        return histories;
    }

    public void deleteAllHistory()
    {
        this.open();
        // db.delete(TABLE_NAME,null,null);
        db.execSQL("delete  from "+ PERIOD_HISTORY_TABLE);
        //db.execSQL("TRUNCATE table " + PERIOD_HISTORY_TABLE);
        this.close();
    }

    public boolean updateHistory(History history, String history_id){
        ContentValues values = new ContentValues();
        putContentValuesHistory(values,history);
        this.open();
        int rowId =  db.update(PERIOD_HISTORY_TABLE,values,HISTORY_ID+" = ?",new String[]{history_id});

        this.close();
        if(rowId > 0){
            return true;
        }
        else {
            return false;
        }
    }
}
