package com.example.cyklusaktivity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class StateChangeActivity extends AppCompatActivity {
    private static final String LAD = "ZmenaStavu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// skry bočný panel
        int screenOrientation = getResources().getConfiguration().orientation;
        if (screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            View sidePane = findViewById(R.id.bocny_panel);
            if (sidePane.getVisibility() == View.VISIBLE) {
                sidePane.setVisibility(View.GONE);
            }
        }
    }
}
