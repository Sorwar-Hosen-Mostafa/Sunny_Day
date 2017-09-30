package com.sunny.sunnyday.Fragments;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sunny.sunnyday.Adapters.ArticleGridRecViewAdapter;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.Model.Article;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentHealthAndFitnessBinding;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthAndFitnessFragment extends Fragment {


    private FragmentHealthAndFitnessBinding healthAndFitnessBinding;
    private Article article;
    static SpotsDialog progressDialog;
    private ArrayList<Article> articles;
    private ArticleGridRecViewAdapter articleRecViewAdapter;
    private DatabaseReference dbRef;

    public HealthAndFitnessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        healthAndFitnessBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_health_and_fitness, container, false);
        View view = healthAndFitnessBinding.getRoot();


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity.fromsavedarticle = false;

        progressDialog = new SpotsDialog(getActivity(), R.style.Custom_Progress_Bar);
        progressDialog.setMessage("please wait..");
        progressDialog.show();


        if (isNetworkAvailable()) {
            getdata();
            Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.FIRST_TIME_RUNS_APP, Utils.STATUS_FALSE);
        } else {
            progressDialog.dismiss();
            if (Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.FIRST_TIME_RUNS_APP).equals("null")) {
                Snackbar.make(healthAndFitnessBinding.parentlayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onActivityCreated(savedInstanceState);
                            }
                        }).show();
            } else {
                getdata();
            }

            getdata();
        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void getdata() {

        dbRef = FirebaseDatabase.getInstance().getReference();
        Query query = dbRef.child(Utils.FIRE_ARTICLE).orderByChild(Utils.FIRE_ARTICLE_CATEGORY).equalTo(Utils.FIRE_ARTICLE_CATEGORY_HEATHANDFITNESS);
        query.keepSynced(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                articles = new ArrayList<Article>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    article = snapshot.getValue(Article.class);
                    articles.add(article);
                }
                if (getActivity() != null) {
                    articleRecViewAdapter = new ArticleGridRecViewAdapter(articles, getActivity());
                    healthAndFitnessBinding.healthAndFitnessRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    healthAndFitnessBinding.healthAndFitnessRecyclerView.setAdapter(articleRecViewAdapter);
                    if (articleRecViewAdapter.getItemCount() > 0) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }


}
