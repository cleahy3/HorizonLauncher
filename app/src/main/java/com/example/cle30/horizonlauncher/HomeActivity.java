package com.example.cle30.horizonlauncher;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.Locale;


public class HomeActivity extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.UK);
        simpleDateFormat.setTimeZone(timeZone);
        String time = simpleDateFormat.format(calendar.getTime());
        System.out.println(time);

        try {
            final java.util.Date currentTime = simpleDateFormat.parse(time);
            final java.util.Date startTime = simpleDateFormat.parse("09:00");
            final java.util.Date endTime = simpleDateFormat.parse("17:00");
            if (currentTime.after(startTime)&& currentTime.before(endTime)) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_second);
            }else{
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_home);
            }
        } catch (ParseException e) {
            System.out.println("you done goofed");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void showApps(View v) {
        Intent i = new Intent(this, AppsListActivity.class);
        startActivity(i);
    }



}
