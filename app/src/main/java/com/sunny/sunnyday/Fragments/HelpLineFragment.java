package com.sunny.sunnyday.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpLineFragment extends Fragment {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LinearLayout linearLayout1,linearLayout2,linearLayout3;
    ImageView backbutton;

    public HelpLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_line, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MainActivity mainActivity = (MainActivity) getActivity();
        linearLayout1 = (LinearLayout) getActivity().findViewById(R.id.first_number);
        linearLayout2 = (LinearLayout) getActivity().findViewById(R.id.second_number);
        linearLayout3 = (LinearLayout) getActivity().findViewById(R.id.third_number);
        backbutton = (ImageView) getActivity().findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment= new SettingsFragment();

                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, settingsFragment, "haff");
                fragmentTransaction.commit();
            }
        });
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01628749318"));
                startActivity(intent);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01521432006"));
                startActivity(intent);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01787167081"));
                startActivity(intent);
            }
        });

    }
}
