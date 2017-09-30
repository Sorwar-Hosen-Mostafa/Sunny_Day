package com.sunny.sunnyday.Fragments;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sunny.sunnyday.DataCollectionFragments.FragmentFive;
import com.sunny.sunnyday.LocalDatabase.SavedArticleDAO;
import com.sunny.sunnyday.MainActivity;

import com.sunny.sunnyday.Model.Article;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.databinding.FragmentArticleReadViewBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleReadViewFragment extends Fragment {

    ArrayList<Article> saved_articles_list;
    SavedArticleDAO savedArticle;
    Article article;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private FragmentArticleReadViewBinding articleReadViewBinding;
    public ArticleReadViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        articleReadViewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_read_view, container, false);
        View view = articleReadViewBinding.getRoot();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"HelveticaLTStd-BlkCond.ttf");
        Typeface typeface2 = Typeface.createFromAsset(getActivity().getAssets(),"HelveticaLTStd-Light.ttf");
        final MainActivity mainActivity = (MainActivity) getActivity();
        article = mainActivity.getArticleViewData();
        articleReadViewBinding.articleDescriptionTV.setText(article.getArticle_Description());
        Picasso.with(getContext()).load(article.getArticle_Image()).into(articleReadViewBinding.articleImageIV);
        articleReadViewBinding.postDateAndReadTimeTV.setText(article.getArticle_PostDate()+" ,"+article.getArticle_Readtime());
        articleReadViewBinding.authorNameTV.setText(article.getArticle_author());
        articleReadViewBinding.articleTitleTV.setText(article.getArticle_title());
        final String cat = article.getArticle_Category();

        articleReadViewBinding.postDateAndReadTimeTV.setTypeface(typeface);
        articleReadViewBinding.authorNameTV.setTypeface(typeface);
        articleReadViewBinding.articleTitleTV.setTypeface(typeface);
        articleReadViewBinding.saveArticleBtn.setTypeface(typeface);


        articleReadViewBinding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainActivity = (MainActivity) getActivity();


                if(mainActivity.fromsavedarticle==true){
                    SavedArticlesFragment savedArticlesFragment = new SavedArticlesFragment();
                    fragmentManager = mainActivity.fragmentManager;
                    fragmentTransaction = mainActivity.fragmentTransaction;
                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.child_fragment_container, savedArticlesFragment, "saf");
                    fragmentTransaction.commit();
                }
                else {
                    if(cat.equals("health and hygiene")){
                        HealthAndFitnessFragment healthAndFitnessFragment= new HealthAndFitnessFragment();

                        fragmentManager = mainActivity.fragmentManager;
                        fragmentTransaction = mainActivity.fragmentTransaction;
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.child_fragment_container, healthAndFitnessFragment, "haff");
                        fragmentTransaction.commit();
                    }
                    else if(cat.equals("humor of the day")){
                        HumorOfTheDayFragment humorOfTheDayFragment= new HumorOfTheDayFragment();

                        fragmentManager = mainActivity.fragmentManager;
                        fragmentTransaction = mainActivity.fragmentTransaction;
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.child_fragment_container, humorOfTheDayFragment, "hotd");
                        fragmentTransaction.commit();
                    }
                    else if(cat.equals("trending now")){
                        TrendingNowFragmet trendingNowFragmet= new TrendingNowFragmet();

                        fragmentManager = mainActivity.fragmentManager;
                        fragmentTransaction = mainActivity.fragmentTransaction;
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.child_fragment_container, trendingNowFragmet, "tnf");
                        fragmentTransaction.commit();
                    }
                    else if(cat.equals("whats in whats out")){
                        WhatsInWhatsOutFragment whatsInWhatsOutFragment= new WhatsInWhatsOutFragment();

                        fragmentManager = mainActivity.fragmentManager;
                        fragmentTransaction = mainActivity.fragmentTransaction;
                        fragmentManager = getFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.child_fragment_container, whatsInWhatsOutFragment, "wiwof");
                        fragmentTransaction.commit();
                    }
                }



            }
        });
        articleReadViewBinding.saveArticleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = false;
                Context context = getActivity();
                savedArticle = new SavedArticleDAO(context);
                saved_articles_list = savedArticle.getSavedArticles();
                if(context!=null){

                    if(saved_articles_list.size()==0){
                        savedArticle.insert_saved_article(article);
                        Toast.makeText(context,"article saved",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for(Article articletemp : saved_articles_list){
                            if(article.getId().equals(articletemp.getId())){
                                status = true;
                                break;
                            }
                            else {
                                status= false;
                            }
                        }
                        if(status){
                            Toast.makeText(context,"You have already saved this article",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            savedArticle.insert_saved_article(article);
                            Toast.makeText(context,"article saved",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else {

                }
            }
        });

    }

}
