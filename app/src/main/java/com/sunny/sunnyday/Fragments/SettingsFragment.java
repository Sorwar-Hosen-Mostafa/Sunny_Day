package com.sunny.sunnyday.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.sunny.sunnyday.DeveloperInfo;
import com.sunny.sunnyday.LocalDatabase.SavedArticleDAO;
import com.sunny.sunnyday.MainActivity;
import com.sunny.sunnyday.R;
import com.sunny.sunnyday.Utils;
import com.sunny.sunnyday.databinding.FragmentSettingsBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding settingsBinding;
    private SavedArticlesFragment savedArticlesFragment;
    private TermsAndConditionsFragment termsAndConditionsFragment;
    private LisenseFragment lisenseFragment;
    private HelpLineFragment helpLineFragment;
    private HistoryFragment historyFragment;
    private FragmentManager fragmentManager;
    String notification_status;
    String pregnancy_mode_status;

    private FragmentTransaction fragmentTransaction;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        View view = settingsBinding.getRoot();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        settingsBinding.notificationchkState.setSelected(true);

        notification_status= Utils.getFromPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.NOTIFICATION);
        pregnancy_mode_status= Utils.getFromPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.PREGNANCY_MODE);

        if(notification_status.equals("null")){
            Utils.saveToPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.NOTIFICATION, Utils.STATUS_TRUE);
            notification_status = Utils.getFromPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.NOTIFICATION);
        }
        if(pregnancy_mode_status.equals("null")){
            Utils.saveToPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.PREGNANCY_MODE, Utils.STATUS_FALSE);
            pregnancy_mode_status= Utils.getFromPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.PREGNANCY_MODE);
        }

        setScreenWithSettings(notification_status,pregnancy_mode_status);

        settingsBinding.notificationchkState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    settingsBinding.notificationchkState.setChecked(true);
                    Utils.saveToPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.NOTIFICATION, Utils.STATUS_TRUE);
                    Toast.makeText(getActivity(), "notification on", Toast.LENGTH_SHORT).show();
                }
                else {
                    settingsBinding.notificationchkState.setChecked(false);
                    Utils.saveToPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.NOTIFICATION, Utils.STATUS_FALSE);
                    Toast.makeText(getActivity(), "notification off", Toast.LENGTH_SHORT).show();


                }
            }
        });


        settingsBinding.secretbutton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), DeveloperInfo.class));
                return true;
            }
        });

        settingsBinding.pragnentchkState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    settingsBinding.pragnentchkState.setChecked(false);
                    Utils.saveToPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.PREGNANCY_MODE, Utils.STATUS_FALSE);
                    Toast.makeText(getActivity(), "pregnancy mode off", Toast.LENGTH_SHORT).show();
                }
                else {
                    settingsBinding.pragnentchkState.setChecked(true);
                    Utils.saveToPrefs(getActivity(), Utils.SETTINGS_PREFERENCES, Utils.PREGNANCY_MODE, Utils.STATUS_TRUE);
                    Toast.makeText(getActivity(), "pregnancy mode on", Toast.LENGTH_SHORT).show();
                }


            }
        });

        settingsBinding.resetDataBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Reset Info !!!");
                alert.setMessage("Are you sure you want to reset your cycle info??");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.saveToPrefs(getActivity(), Utils.DATA_COLLECTION_PREFERENCES, Utils.DATA_COLLECTED_STATUS, Utils.STATUS_FALSE);
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_SET,"1");
                        Utils.saveToPrefs(getActivity(),Utils.DATA_COLLECTION_PREFERENCES,Utils.LOG_CLICKED,Utils.STATUS_FALSE);
                        SavedArticleDAO savedArticleDAO = new SavedArticleDAO(getActivity());
                        savedArticleDAO.deleteAllHistory();

                        Toast.makeText(getActivity(),"Data reset successfully",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("No",null);
                alert.show();

            }
        });

        settingsBinding.viewSavedArticleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                savedArticlesFragment = new SavedArticlesFragment();
                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("abc");
                fragmentTransaction.replace(R.id.child_fragment_container, savedArticlesFragment, "savedArticleFrag");
                fragmentTransaction.commit();
            }
        });
        settingsBinding.termsandconditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                termsAndConditionsFragment = new TermsAndConditionsFragment();
                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("abc");
                fragmentTransaction.replace(R.id.child_fragment_container, termsAndConditionsFragment, "termsandconditionfrag");
                fragmentTransaction.commit();
            }
        });
        settingsBinding.lisenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                lisenseFragment = new LisenseFragment();
                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("abc");
                fragmentTransaction.replace(R.id.child_fragment_container, lisenseFragment, "lisenseFrag");
                fragmentTransaction.commit();
            }
        });

        settingsBinding.historvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                historyFragment = new HistoryFragment();
                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("abc");
                fragmentTransaction.replace(R.id.child_fragment_container, historyFragment, "historyFrag");
                fragmentTransaction.commit();
            }
        });
        settingsBinding.helplineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                helpLineFragment = new HelpLineFragment();
                fragmentManager = mainActivity.fragmentManager;
                fragmentTransaction = mainActivity.fragmentTransaction;
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack("abc");
                fragmentTransaction.replace(R.id.child_fragment_container, helpLineFragment, "helpfrag");
                fragmentTransaction.commit();
            }
        });
        settingsBinding.facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/sunnydaybd/?hc_ref=ARQsaOGPG2hF7Fl0ncStlgcuKLxrnTjLwmFDh5fYcixefpSZt-zVZ80YFQOKRylui9k&fref=nf"));
                startActivity(browserIntent);
            }
        });
        settingsBinding.tvcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCe7RahM4GICcD3Llo0e1sCA"));
                startActivity(browserIntent);
            }
        });
        settingsBinding.shopnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://chaldal.com/sunnyday-superior-heavy-flow-wings-sanitary-napkin-8-pcs-buy-1-get-1"));
                startActivity(browserIntent);
            }
        });



    }
    private void setScreenWithSettings(String notification_status,String pregnancy_mode_status){
        if(notification_status.equals(Utils.STATUS_TRUE)){
            settingsBinding.notificationchkState.setChecked(true);
        }
        else {
            settingsBinding.notificationchkState.setChecked(false);
        }
        if(pregnancy_mode_status.equals(Utils.STATUS_FALSE)){
            settingsBinding.pragnentchkState.setChecked(false);
        }
        else {
            settingsBinding.pragnentchkState.setChecked(true);
        }
    }

}
