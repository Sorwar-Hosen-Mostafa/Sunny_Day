package com.sunny.sunnyday.Fragments;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.sunny.sunnyday.Adapters.LogListRecViewAdapter;
import com.sunny.sunnyday.AlarmReceiver;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.MyJobService;
import com.sunny.sunnyday.Model.Log;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentPeriodCalenderBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeriodCalenderFragment extends Fragment {

    private int JOB_ID = 0 ;
    private Log log;
    private ArrayList<Log> logs;
    public JobScheduler jobScheduler;
    boolean prev = false;
    private SimpleDateFormat dateFormatMonth;
    private FragmentPeriodCalenderBinding periodCalenderBinding;
    private Calendar calendar;
    SimpleDateFormat formatter = new SimpleDateFormat("EEE");
    SimpleDateFormat dayformatter = new SimpleDateFormat("dd");
    SimpleDateFormat monthformatter = new SimpleDateFormat("MM");
    SimpleDateFormat yearformatter = new SimpleDateFormat("yyyy");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
    private Calendar calendertemp;
    ArrayList<Integer> months;
    LogListRecViewAdapter logListRecViewAdapter;
    boolean fisttime = true;
    Calendar calendarcurrent = Calendar.getInstance(Locale.getDefault());
    Date datecurrent ;
    boolean log_function;
    private Date calendarDateClicked;
    private long milliTimearray[];
    private String logTitles[], logTitleSymbols[], logSuggessions[], logTitlesPregnant[], logTitleSymbolsPregnant[], logSuggessionsPregnant[];
    int mYear, mMonth, mDay, periodduration, periodcyclelength, lastperiodmonth, lastperiodday;
    Date lastdate;
    Date ddd;
    ArrayList<Log> logstemp;
    Intent notificationIntent;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    String pragnancystatus;

    public PeriodCalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        periodCalenderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_period_calender, container, false);
        View view = periodCalenderBinding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        months = new ArrayList<>();
        calendarcurrent.set(Calendar.HOUR,0);
        calendarcurrent.set(Calendar.MINUTE,0);
        calendarcurrent.set(Calendar.SECOND,0);
        calendarcurrent.set(Calendar.MILLISECOND,0);
        calendarcurrent.set(Calendar.DAY_OF_MONTH,1);
        datecurrent = new Date(calendarcurrent.getTimeInMillis());
        datecurrent.setHours(0);
        android.util.Log.e("current date",datecurrent.toString());

        logs = new ArrayList<>();
        ddd = new Date();


        // periodCalenderBinding.



        dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

        logTitles = getResources().getStringArray(R.array.logs_title);
        logTitleSymbols = getResources().getStringArray(R.array.logs_title_symbols);
        logSuggessions = getResources().getStringArray(R.array.logs_suggessions);
        logTitlesPregnant = getResources().getStringArray(R.array.logs_title_pregnant);
        logTitleSymbolsPregnant = getResources().getStringArray(R.array.logs_title_symbols_pregnant);
        logSuggessionsPregnant = getResources().getStringArray(R.array.logs_suggessions_pregnant);


        periodCalenderBinding.compactcalendarView.setUseThreeLetterAbbreviation(true);
        periodCalenderBinding.compactcalendarView.setFirstDayOfWeek(7);



        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        periodCalenderBinding.monthTV.setText(dateFormatMonth.format(calendar.getTime()));


        //data retrive from shared prefrence
        lastperiodday = Integer.parseInt(Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_DATE));
        lastperiodmonth = Integer.parseInt(Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_MONTH));
        periodcyclelength = Integer.parseInt(Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_CYCLE_LENGTH));
        periodduration = Integer.parseInt(Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_PERIOD_DURATION));
        pragnancystatus = Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_WANT_TO_PREGNANT_STATUS);


        periodCalenderBinding.buttonNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                periodCalenderBinding.compactcalendarView.showNextMonth();
            }
        });
        periodCalenderBinding.buttonPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                periodCalenderBinding.compactcalendarView.showPreviousMonth();
            }
        });

        periodCalenderBinding.logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(getActivity(),R.style.DialogTheme, dateSet, mYear, lastperiodmonth, lastperiodday);
                dateDialog.show();
            }
        });

      //  clearAllNotificationAlarts();
//        if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION).equals(Utils.STATUS_TRUE)){
//            lastperiodday = lastperiodday+1;
//        }
        if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_FUNCTION).equals(Utils.STATUS_TRUE)){
            Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_DATE,String .valueOf(lastperiodday+1));
            lastperiodday = Integer.parseInt(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_DATE));
            seteventsoncalendar(lastperiodday, lastperiodmonth-1,periodcyclelength-1, periodduration, mYear);
            Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_FUNCTION,Utils.STATUS_FALSE);
        }
        else {
            seteventsoncalendar(lastperiodday, lastperiodmonth-1,periodcyclelength-1, periodduration, mYear);
        }





        periodCalenderBinding.compactcalendarView.shouldScrollMonth(false);
        periodCalenderBinding.compactcalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                calendarDateClicked = dateClicked;
                android.util.Log.e("error from listener:", String.valueOf(calendarDateClicked));
                if (periodCalenderBinding.compactcalendarView.getEvents(dateClicked).size() > 0) {
                    Event event = periodCalenderBinding.compactcalendarView.getEvents(dateClicked).get(0);
                  //  Toast.makeText(getActivity(), "" + event.getData().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                if(datecurrent.getTime()<firstDayOfNewMonth.getTime() || datecurrent.getTime()==firstDayOfNewMonth.getTime()){

                    if(prev){

                    }
                    else {

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(lastdate);
                        cal.set(Calendar.MILLISECOND,0);
                        cal.set(Calendar.HOUR,0);
                        cal.set(Calendar.MINUTE,0);
                        cal.set(Calendar.SECOND,0);
                        Date ld = new Date(cal.getTimeInMillis());


                        android.util.Log.e("lastdate and fdom",ld.toString()+" "+ld.getTime()+"    "+firstDayOfNewMonth.getTime()+" "+firstDayOfNewMonth.toString());

                        if(ld.getTime()==firstDayOfNewMonth.getTime()){
                            android.util.Log.e("same month","same month");
                        }

                        //if scroll to next month
                        else if (ld.getTime() < firstDayOfNewMonth.getTime()) {

                            android.util.Log.e("next month","next month");

                            int lastperiodday = Integer.parseInt(dayformatter.format(lastdate.getTime()));
                            int lastperiodmonth = Integer.parseInt(monthformatter.format(lastdate.getTime()));
                            int myear = Integer.parseInt(yearformatter.format(lastdate.getTime()));
                            //  logs.clear();
                            // logListRecViewAdapter.notifyDataSetChanged();
                            logs.clear();
                            logListRecViewAdapter.notifyDataSetChanged();
                            seteventsoncalendar(lastperiodday, lastperiodmonth-1, periodcyclelength-1, periodduration, myear);
                            logListRecViewAdapter.notifyDataSetChanged();
//                    posiion = posiion+ periodduration + 5;
//                    periodCalenderBinding.periodCalenderRecyclerView.getLayoutManager().scrollToPosition(posiion);


                            //if scroll to previous month
                        } else if (ld.getTime() > firstDayOfNewMonth.getTime()) {

                            android.util.Log.e("prev month","prev month");
                            int lastperiodday = Integer.parseInt(dayformatter.format(lastdate.getTime()));
                            int lastperiodmonth = Integer.parseInt(monthformatter.format(lastdate.getTime()));
                            int myear = Integer.parseInt(yearformatter.format(lastdate.getTime()));
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.DAY_OF_MONTH,lastperiodday);
                            calendar.set(Calendar.MONTH,lastperiodmonth);
                            calendar.set(Calendar.YEAR,myear);
                            lastperiodday = Integer.parseInt(dayformatter.format(calendar.getTimeInMillis()));
                            lastperiodmonth = Integer.parseInt(monthformatter.format(calendar.getTimeInMillis()));
                            myear = Integer.parseInt(yearformatter.format(calendar.getTimeInMillis()));
                            logs.clear();
                            logListRecViewAdapter.notifyDataSetChanged();
                            seteventsoncalendar(lastperiodday, lastperiodmonth-2, -(periodcyclelength+1), periodduration, myear);
                            logListRecViewAdapter.notifyDataSetChanged();

                            //  posiion = posiion-(periodduration+5);
                            //  periodCalenderBinding.periodCalenderRecyclerView.getLayoutManager().scrollToPosition(posiion);

                        } else {
                            periodCalenderBinding.periodCalenderRecyclerView.getLayoutManager().scrollToPosition(0);
                        }
                    }

                    periodCalenderBinding.monthTV.setText(dateFormatMonth.format(firstDayOfNewMonth));

                }
                else {

                    prev =true;
                    periodCalenderBinding.compactcalendarView.showNextMonth();
                    prev=false;
                    Toast.makeText(getActivity(), "no events on previous days", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }




    public String getMonthFromNumber(int x) {

        String month = "null";
        switch (x) {
            case 1:
                month = "jan";
                break;
            case 2:
                month = "fab";
                break;
            case 3:
                month = "mar";
                break;
            case 4:
                month = "apr";
                break;
            case 5:
                month = "may";
                break;
            case 6:
                month = "jun";
                break;
            case 7:
                month = "jul";
                break;
            case 8:
                month = "aug";
                break;
            case 9:
                month = "sep";
                break;
            case 10:
                month = "oct";
                break;
            case 11:
                month = "nov";
                break;
            case 12:
                month = "dec";
                break;
        }
        return month;

    }
    public String getextfromnumber(int x) {

        String ext = "null";
        switch (x) {
            case 1:
                ext = "st";
                break;
            case 2:
                ext = "nd";
                break;
            case 3:
                ext = "rd";
                break;
            default:
                ext = "th";
                break;
        }
        return ext;

    }
    private void setlogsandadapter(ArrayList<Log> logs) {
        logstemp = logs;
        logListRecViewAdapter = new LogListRecViewAdapter(logstemp, getActivity());
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(getActivity());
        linearlayoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        periodCalenderBinding.periodCalenderRecyclerView.setLayoutManager(linearlayoutmanager);
        periodCalenderBinding.periodCalenderRecyclerView.setAdapter(logListRecViewAdapter);


    }





    private void seteventsoncalendar(int lastperiodday, int lastperiodmonth, int periodcyclelength, int periodduration, int mYear) {


        calendertemp = Calendar.getInstance();
        calendertemp.set(Calendar.YEAR, mYear);
        calendertemp.set(Calendar.MONTH, lastperiodmonth);
        calendertemp.set(Calendar.DAY_OF_MONTH, lastperiodday);
        calendertemp.add(Calendar.DAY_OF_MONTH, periodcyclelength);
        String dddd = simpleDateFormat.format(calendertemp.getTimeInMillis());
       // Toast.makeText(getActivity(), ""+dddd, Toast.LENGTH_SHORT).show();


        milliTimearray = new long[periodduration];

        for (int i = 0; i < periodduration; i++) {
            calendertemp.add(Calendar.DAY_OF_MONTH, 1);
            milliTimearray[i] = calendertemp.getTimeInMillis();
                ddd.setTime(milliTimearray[i]);
            if (!periodCalenderBinding.compactcalendarView.getEvents(ddd).equals(null)){
                List<Event> event = periodCalenderBinding.compactcalendarView.getEvents(ddd);
                if(!event.equals(null)){
                    periodCalenderBinding.compactcalendarView.removeEvents(event);
                }

            }
                Event eventone = new Event(Color.parseColor("#C072A4"), milliTimearray[i], logTitles[i]);
                periodCalenderBinding.compactcalendarView.addEvent(eventone);
                String date = calendertemp.get(Calendar.DAY_OF_MONTH) + "" + getextfromnumber(calendertemp.get(Calendar.DAY_OF_MONTH)) + " " + getMonthFromNumber(calendertemp.get(Calendar.MONTH) + 1);
                String day = formatter.format(ddd);
                log = new Log(logTitles[i], logSuggessions[i], date, day, logTitleSymbols[i]);
                logs.add(log);
                setlogsandadapter(logs);

                if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION).equals(Utils.STATUS_FALSE) || Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION).equals("null")){
                    notificationIntent = new Intent(getActivity(),AlarmReceiver.class);
                    alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Date dte = new Date(milliTimearray[i]);
                    Calendar cld = Calendar.getInstance();
                    cld.setTime(dte);
                    cld.set(Calendar.HOUR,0);
                    cld.set(Calendar.MINUTE,0);
                    cld.set(Calendar.SECOND,0);
                    cld.set(Calendar.MILLISECOND,0);
                    dte = cld.getTime();
                    android.util.Log.e("alarms:",dte.toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        notificationIntent.putExtra("request_code", i + 100);
                        pendingIntent = PendingIntent.getBroadcast(getActivity(),100+i,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                        // android.util.Log.e("from notification", String.valueOf(simpleDateFormat.format(dte.getTime())));
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, dte.getTime(), pendingIntent);
                    }

                }
                else {
                    if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_WANT_TO_PREGNANT_STATUS).equals(Utils.STATUS_TRUE)){

                    }else {
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION,Utils.STATUS_FALSE);
                    }

                }




        }

        lastdate = new Date(milliTimearray[0]);

//        for (int i = 0; i < milliTimearray.length; i++) {
//
//        }

//        if (log_function == true) {
//
//            for (int i = 0; i < milliTimearray.length; i++) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    notificationIntent.putExtra("request_code", i + 100);
//                    PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), i + 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                    android.util.Log.e("from notification", String.valueOf(milliTimearray[i]));
//                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliTimearray[i], broadcast);
//                }
//            }
//          //  setNotificationAlarts(milliTimearray);
//            if (!pragnancystatus.equals("true")) {
//                log_function = false;
//            }
//        }
        if (pragnancystatus.equals("true")) {
            calendertemp.add(Calendar.DAY_OF_MONTH, 4);
            milliTimearray = new long[5];
            for (int i = 0; i < 5; i++) {
                calendertemp.add(Calendar.DAY_OF_MONTH, 1);
                milliTimearray[i] = calendertemp.getTimeInMillis();
                    ddd.setTime(milliTimearray[i]);
                if (!periodCalenderBinding.compactcalendarView.getEvents(ddd).equals(null)) {
                    List<Event> event = periodCalenderBinding.compactcalendarView.getEvents(ddd);
                    if (!event.equals(null)) {
                        periodCalenderBinding.compactcalendarView.removeEvents(event);
                    }
                }
                    Event eventone = new Event(Color.parseColor("#FFA500"), milliTimearray[i], "Favourable pregnancy date");
                    periodCalenderBinding.compactcalendarView.addEvent(eventone);
                    String mon = formatter.format(ddd);
                    String date = calendertemp.get(Calendar.DAY_OF_MONTH) + "" + getextfromnumber(calendertemp.get(Calendar.DAY_OF_MONTH)) + " " + getMonthFromNumber(calendertemp.get(Calendar.MONTH) + 1);
                    log = new Log(logTitlesPregnant[i], logSuggessionsPregnant[i], date, mon, logTitleSymbolsPregnant[i]);
                    logs.add(log);
                    setlogsandadapter(logs);

                    if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION).equals(Utils.STATUS_FALSE)||Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION).equals("null")){
                        notificationIntent = new Intent(getActivity(),AlarmReceiver.class);
                        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Date dte = new Date(milliTimearray[i]);
                        Calendar cld = Calendar.getInstance();
                        cld.setTime(dte);
                        cld.set(Calendar.HOUR,0);
                        cld.set(Calendar.MINUTE,0);
                        cld.set(Calendar.SECOND,0);
                        cld.set(Calendar.MILLISECOND,0);
                        dte = cld.getTime();
                        android.util.Log.e("alarms:",dte.toString());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            notificationIntent.putExtra("request_code", i + 200);
                            pendingIntent = PendingIntent.getBroadcast(getActivity(),200+i,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                            // android.util.Log.e("from notification", String.valueOf(simpleDateFormat.format(dte.getTime())));
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, dte.getTime(), pendingIntent);
                        }

                    }
                    else {
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION,Utils.STATUS_FALSE);
                    }







            }


//            if (log_function == true) {
//                setNotificationAlarts(milliTimearray);
//                for (int i = 0; i < milliTimearray.length; i++) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        notificationIntent.putExtra("request_code", i + 100);
//                        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), i + 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                        android.util.Log.e("from notification", String.valueOf(milliTimearray[i]));
//                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, milliTimearray[i], broadcast);
//                    }
//                }
//                log_function = false;
//            }

        }


    }


    private DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {


            clearAllAlarms();
            android.util.Log.e("date:",String .valueOf(datePicker.getDayOfMonth()));

            int new_day = datePicker.getDayOfMonth();
            int new_month = datePicker.getMonth();
            int new_year = datePicker.getYear();
            Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_DATE, String.valueOf(new_day));
            Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_MONTH, String.valueOf(new_month));

            android.util.Log.e("error from listener:", String.valueOf(new_day));

            log_function = true;
            Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_FUNCTION,Utils.STATUS_TRUE);
            periodCalenderBinding.compactcalendarView.removeAllEvents();
            logs.clear();
            logListRecViewAdapter.notifyDataSetChanged();
            seteventsoncalendar(new_day, new_month-1, periodcyclelength, periodduration, new_year);
            logListRecViewAdapter.notifyDataSetChanged();
        }
    };


    private void clearAllAlarms(){
        if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_WANT_TO_PREGNANT_STATUS).equals(Utils.STATUS_FALSE)){

            for (int i = 0; i < periodduration; i++) {
                PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), i + 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                android.util.Log.e("from notification", String.valueOf(milliTimearray[i]));
                alarmManager.cancel(broadcast);
                broadcast.cancel();
            }
        }
        else {

            for (int i = 0; i < periodduration; i++) {
                PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), i + 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(broadcast);
                broadcast.cancel();
            }

            for (int i = 0; i < 5; i++) {
                PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), i + 200, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.cancel(broadcast);
                broadcast.cancel();
            }
        }

    }


}
