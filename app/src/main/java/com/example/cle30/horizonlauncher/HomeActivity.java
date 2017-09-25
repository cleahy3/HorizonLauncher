package com.example.cle30.horizonlauncher;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.app.Activity;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void showApps(View v) {
        Intent i = new Intent(this, AppsListActivity.class);
        startActivity(i);
    }

}
