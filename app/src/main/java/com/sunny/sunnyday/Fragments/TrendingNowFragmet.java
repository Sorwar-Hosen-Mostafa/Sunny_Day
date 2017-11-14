package com.sunny.sunnyday.Fragments;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sunny.sunnyday.Adapters.ArticleGridRecViewAdapter;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.Model.Article;
import com.sunny.sunnyday.PlayerConfig;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentTrendingNowFragmetBinding;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingNowFragmet extends Fragment {

    private Article article;
    private ArrayList<Article> articles;
    private ArticleGridRecViewAdapter articleGridRecViewAdapter;
    private FragmentTrendingNowFragmetBinding trendingNowFragmetBinding;
    private DatabaseReference dbRef;

    public TrendingNowFragmet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        trendingNowFragmetBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trending_now_fragmet, container, false);
        View view = trendingNowFragmetBinding.getRoot();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity.fromsavedarticle = false;

        trendingNowFragmetBinding.tvcImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=z4gNrdacbws"));
                startActivity(browserIntent);
            }
        });

        if(isNetworkAvailable()){
          getdata();
            Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FIRST_TIME_RUNS_APP,Utils.STATUS_FALSE);
        }
        else {

            if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FIRST_TIME_RUNS_APP).equals("null")){
                Snackbar.make(trendingNowFragmetBinding.parentlayout,"No Internet Connection",Snackbar.LENGTH_INDEFINITE)
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


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getdata(){

        dbRef = FirebaseDatabase.getInstance().getReference();

        Query query = dbRef.child(Utils.FIRE_ARTICLE).orderByChild(Utils.FIRE_ARTICLE_CATEGORY).equalTo(Utils.FIRE_ARTICLE_CATEGORY_TRENDING_NOW);
        query.keepSynced(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                articles = new ArrayList<Article>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    article = snapshot.getValue(Article.class);
                    articles.add(article);
                }

                if(getActivity()!=null) {
                    articleGridRecViewAdapter = new ArticleGridRecViewAdapter(articles, getActivity());
                    trendingNowFragmetBinding.trendingNowRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    trendingNowFragmetBinding.trendingNowRecyclerView.setAdapter(articleGridRecViewAdapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

}
