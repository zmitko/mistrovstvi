package com.example.ukol1_vyhledavac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchBySeznam(View view) {
        Intent iSeznam = new Intent(this, SeznamActivity.class);
        final int result = 1;
        iSeznam.putExtra("calling", "MainActivity");
        startActivityForResult(iSeznam, result);

    }

    public void searchByGoogle(View view) {
        Intent iGoogle = new Intent(this, GoogleActivity.class);
        final int result = 1;
        iGoogle.putExtra("calling", "MainActivity");
        startActivityForResult(iGoogle, result);
    }
}
