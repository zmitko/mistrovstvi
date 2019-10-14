package com.example.master_detail;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public class DetailsActivity extends FragmentActivity {
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
            this.getSupportFragmentManager().beginTransaction().add(android.R.id.content,
                    details).commit();
        }
    }
}
