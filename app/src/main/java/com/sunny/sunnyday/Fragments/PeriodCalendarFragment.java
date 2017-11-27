package com.sunny.sunnyday.Fragments;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import com.sunny.sunnyday.LocalDatabase.SavedArticleDAO;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.Model.History;
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



public class PeriodCalendarFragment extends Fragment {

    private Log log;
    private ArrayList<Log> logs;
    int lastYear = 0;
    boolean prev = false;
    private String log_clicked;
    boolean periodalarmset = false;
    String log_set ;
    boolean pregnancyalarmset = false;
    SimpleDateFormat simpleDateFormatforhistory = new SimpleDateFormat("MMM d");
    private SimpleDateFormat dateFormatMonth;
    private FragmentPeriodCalenderBinding periodCalenderBinding;
    private Calendar calendar;
    SimpleDateFormat formatter = new SimpleDateFormat("EEE");
    SimpleDateFormat dayformatter = new SimpleDateFormat("dd");
    SimpleDateFormat monthformatter = new SimpleDateFormat("MM");
    SimpleDateFormat yearformatter = new SimpleDateFormat("yyyy");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
    private Calendar calendarkemp;
    ArrayList<Integer> months;
    Calendar calendarforeventset;
    LogListRecViewAdapter logListRecViewAdapter;
    Calendar calendarcurrent = Calendar.getInstance(Locale.getDefault());
    Date datecurrent;
    boolean log_function;
    boolean end_function;
    private Date calendarDateClicked;
    Calendar calendarmain;
    private long milliTimearray[];
    private String logTitles[], logTitleSymbols[], logSuggessions[], logTitlesPregnant[], logTitleSymbolsPregnant[], logSuggessionsPregnant[];
    int mYear, mMonth, mDay, periodduration, periodcyclelength, lastperiodmonth, lastperiodday;
    Date lastdate;
    Date ddd, datekemp;
    ArrayList<Log> logstemp;
    Date datemain;
    Date ldtemp;
    Intent notificationIntent;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    String pragnancystatus;

    public PeriodCalendarFragment() {
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



        log_clicked=Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_CLICKED);
        if(log_clicked.equals("true")){
            periodCalenderBinding.logText.setText("END");
            periodCalenderBinding.logImage.setColorFilter(ContextCompat.getColor(getActivity(), R.color.state_unselected));
        }else {
            periodCalenderBinding.logText.setText("LOG");
            periodCalenderBinding.logImage.setColorFilter(ContextCompat.getColor(getActivity(), R.color.state_selected));
        }

        calendarmain = Calendar.getInstance();
        calendarmain.set(Calendar.HOUR, 0);
        calendarmain.set(Calendar.MILLISECOND, 0);
        calendarmain.set(Calendar.SECOND, 0);
        calendarmain.set(Calendar.MINUTE, 0);
        datemain = new Date(calendarmain.getTimeInMillis());
        datemain.setHours(0);


        months = new ArrayList<>();
        calendarcurrent.set(Calendar.HOUR, 0);
        calendarcurrent.set(Calendar.MINUTE, 0);
        calendarcurrent.set(Calendar.SECOND, 0);
        calendarcurrent.set(Calendar.MILLISECOND, 0);
        calendarcurrent.set(Calendar.DAY_OF_MONTH, 1);
        datecurrent = new Date(calendarcurrent.getTimeInMillis());
        datecurrent.setHours(0);


        calendarkemp = Calendar.getInstance();
        calendarkemp = calendarcurrent;
        calendarkemp.add(Calendar.MONTH, 5);
        datekemp = new Date(calendarkemp.getTimeInMillis());
        datekemp.setHours(0);


        logs = new ArrayList<>();
        ddd = new Date();


        log_set = Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_SET);

        dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

        logTitles = getResources().getStringArray(R.array.logs_title);
        logTitleSymbols = getResources().getStringArray(R.array.logs_title_symbols);
        logTitlesPregnant = getResources().getStringArray(R.array.logs_title_pregnant);
        logTitleSymbolsPregnant = getResources().getStringArray(R.array.logs_title_symbols_pregnant);
        logSuggessionsPregnant = getResources().getStringArray(R.array.logs_suggessions_pregnant);


        if(log_set.equals("1")){
            logSuggessions = getResources().getStringArray(R.array.logs_suggessions);
        }else if(log_set.equals("2")){
            logSuggessions = getResources().getStringArray(R.array.logs_suggessions_two);
        }else if(log_set.equals("3")){
            logSuggessions = getResources().getStringArray(R.array.logs_suggessions_three);
        }else if (log_set.equals("4")){
            logSuggessions = getResources().getStringArray(R.array.logs_suggessions_four);
        }else {
            logSuggessions = getResources().getStringArray(R.array.logs_suggessions);
        }



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

        if (!Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_YEAR).equals("null")) {
            lastYear = Integer.parseInt(Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_YEAR));
        } else {
            lastYear = calendar.get(Calendar.YEAR);
        }

        android.util.Log.e("whatcomesfrompref", lastperiodday + " " + lastperiodmonth + " " + lastYear);

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

        periodCalenderBinding.logImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, dateSet, mYear, Integer.parseInt(monthformatter.format(datemain.getTime())) - 1, Integer.parseInt(dayformatter.format(datemain.getTime())));
                dateDialog.show();
            }
        });




        if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_FUNCTION).equals(Utils.STATUS_TRUE)){
            calendarforeventset = Calendar.getInstance();
            calendarforeventset.set(Calendar.YEAR, lastYear);
            calendarforeventset.set(Calendar.MONTH, lastperiodmonth);
            calendarforeventset.set(Calendar.DAY_OF_MONTH, lastperiodday-1);
            seteventsoncalendarnew(calendarforeventset);
        }
        else {
            calendarforeventset = Calendar.getInstance();
            calendarforeventset.set(Calendar.YEAR, lastYear);
            calendarforeventset.set(Calendar.MONTH, lastperiodmonth-1);
            calendarforeventset.set(Calendar.DAY_OF_MONTH, lastperiodday);
            calendarforeventset.add(Calendar.DAY_OF_MONTH, periodcyclelength-1);
            android.util.Log.e("first time cal_date", calendarforeventset.getTimeInMillis() + " " + simpleDateFormat.format(calendarforeventset.getTimeInMillis()));
            seteventsoncalendarnew(calendarforeventset);
        }



        periodCalenderBinding.compactcalendarView.shouldScrollMonth(false);
        periodCalenderBinding.compactcalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

//                calendarDateClicked = dateClicked;
//                if (periodCalenderBinding.compactcalendarView.getEvents(dateClicked).size() > 0) {
//                    Event event = periodCalenderBinding.compactcalendarView.getEvents(dateClicked).get(0);
//                    Toast.makeText(getActivity(), "" + event.getData().toString(), Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                if (datecurrent.getTime() < firstDayOfNewMonth.getTime() || datecurrent.getTime() == firstDayOfNewMonth.getTime()) {

                    if (prev) {

                    } else {
                        ldtemp = new Date(lastdate.getTime());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(lastdate);
                        cal.set(Calendar.MILLISECOND, 0);
                        cal.set(Calendar.HOUR, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        Date ld = new Date(cal.getTimeInMillis());


                        android.util.Log.e("lastdate and fdom", ld.toString() + " " + ld.getTime() + "    " + firstDayOfNewMonth.getTime() + " " + firstDayOfNewMonth.toString());

                        if (ld.getTime() == firstDayOfNewMonth.getTime()) {
                            android.util.Log.e("same month", "same month");

                        }

                        //if scroll to next month
                        else if (ld.getTime() < firstDayOfNewMonth.getTime()) {

                            android.util.Log.e("next month", "next month");

                            Date date = new Date(lastdate.getTime());
                            date.setHours(0);
                            date.setMinutes(0);
                            date.setSeconds(0);
                            if (date.getTime() < datekemp.getTime()) {
                                logs.clear();

                                if (lastdate != null) {
                                    calendarforeventset.setTime(lastdate);
                                }

                                calendarforeventset.add(Calendar.DAY_OF_MONTH, periodcyclelength - 1);
                                logListRecViewAdapter.notifyDataSetChanged();
                                seteventsoncalendarnew(calendarforeventset);
                                logListRecViewAdapter.notifyDataSetChanged();

                            } else {
                                prev = true;
                                periodCalenderBinding.compactcalendarView.showPreviousMonth();
                                firstDayOfNewMonth.setMonth(firstDayOfNewMonth.getMonth() - 1);
                                periodCalenderBinding.monthTV.setText(dateFormatMonth.format(firstDayOfNewMonth));
                                prev = false;
                                Toast.makeText(getActivity(), "no predictions on next days", Toast.LENGTH_SHORT).show();
                            }

                        } else if (ld.getTime() > firstDayOfNewMonth.getTime()) {

                            android.util.Log.e("prev month", "prev month");

                            if (lastdate != null) {
                                calendarforeventset.setTime(lastdate);
                            }
                            calendarforeventset.add(Calendar.DAY_OF_MONTH, -(periodcyclelength + 1));
                            logs.clear();
                            logListRecViewAdapter.notifyDataSetChanged();
                            seteventsoncalendarnew(calendarforeventset);
                            logListRecViewAdapter.notifyDataSetChanged();


                        } else {
                            periodCalenderBinding.periodCalendrRecyclerView.getLayoutManager().scrollToPosition(0);
                        }
                    }

                    periodCalenderBinding.monthTV.setText(dateFormatMonth.format(firstDayOfNewMonth));

                } else {

                    prev = true;
                    periodCalenderBinding.compactcalendarView.showNextMonth();
                    prev = false;
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
        periodCalenderBinding.periodCalendrRecyclerView.setLayoutManager(linearlayoutmanager);
        periodCalenderBinding.periodCalendrRecyclerView.setAdapter(logListRecViewAdapter);


    }


    private DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

            if(log_clicked.equals("null") || log_clicked.equals("false")){


                datePicker.getDrawingTime();
                int new_day = datePicker.getDayOfMonth();
                int new_month = datePicker.getMonth();
                int new_year = datePicker.getYear();

                calendarforeventset.set(Calendar.DAY_OF_MONTH, new_day);
                calendarforeventset.set(Calendar.MONTH, new_month);
                calendarforeventset.set(Calendar.YEAR, new_year);
                calendarforeventset.set(Calendar.HOUR, 0);
                calendarforeventset.set(Calendar.MINUTE, 0);
                calendarforeventset.set(Calendar.SECOND, 0);
                calendarforeventset.set(Calendar.MILLISECOND, 0);

                Calendar calendar_now_time = Calendar.getInstance();
                calendar_now_time.set(Calendar.HOUR,0);
                calendar_now_time.set(Calendar.MINUTE,0);
                calendar_now_time.set(Calendar.SECOND,0);
                calendar_now_time.set(Calendar.MILLISECOND,0);

                if(calendarforeventset.getTimeInMillis()<=calendar_now_time.getTimeInMillis()){
                    clearAllAlarms();
                    calendarforeventset.add(Calendar.DAY_OF_MONTH,-1);
                    lastperiodday = new_day;
                    lastperiodmonth = new_month;
                    lastYear = new_year;

                    periodCalenderBinding.compactcalendarView.removeAllEvents();
                    logs.clear();
                    logListRecViewAdapter.notifyDataSetChanged();

                    log_function = true;
                    Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.LOG_FUNCTION, Utils.STATUS_TRUE);
                    Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_DATE,String.valueOf(new_day));
                    Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_MONTH,String.valueOf(new_month));
                    Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_YEAR,String.valueOf(new_year));

                    log_set = Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_SET);

                    if(log_set.equals("1")){
                        logSuggessions = getResources().getStringArray(R.array.logs_suggessions_two);
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_SET,"2");
                    }else if(log_set.equals("2")){
                        logSuggessions = getResources().getStringArray(R.array.logs_suggessions_three);
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_SET,"3");

                    }else if(log_set.equals("3")){
                        logSuggessions = getResources().getStringArray(R.array.logs_suggessions_four);
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_SET,"4");

                    }else if (log_set.equals("4")){
                        logSuggessions = getResources().getStringArray(R.array.logs_suggessions);
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_SET,"1");
                    }
                    else {
                        logSuggessions = getResources().getStringArray(R.array.logs_suggessions_two);
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_SET,"2");
                    }


                    seteventsoncalendarnew(calendarforeventset);

                    logListRecViewAdapter.notifyDataSetChanged();

                    periodCalenderBinding.logText.setText("END");
                    periodCalenderBinding.logImage.setColorFilter(ContextCompat.getColor(getActivity(), R.color.state_unselected));
                    log_clicked="true";
                    Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_CLICKED,"true");




                }

                else {
                    Toast.makeText(getActivity(),"You can't log a future date",Toast.LENGTH_LONG).show();
                }


            }
            else {

                Calendar calendar_now_time = Calendar.getInstance();
                calendar_now_time.set(Calendar.HOUR,0);
                calendar_now_time.set(Calendar.MINUTE,0);
                calendar_now_time.set(Calendar.SECOND,0);
                calendar_now_time.set(Calendar.MILLISECOND,0);

                datePicker.getDrawingTime();
                int new_day = datePicker.getDayOfMonth();
                int new_month = datePicker.getMonth();
                int new_year = datePicker.getYear();

                calendarforeventset.set(Calendar.DAY_OF_MONTH, new_day);
                calendarforeventset.set(Calendar.MONTH, new_month);
                calendarforeventset.set(Calendar.YEAR, new_year);
                calendarforeventset.set(Calendar.HOUR, 0);
                calendarforeventset.set(Calendar.MINUTE, 0);
                calendarforeventset.set(Calendar.SECOND, 0);
                calendarforeventset.set(Calendar.MILLISECOND, 0);



                if(calendarforeventset.getTimeInMillis()<=calendar_now_time.getTimeInMillis()){
                    log_function = true;
                    int new_day_temp = Integer.parseInt(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_DATE));
                    int new_month_temp = Integer.parseInt(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_MONTH));
                    int new_year_temp = Integer.parseInt(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_YEAR));

                    Calendar calendartemp= Calendar.getInstance();
                    calendartemp.set(Calendar.YEAR,new_year_temp);
                    calendartemp.set(Calendar.MONTH,new_month_temp);
                    calendartemp.set(Calendar.DAY_OF_MONTH,new_day_temp);
                    calendartemp.set(Calendar.HOUR,0);
                    calendartemp.set(Calendar.MINUTE,0);
                    calendartemp.set(Calendar.SECOND,0);
                    calendartemp.set(Calendar.MILLISECOND,0);
                    Date datefromprevious = new Date(calendartemp.getTimeInMillis());
                    String dfp = simpleDateFormat.format(datefromprevious);
                    android.util.Log.e("date from previuous",dfp);


                    Calendar calendarforeventsetmain = Calendar.getInstance();
                    calendarforeventsetmain.setTimeInMillis(calendarforeventset.getTimeInMillis());


                    if(calendartemp.getTimeInMillis()<calendarforeventset.getTimeInMillis()){

                        int i = 1;
                        for(;calendarforeventsetmain.getTimeInMillis()>calendartemp.getTimeInMillis();calendarforeventsetmain.add(Calendar.DAY_OF_MONTH,-1)){
                            i++;
                        }
                        //   Toast.makeText(getActivity(),String.valueOf(i),Toast.LENGTH_LONG).show();
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_PERIOD_DURATION,String .valueOf(i));


                        if(i<=10){
                            periodduration=i;
                            Toast.makeText(getActivity(),"End date selected",Toast.LENGTH_LONG).show();
                            periodCalenderBinding.logText.setText("LOG");
                            periodCalenderBinding.logImage.setColorFilter(ContextCompat.getColor(getActivity(), R.color.state_selected));
                            log_clicked="false";
                            Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_CLICKED,"false");

                            clearAllAlarms();
                            periodCalenderBinding.compactcalendarView.removeAllEvents();
                            logs.clear();
                            logListRecViewAdapter.notifyDataSetChanged();

                            Calendar calendarforhistory = Calendar.getInstance();
                            calendarforhistory.setTimeInMillis(calendartemp.getTimeInMillis());

                            calendartemp.add(Calendar.DAY_OF_MONTH,-1);
                            seteventsoncalendarnew(calendartemp);


                            android.util.Log.e("calendar for history",simpleDateFormat.format(calendartemp.getTimeInMillis()).toString());
                            int month_for_history = Integer.parseInt(monthformatter.format(calendarforhistory.getTimeInMillis()));
                            int year_for_history = Integer.parseInt(yearformatter.format(calendarforhistory.getTimeInMillis()));
                            String startdate_for_history = simpleDateFormatforhistory.format(calendarforhistory.getTimeInMillis());
                            calendarforhistory.add(Calendar.DAY_OF_MONTH,periodduration-1);
                            String enddate_for_history = simpleDateFormatforhistory.format(calendarforhistory.getTimeInMillis());
                            History history = new History();
                            history.setHistoryId(startdate_for_history+""+enddate_for_history+""+month_for_history+""+year_for_history);
                            history.setHistoryMonth(month_for_history);
                            history.setHistoryYear(year_for_history);
                            history.setHistoryStartDate(startdate_for_history);
                            history.setHistoryEndDate(enddate_for_history);
                            SavedArticleDAO savedArticleDAO = new SavedArticleDAO(getActivity());
                            savedArticleDAO.insert_history(history);


                            logListRecViewAdapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getActivity(),"Period duration can't be that long, please recheck!",Toast.LENGTH_LONG).show();
                        }

                    }
                    else if (calendartemp.getTimeInMillis()==calendarforeventset.getTimeInMillis()){

                        Toast.makeText(getActivity(),"Start date and end date can't be same",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(),"End date can't be a previous of start date",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(),"You can't select a future date as end date",Toast.LENGTH_LONG).show();

                }





            }


        }
    };

    private void clearAllAlarms() {
        if (Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_WANT_TO_PREGNANT_STATUS).equals(Utils.STATUS_FALSE)) {

            for (int i = 0; i < periodduration; i++) {
                if (periodalarmset == true) {
                    PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), i + 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    //android.util.Log.e("from notification", String.valueOf(milliTimearray[i]));
                    alarmManager.cancel(broadcast);
                    broadcast.cancel();
                }
            }
        } else {

            for (int i = 0; i < periodduration; i++) {
                if (periodalarmset == true) {
                    PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), i + 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(broadcast);
                    broadcast.cancel();
                }

            }

            for (int i = 0; i < 5; i++) {
                if (pregnancyalarmset == true) {
                    PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), i + 200, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(broadcast);
                    broadcast.cancel();
                }

            }
        }

    }


    private void seteventsoncalendarnew(Calendar cal) {

        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date date2 = new Date(cal.getTimeInMillis());
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        android.util.Log.e("first time cal_date", cal.getTimeInMillis() + " " + simpleDateFormat.format(cal.getTimeInMillis()));

        milliTimearray = new long[periodduration];

        for (int i = 0; i < periodduration; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            milliTimearray[i] = cal.getTimeInMillis();
            ddd.setTime(milliTimearray[i]);
            ddd.setHours(0);

            if (!periodCalenderBinding.compactcalendarView.getEvents(ddd).equals(null)) {
                List<Event> event = periodCalenderBinding.compactcalendarView.getEvents(ddd);
                if (!event.equals(null)) {
                    periodCalenderBinding.compactcalendarView.removeEvents(event);
                }

            }
            Event eventone = new Event(Color.parseColor("#C072A4"), ddd.getTime(), logTitles[i]);
            periodCalenderBinding.compactcalendarView.addEvent(eventone);

            String date = cal.get(Calendar.DAY_OF_MONTH) + "" + getextfromnumber(cal.get(Calendar.DAY_OF_MONTH)) + " " + getMonthFromNumber(cal.get(Calendar.MONTH) + 1);
            String day = formatter.format(ddd);
            log = new Log(logTitles[i], logSuggessions[i], date, day, logTitleSymbols[i]);
            logs.add(log);
            setlogsandadapter(logs);

            if (datemain.getTime() <= ddd.getTime()) {
                if (Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.FROM_NOTIFICATION).equals(Utils.STATUS_FALSE) || Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.FROM_NOTIFICATION).equals("null")) {
                    notificationIntent = new Intent(getActivity(), AlarmReceiver.class);
                    alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Date dte = new Date(milliTimearray[i]);
                    Calendar cld = Calendar.getInstance();
                    cld.setTime(dte);
                    cld.set(Calendar.HOUR, 0);
                    cld.set(Calendar.MINUTE, 0);
                    cld.set(Calendar.SECOND, 0);
                    cld.set(Calendar.MILLISECOND, 0);
                    dte = cld.getTime();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        notificationIntent.putExtra("request_code", i + 100);
                        pendingIntent = PendingIntent.getBroadcast(getActivity(), 100 + i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, dte.getTime(), pendingIntent);
                        periodalarmset = true;
                    }

                } else {
                    if (Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_WANT_TO_PREGNANT_STATUS).equals(Utils.STATUS_TRUE)) {

                    } else {
                        Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.FROM_NOTIFICATION, Utils.STATUS_FALSE);
                    }

                }
            }


        }

        lastdate = new Date(milliTimearray[0]);

        if (lastdate.getTime() < datemain.getTime()) {
            periodCalenderBinding.compactcalendarView.setCurrentDate(datemain);
            periodCalenderBinding.monthTV.setText(dateFormatMonth.format(datemain));
        } else {
            if (ldtemp != null) {
                if (monthformatter.format(ldtemp.getTime()).equals(monthformatter.format(lastdate.getTime()))) {

                } else {
                    periodCalenderBinding.compactcalendarView.setCurrentDate(lastdate);
                    periodCalenderBinding.monthTV.setText(dateFormatMonth.format(lastdate));
                }
            } else {
                periodCalenderBinding.compactcalendarView.setCurrentDate(lastdate);
                periodCalenderBinding.monthTV.setText(dateFormatMonth.format(lastdate));
            }


        }


        if (pragnancystatus.equals("true")) {
            cal.setTime(lastdate);
            cal.add(Calendar.DAY_OF_MONTH, 11);
            milliTimearray = new long[5];
            for (int i = 0; i < 5; i++) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                milliTimearray[i] = cal.getTimeInMillis();
                ddd.setTime(milliTimearray[i]);

                if (!periodCalenderBinding.compactcalendarView.getEvents(ddd).equals(null)) {
                    List<Event> event = periodCalenderBinding.compactcalendarView.getEvents(ddd);
                    if (!event.equals(null)) {
                        periodCalenderBinding.compactcalendarView.removeEvents(event);
                    }
                }
                Event eventone = new Event(Color.parseColor("#8CCB28"), ddd.getTime(), "Favourable pregnancy date");
                periodCalenderBinding.compactcalendarView.addEvent(eventone);
                String mon = formatter.format(ddd);
                String date = cal.get(Calendar.DAY_OF_MONTH) + "" + getextfromnumber(cal.get(Calendar.DAY_OF_MONTH)) + " " + getMonthFromNumber(cal.get(Calendar.MONTH) + 1);
                log = new Log(logTitlesPregnant[i], logSuggessionsPregnant[i], date, mon, logTitleSymbolsPregnant[i]);
                logs.add(log);
                setlogsandadapter(logs);

                if (datemain.getTime() <= ddd.getTime()) {
                    if (Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.FROM_NOTIFICATION).equals(Utils.STATUS_FALSE) || Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.FROM_NOTIFICATION).equals("null")) {
                        notificationIntent = new Intent(getActivity(), AlarmReceiver.class);
                        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Date dte = new Date(milliTimearray[i]);
                        Calendar cld = Calendar.getInstance();
                        cld.setTime(dte);
                        cld.set(Calendar.HOUR, 0);
                        cld.set(Calendar.MINUTE, 0);
                        cld.set(Calendar.SECOND, 0);
                        cld.set(Calendar.MILLISECOND, 0);
                        dte = cld.getTime();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            notificationIntent.putExtra("request_code", i + 200);
                            pendingIntent = PendingIntent.getBroadcast(getActivity(), 200 + i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            //    android.util.Log.e("fpd alarms", String.valueOf(simpleDateFormat.format(dte.getTime())));
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, dte.getTime(), pendingIntent);
                            pregnancyalarmset = true;
                        }

                    } else {
                        Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.FROM_NOTIFICATION, Utils.STATUS_FALSE);
                    }
                } else {

                }


            }


        }

//        if(Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_FUNCTION).equals(Utils.STATUS_TRUE)){
//
//            if(periodcyclelength==27){
//                cal2.add(Calendar.DAY_OF_MONTH,-(periodcyclelength+2));
//            }else if(periodcyclelength==28){
//                cal2.add(Calendar.DAY_OF_MONTH,-(periodcyclelength+1));
//            }else if(periodcyclelength==29){
//                cal2.add(Calendar.DAY_OF_MONTH,-(periodcyclelength));
//            }else if(periodcyclelength==31){
//                cal2.add(Calendar.DAY_OF_MONTH,-(periodcyclelength-2));
//            }else if(periodcyclelength==32){
//                cal2.add(Calendar.DAY_OF_MONTH,-(periodcyclelength-3));
//            }else if(periodcyclelength==33){
//                cal2.add(Calendar.DAY_OF_MONTH,-(periodcyclelength-4));
//            }else{
//                cal2.add(Calendar.DAY_OF_MONTH,-(periodcyclelength-1));
//            }
//            int new_day = Integer.parseInt(dayformatter.format(cal2.getTimeInMillis()));
//            int new_month = Integer.parseInt(monthformatter.format(cal2.getTimeInMillis()));
//            int new_year = Integer.parseInt(yearformatter.format(cal2.getTimeInMillis()));
//
//            Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_DATE, String.valueOf(new_day));
//            Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_MONTH, String.valueOf(new_month));
//            Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_YEAR, String.valueOf(new_year));
//            android.util.Log.e("whatsavinginpreflog",new_day +" "+new_month+" "+new_year);
//
//            Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_FUNCTION,Utils.STATUS_FALSE);
//        }


    }


}
