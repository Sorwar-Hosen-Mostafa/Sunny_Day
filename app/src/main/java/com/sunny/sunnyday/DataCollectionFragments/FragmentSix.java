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

import com.sunny.sunnyday.Fragments.PeriodCalenderFragment;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentFragmentSixBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSix extends Fragment {



    private PeriodCalenderFragment periodCalenderFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainActivity mainActivity;
    FragmentFragmentSixBinding sixBinding;
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


        mainActivity = (MainActivity) getActivity();

        sixBinding.buttonNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.DATA_COLLECTED_STATUS,"true");
                    periodCalenderFragment = new PeriodCalenderFragment();
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
