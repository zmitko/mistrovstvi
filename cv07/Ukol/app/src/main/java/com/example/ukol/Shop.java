package com.example.ukol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Shop extends AppCompatActivity {
    private static String SKORE = "skore";
    private static String SUPERKLIK = "superklik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        final SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", 0);
        //final Integer skore = sharedPreferences.getInt(SKORE, 0);
        //final Integer superKlik = sharedPreferences.getInt(SUPERKLIK, 0);

        //Bundle bundle = getIntent().getExtras();
        //final String skore = bundle.getString("skore");
        //final String superKlik = bundle.getString("superklik");

        final TextView txtSkore2 = (TextView) findViewById(R.id.top_label2);
        txtSkore2.setText("Vítejte v obchodě! Momentálně vlastníte " + sharedPreferences.getInt(SKORE, 0) + " doríto chipsů.");

        Button next = (Button) findViewById(R.id.BackButton);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent();
                //Bundle b = new Bundle();
                //b.putString("skore", skore);
                //b.putString("superklik", superKlik);
                //myIntent.putExtras(b);
                setResult(RESULT_OK, myIntent);
                finish();
            }
        });

        Button buySK = (Button) findViewById(R.id.BuySuperKlik);
        buySK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Integer skore = sharedPreferences.getInt(SKORE, 0);
                Integer superKlik = sharedPreferences.getInt(SUPERKLIK, 0);
                if (skore > 9) {
                    //skore = String.valueOf(Integer.parseInt(skore) - 10);
                    //superKlik = String.valueOf(Integer.parseInt(superKlik) + 1);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(SKORE, skore - 10);
                    editor.putInt(SUPERKLIK, superKlik + 1);
                    editor.commit();
                    skore = sharedPreferences.getInt(SKORE, 0);
                    txtSkore2.setText("Ajaj! Už máte jen " + skore + " doríto chipsů. :(");
                } else {
                    new AlertDialog.Builder(Shop.this)
                            .setTitle("Nedostatek Chipsů")
                            .setMessage("Nemáte dostatek potřebných Dorito chipsů. Cena upgradu je 10 chipsů.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Whatever...
                                }
                            }).show();
                }
            }
        });
    }
}
