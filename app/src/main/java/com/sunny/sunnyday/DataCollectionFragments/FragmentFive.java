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
import com.sunny.sunnyday.databinding.FragmentFragmentFiveBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFive extends Fragment {


    private FragmentSix fragmentSix;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainActivity mainActivity;
    private FragmentFragmentFiveBinding fiveBinding;
    private FragmentFour fragmentFour;
    private String yearOfBirth;
    public FragmentFive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fiveBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fragment_five, container, false);
        View view = fiveBinding.getRoot();


        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"HelveticaLTStd-BlkCond.ttf");
        fiveBinding.dataOfBirthPicker.setTypeface(typeface);
        mainActivity = (MainActivity) getActivity();



        fiveBinding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentSix = new FragmentSix();


                yearOfBirth = String.valueOf(fiveBinding.dataOfBirthPicker.getValue());
                Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_BIRTH_YEAR, yearOfBirth);
               // Toast.makeText(getActivity(), Utils.getFromPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.USER_BIRTH_YEAR)+" ",Toast.LENGTH_LONG).show();

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentSix, "fragmentSix");
                fragmentTransaction.commit();
            }
        });
        fiveBinding.backbuttonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentFour= new FragmentFour();

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, fragmentFour, "fragfour");
                fragmentTransaction.commit();
            }
        });
    }


}
