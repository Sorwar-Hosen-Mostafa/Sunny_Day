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
import android.widget.CompoundButton;
import android.widget.Toast;

import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentFragmentThreeBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment {


    private FragmentFour fragmentFour;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainActivity mainActivity;
    FragmentFragmentThreeBinding threeBinding;
    private FragmentTwo fragmentTwo;
    private int period_duration;
    boolean checkstatus = false;
    private boolean set=false;
    public FragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        threeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_three, container, false);
        View view = threeBinding.getRoot();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"Jersey M54.ttf");
        threeBinding.cyclePeriodPicker.setTypeface(typeface);
        threeBinding.idontknowRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkstatus){
                    checkstatus=false;
                    threeBinding.idontknowRB.setChecked(false);
                    set=false;
                    threeBinding.cyclePeriodPicker.setEnabled(true);
                    period_duration = threeBinding.cyclePeriodPicker.getValue();





                }
                else {
                    set=true;
                    checkstatus=true;
                    threeBinding.idontknowRB.setChecked(true);
                    period_duration = 3;
                    threeBinding.cyclePeriodPicker.setEnabled(false);


                }

            }
        });
//        threeBinding.idontknowRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    set=true;
//                    period_duration = 3;
//                    threeBinding.cyclePeriodPicker.setEnabled(false);
//                    threeBinding.idontknowRB.setChecked(true);
//                }
//                else {
//                    set=false;
//                    period_duration = threeBinding.cyclePeriodPicker.getValue();
//                    threeBinding.cyclePeriodPicker.setEnabled(true);
//                }
//            }
//        });

        mainActivity = (MainActivity) getActivity();

        threeBinding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentFour = new FragmentFour();
                if(!set){
                    period_duration = threeBinding.cyclePeriodPicker.getValue();
                }
                Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_PERIOD_DURATION,String .valueOf(period_duration));

               // Toast.makeText(getActivity(), Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_PERIOD_DURATION)+" ",Toast.LENGTH_LONG).show();

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentFour, "fragmentFour");
                fragmentTransaction.commit();
            }
        });
        threeBinding.backbuttonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTwo= new FragmentTwo();

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentTwo, "fragtwo");
                fragmentTransaction.commit();
            }
        });

    }

}
