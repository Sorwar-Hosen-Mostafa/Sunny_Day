package com.sunny.sunnyday.DataCollectionFragments;


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

import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentFragmentTwoBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    private FragmentThree fragmentThree;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainActivity mainActivity;
    private FragmentFragmentTwoBinding twoBinding;
    private FragmentOne fragmentOne;
    private String date;
    SimpleDateFormat dayformatter = new SimpleDateFormat("dd");
    SimpleDateFormat monthformatter = new SimpleDateFormat("MM");
    private String month;
    private int number;
    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        twoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_two, container, false);
        View view = twoBinding.getRoot();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Calendar calendar = Calendar.getInstance();

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);



        mainActivity = (MainActivity) getActivity();
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"Jersey M54.ttf");
        twoBinding.datePicker.setTypeface(typeface);
        twoBinding.monthPicker.setDisplayedValues(new String[]{"JAN","FAB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"});
        twoBinding.monthPicker.setValue(month+1);
        twoBinding.monthPicker.setTypeface(typeface);

        twoBinding.datePicker.setValue(day);


        twoBinding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date = String .valueOf(twoBinding.datePicker.getValue());
                number = twoBinding.monthPicker.getValue();
               // month = getMonthFromNumber(number);
                Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_DATE,date);
                Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_MONTH,String .valueOf(number));

               // Toast.makeText(getActivity(), Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_DATE)+" "+Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_LAST_PERIOD_MONTH),Toast.LENGTH_LONG).show();

                fragmentThree = new FragmentThree();
                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentThree, "fragmentThree");
                fragmentTransaction.commit();
            }
        });

        twoBinding.backbuttonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentOne= new FragmentOne();

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentOne, "fragone");
                fragmentTransaction.commit();
            }
        });

    }



}
