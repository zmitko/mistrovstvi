package com.example.navrhui4;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class DetailsActivity extends FragmentActivity { //TODO TIP: API 21 must be
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //je orientácia na šírku? vtedy túto aktivitu netreba
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            DetailFragment details = new DetailFragment();
            // odovzdanie indexu vybranej polozky
            details.setArguments(getIntent().getExtras());

            //TODO TIP: API 21 depricated use this:

            //getFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
            this.getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();

        }
    }
}

