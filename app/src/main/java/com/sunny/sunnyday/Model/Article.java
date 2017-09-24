package com.sunny.sunnyday.Model;

import com.sunny.sunnyday.R;

import java.util.ArrayList;

/**
 * Created by Jibunnisa on 8/26/2017.
 */

public class Article {
    private String id;
    private String article_title;
    private String article_author;
    private String article_Image;
    private String  article_Description;
    private String  article_PostDate;
    private String  article_Readtime;
    private String article_Category;

    public Article(String id, String article_title, String article_author, String article_Image, String article_Description, String article_PostDate, String article_Readtime, String article_Category) {
        this.id = id;
        this.article_title = article_title;
        this.article_author = article_author;
        this.article_Image = article_Image;
        this.article_Description = article_Description;
        this.article_PostDate = article_PostDate;
        this.article_Readtime = article_Readtime;
        this.article_Category = article_Category;
    }

    public Article() {
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_author() {
        return article_author;
    }

    public void setArticle_author(String article_author) {
        this.article_author = article_author;
    }

    public String getArticle_Category() {
        return article_Category;
    }

    public void setArticle_Category(String article_Category) {
        this.article_Category = article_Category;
    }

    public String getArticle_Image() {
        return article_Image;
    }

    public String getArticle_Description() {
        return article_Description;
    }

    public String getArticle_PostDate() {
        return article_PostDate;
    }

    public String getArticle_Readtime() {
        return article_Readtime;
    }

    public void setArticle_Image(String article_Image) {
        this.article_Image = article_Image;
    }

    public void setArticle_Description(String article_Description) {
        this.article_Description = article_Description;
    }

    public void setArticle_PostDate(String article_PostDate) {
        this.article_PostDate = article_PostDate;
    }

    public void setArticle_Readtime(String article_Readtime) {
        this.article_Readtime = article_Readtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
