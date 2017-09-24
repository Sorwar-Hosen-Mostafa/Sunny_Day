package com.sunny.sunnyday;


import android.widget.Toast;

import me.tatarka.support.job.JobParameters;

/**
 * Created by Jibunnisa on 9/16/2017.
 */

public class MyJobService extends me.tatarka.support.job.JobService{
    @Override
    public boolean onStartJob(JobParameters params) {

        Toast.makeText(this,"job started",Toast.LENGTH_SHORT).show();
//        switch (params.getJobId()){
//            case 1:
//                Toast.makeText(this, "1st", Toast.LENGTH_SHORT).show();
//                break;
//            case 2:
//                Toast.makeText(this, "2nd", Toast.LENGTH_SHORT).show();
//                break;
//            case 3:
//                Toast.makeText(this, "3rd", Toast.LENGTH_SHORT).show();
//                break;
//            case 4:
//                Toast.makeText(this, "4rt", Toast.LENGTH_SHORT).show();
//                break;
//            case 5:
//                Toast.makeText(this, "5th", Toast.LENGTH_SHORT).show();
//                break;
//            case 6:
//                Toast.makeText(this, "6th", Toast.LENGTH_SHORT).show();
//                break;
//            case 7:
//                Toast.makeText(this, "7th", Toast.LENGTH_SHORT).show();
//                break;
//            case 11:
//                Toast.makeText(this, "pst", Toast.LENGTH_SHORT).show();
//                break;
//            case 12:
//                Toast.makeText(this, "pst", Toast.LENGTH_SHORT).show();
//                break;
//            case 13:
//                Toast.makeText(this, "pst", Toast.LENGTH_SHORT).show();
//                break;
//            case 14:
//                Toast.makeText(this, "pst", Toast.LENGTH_SHORT).show();
//                break;
//            case 15:
//                Toast.makeText(this, "pst", Toast.LENGTH_SHORT).show();
//                break;
//        }

        return false;

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
