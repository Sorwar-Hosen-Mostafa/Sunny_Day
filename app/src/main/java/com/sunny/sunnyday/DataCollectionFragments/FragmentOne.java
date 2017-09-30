package com.sunny.sunnyday.DataCollectionFragments;


import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.shawnlin.numberpicker.NumberPicker;
import com.sunny.sunnyday.Fragments.HealthAndFitnessFragment;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentFragmentOneBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {


    private MainActivity mainActivity;
    private FragmentFragmentOneBinding oneBinding;
    private FragmentTwo fragmentTwo;
    private NumberPicker numberPicker;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private HealthAndFitnessFragment healthAndFitnessFragment;
    private int radio_btn_id;

    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        oneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_one, container, false);
        View view = oneBinding.getRoot();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity) getActivity();




        oneBinding.buttonNext.setEnabled(false);

        oneBinding.radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                oneBinding.buttonNext.setEnabled(true);
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    oneBinding.buttonNext.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_button_data_collection_two));

                } else {
                    oneBinding.buttonNext.setBackground(getResources().getDrawable(R.drawable.border_button_data_collection_two));
                }
            }
        });

        oneBinding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radio_btn_id = oneBinding.radiogroup.getCheckedRadioButtonId();

                switch (radio_btn_id){
                    case R.id.wantToPregRadioButton:

                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_WANT_TO_PREGNANT_STATUS,Utils.STATUS_TRUE);
                        break;
                    case R.id.wantToTrackCycleRadioButton:

                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_WANT_TO_PREGNANT_STATUS,Utils.STATUS_FALSE);
                        break;
                }


                fragmentTwo = new FragmentTwo();
                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentTwo, "fragmentTwo");
                fragmentTransaction.commit();
            }
        });
        oneBinding.backbuttonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthAndFitnessFragment= new HealthAndFitnessFragment();

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, healthAndFitnessFragment, "healthFrag");
                fragmentTransaction.commit();
            }
        });
    }
}
