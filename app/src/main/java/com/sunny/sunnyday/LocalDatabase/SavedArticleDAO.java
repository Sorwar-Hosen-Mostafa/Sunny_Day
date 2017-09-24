package com.sunny.sunnyday.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sunny.sunnyday.Model.Article;

import java.util.ArrayList;

/**
 * Created by Alamgir on 04/07/2017.
 */

public class SavedArticleDAO {

    public static final String SAVED_ARTICLE_TABLE = "Saved_Article";
    public static final String ALREADY_READ_ARTICLE_TABLE = "Already_Read_Article";
    public static final String COLUMN_ARTICLE_ID = "article_id";



    public static final String CREATE_SAVED_ARTICLE_TABLE = "create table "+SAVED_ARTICLE_TABLE +
            "( "+COLUMN_ARTICLE_ID+" text primary key);";

    public static final String CREATE_ALREADY_READ_ARTICLE_TABLE = "create table "+ALREADY_READ_ARTICLE_TABLE +
            "( "+COLUMN_ARTICLE_ID+" text primary key);";


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
        ArrayList<Article > articles = new ArrayList<>();
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
        ArrayList<Article > articles = new ArrayList<>();
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
}
