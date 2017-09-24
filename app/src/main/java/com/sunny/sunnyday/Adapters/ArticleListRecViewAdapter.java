package com.sunny.sunnyday.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

public class ArticleListRecViewAdapter extends RecyclerView.Adapter<ArticleListRecViewAdapter.DerpHolder> {


    List<Article> articles ;
    ArrayList<Article > already_saved_articles;
    LayoutInflater inflater;
    ClickedListener clickedListener;
    String device_id;
    ArticleListRecViewAdapter articleListRecViewAdapter;
    String from;
    Context context;
    ArrayList<Article> saved_articles_list;
    SavedArticleDAO savedArticleDAO;
    MainActivity mainActivity;;
    DatabaseReference databaseReferencetwo;
    public ArticleListRecViewAdapter(List<Article> articles, Context context, String from){

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.articles = articles;
        mainActivity = (MainActivity) context;
        this.from = from;

    }
    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    @Override
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.listcontentcardview,parent,false);

        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(final DerpHolder holder, final int position) {

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

                boolean status = false;
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
                            savedArticleDAO.insert_saved_article(article);
                            Toast.makeText(context,"article saved",Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else {

                }
                //Toast.makeText(context,"article saved",Toast.LENGTH_SHORT).show();
//                device_id = Utils.getFromPrefs(context,Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_DEVICE_ID,Utils.DEFAULT_VALUE);
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
//                    Toast.makeText(context,"You have already saved this article",Toast.LENGTH_LONG).show();
//                }
//                else {
//                    saved_articles_final.add(article.getId());
//                    android.util.Log.e("final",saved_articles_final.toString());
//                    databaseReferencetwo.setValue(saved_articles_final);
//                }
            }
        });

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(from.equals("HOTD")){
                    return false;
                }
                else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Remove Article");
                    alert.setMessage("Are you sure you want to remove");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            savedArticleDAO = new SavedArticleDAO(context);
                            savedArticleDAO.delete_article_id(article.getId());
                            Toast.makeText(context,"article removed",Toast.LENGTH_SHORT).show();
                            //Snackbar.make(,"Data Reset Successfully",Snackbar.LENGTH_SHORT).show();
                            articles = savedArticleDAO.getSavedArticles();
                            notifyItemRemoved(position);
                        }
                    });
                    alert.setNegativeButton("No",null);
                    alert.show();

                    return true;
                }

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
        TextView newtag;
        View view;

        public DerpHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.articleTitle_TV);
            picture = (ImageView) itemView.findViewById(R.id.articleImage_IV);
            saveArticleButton = (ImageView) itemView.findViewById(R.id.SaveArticle_Btn);
            postdate = (TextView) itemView.findViewById(R.id.articlePostDate_TV);
            readtime = (TextView) itemView.findViewById(R.id.articleReadTime_TV);
            newtag = (TextView) itemView.findViewById(R.id.new_tag);
            view = itemView.findViewById(R.id.horizontalCardView);
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
