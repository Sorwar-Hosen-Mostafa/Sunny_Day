package com.sunny.sunnyday.Fragments;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sunny.sunnyday.Adapters.ArticleListRecViewAdapter;
import com.sunny.sunnyday.LocalDatabase.SavedArticleDAO;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.Model.Article;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentSavedArticlesBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedArticlesFragment extends Fragment {


    ArrayList<String> saved_articles_list;
    private Article article;
    String device_id;
    ArrayList<Article> finalarticles;
    ArrayList<String> arrayList;
    SavedArticleDAO savedArticleDao;
    MainActivity mainActivity;
    private ArrayList<Article> articles;
    private ArticleListRecViewAdapter articleListRecViewAdapter;
    private DatabaseReference dbRef;
    private FragmentSavedArticlesBinding savedArticlesBinding;

    public SavedArticlesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        savedArticlesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_articles, container, false);
        View view = savedArticlesBinding.getRoot();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity) getActivity();

        savedArticleDao= new SavedArticleDAO(getActivity());
        // mainActivity.saveArticle();
        //arrayList=mainActivity.getSaved_articles_final();

//        device_id = Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_DEVICE_ID);
//        dbRef = FirebaseDatabase.getInstance().getReference();
//        final Query query2 = dbRef.child(device_id).child(Utils.USER_SAVED_ARTICLE);
//
//        final Query query = dbRef.child(Utils.FIRE_ARTICLE);
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                articles = new ArrayList<Article>();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                    articles.add(snapshot.getValue(Article.class));
//
//                }
//
//                if (articles.size() > 0) {
//
//                    ArrayList<Article> saved_articles_list = savedArticleDao.getSavedArticles();
//
//                    for (int i = 0; i < articles.size(); i++) {
//                        for (int j = 0; j < saved_articles_list.size(); j++) {
//                            if (saved_articles_list.get(j).getId().equals(articles.get(i).getId())) {
//                                finalarticles.add(articles.get(j));
//                            } else {
//                                Toast.makeText(mainActivity, "No articles saved yet", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    if (getActivity() != null   ) {
//                        articleListRecViewAdapter = new ArticleListRecViewAdapter(finalarticles, getActivity());
//                        savedArticlesBinding.SavedArticleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                        savedArticlesBinding.SavedArticleRecyclerView.setAdapter(articleListRecViewAdapter);
//                    } else {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//
//        });

        if(isNetworkAvailable()){
            getdata();
            Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FIRST_TIME_RUNS_APP,Utils.STATUS_FALSE);

        }
        else {
            if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FIRST_TIME_RUNS_APP).equals("null")){
                Snackbar.make(savedArticlesBinding.parentlayout,"No Internet Connection",Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onActivityCreated(savedInstanceState);
                            }
                        }).show();
            }
            else {
                getdata();
            }
        }





    }

    private void getdata(){
        dbRef = FirebaseDatabase.getInstance().getReference();
        device_id = Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_DEVICE_ID);

        final Query query = dbRef.child(Utils.FIRE_ARTICLE);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                articles = new ArrayList<Article>();
                finalarticles = new ArrayList<Article>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    article = snapshot.getValue(Article.class);
                    articles.add(article);
                }

                ArrayList<Article> saved_articles_list = savedArticleDao.getSavedArticles();

                if(saved_articles_list.size()>0){
                    for (int i = 0; i< saved_articles_list.size(); i++){
                        for(int j = 0;j<articles.size();j++){
                            if(articles.get(j).getId().equals(saved_articles_list.get(i).getId())){
                                finalarticles.add(articles.get(j));
                            }
                            else {

                            }
                        }

                    }

                    if(getActivity()!=null){
                        articleListRecViewAdapter = new ArticleListRecViewAdapter(finalarticles,getActivity(),"SAF");
                        savedArticlesBinding.SavedArticleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        savedArticlesBinding.SavedArticleRecyclerView.setAdapter(articleListRecViewAdapter);

                    }
                    else {

                    }

                }
                else {
                    Toast.makeText(getActivity(), "no article saved", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
