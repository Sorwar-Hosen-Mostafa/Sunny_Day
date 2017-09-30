package com.sunny.sunnyday;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


public class Utils {

    public static final String DATA_COLLECTION_PREFERENCES = "dataprefs";
    public static final String SETTINGS_PREFERENCES = "settingsprefs";
    public static final String FIRST_TIME_RUNS_APP = "firsttimerunsapp";
    public static final String FIRST_TIME_OPEN_APP = "firsttimeopensapp";



    public static final String LOG_FUNCTION = "log_function";
    public static final String NOTIFICATION = "notification";
    public static final String FROM_NOTIFICATION = "from_notification";
    public static final String PREGNANCY_MODE = "pregnancy_mode";

    public final static String USER_DEVICE_ID = "user_device_id";

    public final static String USER_WANT_TO_PREGNANT_STATUS = "user_wants_to_pregnant";
    public final static String USER_LAST_PERIOD_DATE = "user_last_period_date";
    public final static String USER_LAST_PERIOD_MONTH = "user_last_period_month";
    public final static String USER_LAST_PERIOD_YEAR = "user_last_period_year";
    public final static String USER_PERIOD_DURATION = "user_period_duration";
    public final static String USER_CYCLE_LENGTH = "user_cycle_length";
    public final static String USER_BIRTH_YEAR= "user_birth_year";

    public final static String DATA_COLLECTED_STATUS = "user_completedDataCollection";

    public final static String STATUS_FALSE ="false";
    public final static String STATUS_TRUE = "true";

    public final static String FIRE_ARTICLE = "article";
    public final static String FIRE_ARTICLE_CATEGORY = "article_Category";


    public final static String FIRE_ARTICLE_CATEGORY_HEATHANDFITNESS = "health and hygiene";
    public final static String FIRE_ARTICLE_CATEGORY_TRENDING_NOW = "trending now";
    public final static String FIRE_ARTICLE_CATEFORY_WHATS_IN_WHATS_OUT = "whats in whats out";
    public final static String FIRE_ARTICLE_CATEGORY_HUMOR_OF_THE_DAY = "humor of the day";

    public static void saveToPrefs(Context context,String prefsname, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(prefsname,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getFromPrefs(Context context,String prefsname, String key) {
        SharedPreferences prefs = context.getSharedPreferences(prefsname,context.MODE_PRIVATE);
        try {
            return prefs.getString(key, "null");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            return "null";
        }
    }


}
