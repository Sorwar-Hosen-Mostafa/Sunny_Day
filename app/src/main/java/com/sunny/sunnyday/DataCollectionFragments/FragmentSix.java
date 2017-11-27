package com.sunny.sunnyday.DataCollectionFragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunny.sunnyday.Fragments.PeriodCalendarFragment;
import com.sunny.sunnyday.Fragments.SettingsFragment;
import com.sunny.sunnyday.LocalDatabase.SavedArticleDAO;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.Model.History;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentFragmentSixBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSix extends Fragment {


    SavedArticleDAO savedArticleDao;
    History history;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d");
    private PeriodCalendarFragment periodCalenderFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager2;
    private FragmentTransaction fragmentTransaction2;
    private MainActivity mainActivity;
    FragmentFragmentSixBinding sixBinding;
    private SettingsFragment settingsFragment;
    private FragmentFive fragmentFive;
    public FragmentSix() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sixBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_six, container, false);
        View view = sixBinding.getRoot();


        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int month= Integer.parseInt(Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_MONTH));
        int startday= Integer.parseInt(Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_LAST_PERIOD_DATE));
        int duration= Integer.parseInt(Utils.getFromPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_PERIOD_DURATION));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,startday);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        int year = calendar.get(Calendar.YEAR);
        String start_date = simpleDateFormat.format(calendar.getTimeInMillis());

        calendar.add(calendar.DAY_OF_MONTH,duration-1);


        String end_date = simpleDateFormat.format(calendar.getTimeInMillis());
        String history_id = start_date+""+end_date+""+month+""+year;



        history = new History();
        history.setHistoryId(history_id);
        history.setHistoryMonth(month);
        history.setHistoryYear(year);
        history.setHistoryStartDate(start_date);
        history.setHistoryEndDate(end_date);


        savedArticleDao = new SavedArticleDAO(getActivity());
        savedArticleDao.insert_history(history);

        mainActivity = (MainActivity) getActivity();

        sixBinding.buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.DATA_COLLECTED_STATUS,"true");
                settingsFragment = new SettingsFragment();
                fragmentManager2 = mainActivity.fragmentManager;
                fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.child_fragment_container, settingsFragment, "settingsFrag");
                fragmentTransaction2.addToBackStack("eww");
                fragmentTransaction2.commit();
                    periodCalenderFragment = new PeriodCalendarFragment();
                    fragmentManager = mainActivity.fragmentManager;
                    fragmentTransaction = mainActivity.fragmentTransaction;
                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.child_fragment_container, periodCalenderFragment, "periodCalenderFrag");
                    fragmentTransaction.commit();


            }
        });
        sixBinding.backbuttonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentFive= new FragmentFive();

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentFive, "fragfive");
                fragmentTransaction.commit();
            }
        });
    }


}
