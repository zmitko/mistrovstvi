package com.example.explicitniintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View view) {
        Intent iMeno = new Intent(this, Second.class);
// očakávame že aktivita vrátihodnotu
        final int result = 1;
        iMeno.putExtra("volajúcaAktivita", "MainActivity");
        startActivityForResult(iMeno, result);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView tvMeno = (TextView) findViewById(R.id.meno_od_volanej_aktivity);
        String sMeno = data.getStringExtra("ZadaneMeno");
        tvMeno.append(" " + sMeno);
    }
}
