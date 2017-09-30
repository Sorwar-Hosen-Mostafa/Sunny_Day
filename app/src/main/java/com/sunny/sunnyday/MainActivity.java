package com.sunny.sunnyday;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sunny.sunnyday.DataCollectionFragments.FragmentOne;
import com.sunny.sunnyday.Fragments.ArticleReadViewFragment;
import com.sunny.sunnyday.Fragments.HealthAndFitnessFragment;
import com.sunny.sunnyday.Fragments.HumorOfTheDayFragment;
import com.sunny.sunnyday.Fragments.PeriodCalenderFragment;
import com.sunny.sunnyday.Fragments.SettingsFragment;
import com.sunny.sunnyday.Fragments.TrendingNowFragmet;
import com.sunny.sunnyday.Fragments.WhatsInWhatsOutFragment;
import com.sunny.sunnyday.Model.Article;
import com.sunny.sunnyday.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.UUID;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {


    LinearLayout parentlayout;
    ArrayList<String> saved_articles_final;
    String device_id;
    DatabaseReference databaseReference;
    public static int REQUEST_READ_PHONE_STATE = 1;
    private ActivityMainBinding activityMainBinding;
    private HealthAndFitnessFragment healthAndFitnessFragment;
    private PeriodCalenderFragment periodCalenderFragment;
    private SettingsFragment settingsFragment;
    public FragmentManager fragmentManager;
    public ArrayList<String> savedarticle;
    public ArrayList<String> savedarticlefinel;
    public FragmentTransaction fragmentTransaction;
    private HumorOfTheDayFragment humorOfTheDayFragment;
    private WhatsInWhatsOutFragment whatsInWhatsOutFragment;
    private TrendingNowFragmet trendingNowFragmet;
    private FragmentOne fragmentOne;
    public boolean fromsavedarticle = false;

    private Article article;
    int size;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        savedarticle = new ArrayList<>();
        savedarticlefinel = new ArrayList<>();


        Utils.saveToPrefs(MainActivity.this,Utils.DATA_COLLECTION_PREFERENCES,Utils.FIRST_TIME_OPEN_APP,Utils.STATUS_TRUE);


        Utils.saveToPrefs(this, Utils.DATA_COLLECTION_PREFERENCES, Utils.FROM_NOTIFICATION, Utils.STATUS_FALSE);
        parentlayout = (LinearLayout) findViewById(R.id.parentlayout);

        saved_articles_final = new ArrayList<String>();

        device_id = Utils.getFromPrefs(MainActivity.this, Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_DEVICE_ID);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        healthAndFitnessFragment = new HealthAndFitnessFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        String menuFragment = getIntent().getStringExtra("menuFragment");

        if (menuFragment != null) {

            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (menuFragment.equals("periodCalendar")) {

                    Utils.saveToPrefs(MainActivity.this,Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION,Utils.STATUS_TRUE);
                    PeriodCalenderFragment periodCalenderFragment = new PeriodCalenderFragment();
                    fragmentTransaction.replace(R.id.child_fragment_container, periodCalenderFragment, "periodCalendarFrag");
                    fragmentTransaction.addToBackStack("eww");
                    fragmentTransaction.commit();
                    activityMainBinding.bottomNevigationView.setSelectedItemId(R.id.PeriodCalanderMenuItem);
                    Utils.saveToPrefs(MainActivity.this,Utils.DATA_COLLECTION_PREFERENCES,Utils.FIRST_TIME_OPEN_APP,Utils.STATUS_FALSE);


            }
        } else {

            Utils.saveToPrefs(MainActivity.this,Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION,Utils.STATUS_FALSE);
            fragmentTransaction.replace(R.id.child_fragment_container, healthAndFitnessFragment, "healthAndFitnessFrag");
            fragmentTransaction.commit();

        }


        activityMainBinding.ProgramsTabLayout.addTab(activityMainBinding.ProgramsTabLayout.newTab().setText("Humor \nof the day"));
        activityMainBinding.ProgramsTabLayout.addTab(activityMainBinding.ProgramsTabLayout.newTab().setText("Trending \nnow"));
        activityMainBinding.ProgramsTabLayout.addTab(activityMainBinding.ProgramsTabLayout.newTab().setText("What's in\nWhat's out"));
        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#4e92cf"));


        size = activityMainBinding.ProgramsTabLayout.getTabCount();

        for (int i = 0; i < size; i++) {
            TextView tv = (TextView) (((LinearLayout) ((LinearLayout) activityMainBinding.ProgramsTabLayout.getChildAt(0)).getChildAt(i)).getChildAt(1));
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(1, 11);
        }


//        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
//
//
//            ContinueProcess();
//
//
//        } else {
//            askForPermission(android.Manifest.permission.READ_PHONE_STATE, REQUEST_READ_PHONE_STATE);
//        }


        activityMainBinding.ProgramsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                switch (tab.getPosition()) {
                    case 0:

                        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                        humorOfTheDayFragment = new HumorOfTheDayFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.child_fragment_container, humorOfTheDayFragment, "humorOfTheDayFrag");
                        fragmentTransaction.commit();
                        break;
                    case 1:

                        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                        trendingNowFragmet = new TrendingNowFragmet();
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack("eww");
                        fragmentTransaction.replace(R.id.child_fragment_container, trendingNowFragmet, "trendingNowFrag");
                        fragmentTransaction.commit();
                        break;
                    case 2:

                        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                        whatsInWhatsOutFragment = new WhatsInWhatsOutFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack("eww");
                        fragmentTransaction.replace(R.id.child_fragment_container, whatsInWhatsOutFragment, "whatsInWhatsOutFrag");
                        fragmentTransaction.commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


                switch (tab.getPosition()) {
                    case 0:

                        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                        humorOfTheDayFragment = new HumorOfTheDayFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack("eww");
                        fragmentTransaction.replace(R.id.child_fragment_container, humorOfTheDayFragment, "humorOfTheDayFrag");
                        fragmentTransaction.commit();
                        break;
                    case 1:

                        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                        trendingNowFragmet = new TrendingNowFragmet();
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack("eww");
                        fragmentTransaction.replace(R.id.child_fragment_container, trendingNowFragmet, "trendingNowFrag");
                        fragmentTransaction.commit();
                        break;
                    case 2:

                        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
                        whatsInWhatsOutFragment = new WhatsInWhatsOutFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.addToBackStack("eww");
                        fragmentTransaction.replace(R.id.child_fragment_container, whatsInWhatsOutFragment, "whatsInWhatsOutFrag");
                        fragmentTransaction.commit();
                        break;
                }
            }
        });


        activityMainBinding.bottomNevigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.healthAndHygieneMenuItem:
                        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#4e92cf"));
                        healthAndFitnessFragment = new HealthAndFitnessFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.child_fragment_container, healthAndFitnessFragment, "healthAndFitnessFrag");
                        fragmentTransaction.addToBackStack("eww");
                        fragmentTransaction.commit();
                        return true;
                    case R.id.PeriodCalanderMenuItem:
                        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#4e92cf"));
                        String status = Utils.getFromPrefs(MainActivity.this, Utils.DATA_COLLECTION_PREFERENCES, Utils.DATA_COLLECTED_STATUS);
                        if (status.equals("false") || status.equals("null")) {
                            fragmentOne = new FragmentOne();
                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.child_fragment_container, fragmentOne, "fragmentOne");
                            fragmentTransaction.addToBackStack("eww");
                            fragmentTransaction.commit();

                        } else {

                            if(Utils.getFromPrefs(MainActivity.this,Utils.DATA_COLLECTION_PREFERENCES,Utils.FIRST_TIME_OPEN_APP).equals(Utils.STATUS_TRUE)){
                             //   Utils.saveToPrefs(MainActivity.this,Utils.DATA_COLLECTION_PREFERENCES,Utils.FROM_NOTIFICATION,Utils.STATUS_FALSE);
                                settingsFragment = new SettingsFragment();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.child_fragment_container, settingsFragment, "settingsFrag");
                                fragmentTransaction.addToBackStack("eww");
                                fragmentTransaction.commit();
                                activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#4e92cf"));
                                periodCalenderFragment = new PeriodCalenderFragment();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.addToBackStack("eww");
                                fragmentTransaction.replace(R.id.child_fragment_container, periodCalenderFragment, "periodCalenderFrag");
                                fragmentTransaction.commit();
                                Utils.saveToPrefs(MainActivity.this,Utils.DATA_COLLECTION_PREFERENCES,Utils.FIRST_TIME_OPEN_APP,Utils.STATUS_FALSE);
                            }
                            else {
                                activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#4e92cf"));
                                periodCalenderFragment = new PeriodCalenderFragment();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.addToBackStack("eww");
                                fragmentTransaction.replace(R.id.child_fragment_container, periodCalenderFragment, "periodCalenderFrag");
                                fragmentTransaction.commit();
                            }

                        }


                        return true;
                    case R.id.SettingsMenuItem:
                        activityMainBinding.ProgramsTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#4e92cf"));

                        settingsFragment = new SettingsFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.child_fragment_container, settingsFragment, "settingsFrag");
                        fragmentTransaction.addToBackStack("eww");
                        fragmentTransaction.commit();
                        return true;

                }
                return false;
            }
        });

    }

    public void articleview() {

        ArticleReadViewFragment articleReadViewFragment = new ArticleReadViewFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, articleReadViewFragment, "articleReadViewFrag");
        fragmentTransaction.addToBackStack("xyz");
        fragmentTransaction.commit();
    }

    public void setArticleViewData(Article article) {
        this.article = article;
    }

    public Article getArticleViewData() {
        return article;
    }

    private String getuserdeviceID() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(MainActivity.this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        return deviceId;
    }

//    private void askForPermission(String permission, Integer requestCode) {
//        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
//
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
//
//            } else {
//
//                Log.d(TAG, "askForPermission: " + permission);
//
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
//            }
//        } else {
//            Toast.makeText(MainActivity.this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        Log.d(TAG, "onRequestPermissionsResult: " + requestCode);
//
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    Log.d(TAG, "onRequestPermissionsResult: request");
//
//                    ContinueProcess();
//
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                        finishAffinity();
//                    } else {
//                        finish();
//                    }
//                }
//                break;
//
//        }
//    }

//    private void ContinueProcess() {
//
//        Utils.saveToPrefs(MainActivity.this, Utils.DATA_COLLECTION_PREFERENCES, Utils.USER_DEVICE_ID, getuserdeviceID());
//
//    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
