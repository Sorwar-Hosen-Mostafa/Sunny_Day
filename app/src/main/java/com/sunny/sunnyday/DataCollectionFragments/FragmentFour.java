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

import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentFragmentFourBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFour extends Fragment {

    private FragmentFive fragmentFive;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainActivity mainActivity;
    private FragmentFragmentFourBinding fourBinding;
    private FragmentThree fragmentThree;
    private int cycle_length;
    private boolean set;
    boolean checkstatus= false;
    public FragmentFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fourBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_four, container, false);
        View view = fourBinding.getRoot();


        return view;
    }
    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"HelveticaLTStd-BlkCond.ttf");
        fourBinding.CycleLengthPicker.setTypeface(typeface);
        mainActivity = (MainActivity) getActivity();


        fourBinding.idontknowRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkstatus){
                    checkstatus=false;
                    fourBinding.idontknowRB.setChecked(false);
                    set=false;
                    fourBinding.CycleLengthPicker.setEnabled(true);
                    cycle_length = fourBinding.CycleLengthPicker.getValue();

                }
                else {
                    set=true;
                    checkstatus=true;
                    fourBinding.idontknowRB.setChecked(true);
                    cycle_length = 30;
                    fourBinding.CycleLengthPicker.setEnabled(false);


                }

            }
        });



        fourBinding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentFive = new FragmentFive();

                if(!set){
                    cycle_length = fourBinding.CycleLengthPicker.getValue();
                }

                Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_CYCLE_LENGTH,String .valueOf(cycle_length));

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentFive, "fragmentFive");
                fragmentTransaction.commit();
            }
        });
        fourBinding.backbuttonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentThree= new FragmentThree();

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentThree, "fragthree");
                fragmentTransaction.commit();
            }
        });
    }


}
