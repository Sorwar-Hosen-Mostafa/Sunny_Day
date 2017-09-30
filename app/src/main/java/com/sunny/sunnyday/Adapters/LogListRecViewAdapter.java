package com.sunny.sunnyday.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sunny.sunnyday.Model.Log;
import com.sunny.sunnyday.R;

import java.util.List;


/**
 * Created by Jibunnisa on 12/4/2016.
 */

public class LogListRecViewAdapter extends RecyclerView.Adapter<LogListRecViewAdapter.DerpHolder> {

    List<Log> logs ;
    LayoutInflater inflater;
    ClickedListener clickedListener;
    Context context;
    public LogListRecViewAdapter(List<Log> logs, Context context){

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.logs = logs;

    }
    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    @Override
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.loglistviewitems,parent,false);

        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {

        final Log log = logs.get(position);

        holder.title.setText(log.getLog_title());
        holder.suggesstion.setText(log.getLog_suggesstion());
        holder.date.setText(log.getLog_date());
        holder.day.setText(log.getLog_day());
        holder.titlesymble.setText(log.getLog_textsymble());

//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(context,EventDescriptionActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.putExtra("title",item.getTitle());
//                i.putExtra("description",item.getDescription());
//                i.putExtra("image",item.getPicture());
//                i.putExtra("deadline","Dead Line: "+item.getDeadline().toString());
//                context.startActivity(i);
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView suggesstion;
        TextView date;
        TextView day;
        TextView titlesymble;
        View view;

        public DerpHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.logTitle_TV);
            suggesstion = (TextView) itemView.findViewById(R.id.logSuggession_TV);
            date = (TextView) itemView.findViewById(R.id.logDate_TV);
            day = (TextView) itemView.findViewById(R.id.logDay_TV);
            titlesymble = (TextView) itemView.findViewById(R.id.logTitleSymble_TV);
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
