package com.sunny.sunnyday.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.sunny.sunnyday.LocalDatabase.SavedArticleDAO;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.Model.Article;
import com.sunny.sunnyday.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jibunnisa on 12/4/2016.
 */

public class ArticleGridRecViewAdapter extends RecyclerView.Adapter<ArticleGridRecViewAdapter.DerpHolder> {

    List<Article> articles ;
    ArrayList<Article > already_saved_articles;
    ArrayList<String > saved_articles_final;
    LayoutInflater inflater;
    ClickedListener clickedListener;
    String device_id;
    Context context;

    MainActivity mainActivity;;
    DatabaseReference databaseReference;
    SavedArticleDAO savedArticleDAO;
    DatabaseReference databaseReferencetwo;
    ArrayList<Article> saved_articles_list;
    public ArticleGridRecViewAdapter(List<Article> articles, Context context){



        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.articles = articles;
        mainActivity = (MainActivity) context;


    }
    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    @Override
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.gridcontentcardview,parent,false);

        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(final DerpHolder holder, int position) {

        final Article article = articles.get(position);
        holder.description.setText(article.getArticle_Description());


        Picasso.with(context).load(article.getArticle_Image()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.picture, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context)
                        .load(article.getArticle_Image())
                        .into(holder.picture);
            }
        });

        holder.postdate.setText(article.getArticle_PostDate());
        holder.readtime.setText(article.getArticle_Readtime());


        already_saved_articles = new ArrayList<>();
        boolean already_read_status = false;
        savedArticleDAO = new SavedArticleDAO(context);
        already_saved_articles = savedArticleDAO.getAlreadySavedArticles();
        if(context!=null){

            if(already_saved_articles.size()==0){

            }
            else {
                for(Article articletemp : already_saved_articles){
                    if(article.getId().equals(articletemp.getId())){
                        already_read_status = true;
                        break;
                    }
                    else {
                        already_read_status= false;
                    }
                }
                if(already_read_status){
                    holder.newtag.setVisibility(View.INVISIBLE);
                }
                else {

                }
            }

        }
        else {

        }


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.articleview();
                mainActivity.setArticleViewData(article);
                savedArticleDAO = new SavedArticleDAO(context);
                savedArticleDAO.insert_already_read_article(article);
            }
        });

        holder.saveArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean saved_status = false;
                savedArticleDAO = new SavedArticleDAO(context);
                saved_articles_list = savedArticleDAO.getSavedArticles();
                if(context!=null){

                    if(saved_articles_list.size()==0){
                        savedArticleDAO.insert_saved_article(article);
                        Toast.makeText(context,"article saved",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for(Article articletemp : saved_articles_list){
                            if(article.getId().equals(articletemp.getId())){
                                saved_status = true;
                                break;
                            }
                            else {
                                saved_status= false;
                            }
                        }
                        if(saved_status){
                            Toast.makeText(context,"You have already saved this article",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            savedArticleDAO.insert_saved_article(article);
                            Toast.makeText(context,"article saved",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else {

                }






//                device_id = Utils.getFromPrefs(context,Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_DEVICE_ID);
//                databaseReferencetwo = FirebaseDatabase.getInstance().getReference(device_id).child(Utils.USER_SAVED_ARTICLE);
//
//                mainActivity.saveArticle();
//
//                saved_articles_final=mainActivity.getSaved_articles_final();
//                boolean status = false;
//                for(int i=0;i<saved_articles_final.size();i++){
//                    if(saved_articles_final.get(i).equals(article.getId())){
//                        status= true;
//                        break;
//                    }
//                    else {
//                        status = false;
//                        continue;
//                    }
//                }
//                if (status==true){
//                    Toast.makeText(context,"You have already saved this article",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    saved_articles_final.add(article.getId());
//                    android.util.Log.e("final",saved_articles_final.toString());
//                    databaseReferencetwo.setValue(saved_articles_final);
//                    Toast.makeText(context,"article saved",Toast.LENGTH_SHORT).show();
//                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView description;
        TextView postdate;
        TextView readtime;
        ImageView picture;
        ImageView saveArticleButton;
        View view;
        TextView newtag;

        public DerpHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.articleDescrip_TV);
            picture = (ImageView) itemView.findViewById(R.id.articleImage_IV);
            postdate = (TextView) itemView.findViewById(R.id.articlePostDate_TV);
            readtime = (TextView) itemView.findViewById(R.id.articleReadTime_TV);
            saveArticleButton = (ImageView) itemView.findViewById(R.id.SaveArticle_Btn);
            newtag = (TextView) itemView.findViewById(R.id.new_tag);
            view = itemView.findViewById(R.id.VerticalGridCardView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if(clickedListener!=null){
                clickedListener.itemclick(v,getPosition());
            }
        }
    }
    public interface ClickedListener{
        public void itemclick(View view, int position);
    }


}
