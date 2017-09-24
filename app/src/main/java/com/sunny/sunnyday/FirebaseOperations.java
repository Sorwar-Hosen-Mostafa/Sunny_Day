package com.sunny.sunnyday;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sunny.sunnyday.Model.Article;
import com.sunny.sunnyday.Model.Person;

import java.util.ArrayList;

/**
 * Created by Jibunnisa on 9/4/2017.
 */

public class FirebaseOperations {


    private static DatabaseReference dbRef;
    private Article article;
    private ArrayList<Article> articles;
    private Context context;


    public void getArticles() {


        dbRef = FirebaseDatabase.getInstance().getReference(Utils.FIRE_ARTICLE);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                articles = new ArrayList<Article>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    article = snapshot.getValue(Article.class);
                    articles.add(article);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

}



