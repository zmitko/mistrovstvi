package com.example.aktivityaintenty;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view) {
        Intent iJmeno = new Intent(this, SecondActivity.class);
// očakávame že aktivita vrátí hodnotu
        final int result = 1;
        iJmeno.putExtra("volajiciAktivita", "MainActivity");
        startActivityForResult(iJmeno, result);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView tvJmeno = (TextView) findViewById(R.id.jmeno_od_volane_aktivity);
        String sJmeno = data.getStringExtra("ZadaneJmeno");
        tvJmeno.setText("Ahoj, " + sJmeno);
    }
}

