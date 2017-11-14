package com.sunny.sunnyday.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunny.sunnyday.Model.History;
import com.sunny.sunnyday.R;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Jibunnisa on 12/4/2016.
 */

public class HistoryRecViewAdapter extends RecyclerView.Adapter<HistoryRecViewAdapter.DerpHolder> {

    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MM");
    List<History> histories ;
    LayoutInflater inflater;
    ClickedListener clickedListener;
    Context context;
    public HistoryRecViewAdapter(List<History> histories, Context context){

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.histories = histories;

    }
    public void setClickedListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }

    @Override
    public DerpHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.history_layout,parent,false);

        return new DerpHolder(view);
    }

    @Override
    public void onBindViewHolder(DerpHolder holder, int position) {

        final History history = histories.get(position);

        int month = history.getHistoryMonth();
        String month_long = getmonthfromnumber(month);
        holder.textViewMonth.setText(month_long);
        holder.textViewStartDate.setText(history.getHistoryStartDate());
        holder.textViewEndDate.setText(history.getHistoryEndDate());


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

    private String getmonthfromnumber(int month) {
        String month_long;
        if(month==1){
            month_long="JAN";
        }
        else if(month==2){
            month_long="FAB";
        }
        else if(month==3){
            month_long="MAR";
        }
        else if(month==4){
            month_long="APR";
        }
        else if(month==5){
            month_long="MAY";
        }
        else if(month==6){
            month_long="JUN";
        }
        else if(month==7){
            month_long="JUL";
        }
        else if(month==8){
            month_long="AUG";
        }
        else if(month==9){
            month_long="SEP";

        }
        else if(month==10){
            month_long="OCT";

        }
        else if(month==11){
            month_long="NOV";
        }
        else{
            month_long="DEC";
        }

        return month_long;
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    class DerpHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewMonth;
        TextView textViewStartDate;
        TextView textViewEndDate;
        View view;

        public DerpHolder(View itemView) {
            super(itemView);
            textViewMonth = (TextView) itemView.findViewById(R.id.month);
            textViewStartDate = (TextView) itemView.findViewById(R.id.startDate);
            textViewEndDate = (TextView) itemView.findViewById(R.id.endDate);
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
