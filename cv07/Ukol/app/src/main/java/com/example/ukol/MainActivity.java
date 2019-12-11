package com.example.ukol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageButton;

public class MainActivity extends AppCompatActivity {
    private static String SKORE = "skore";
    private static String SUPERKLIK = "superklik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        //https://stackoverflow.com/questions/6186123/android-how-do-i-get-sharedpreferences-from-another-activity
        final SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", 0);
        final TextView txtSkore = (TextView) findViewById(R.id.top_text);
        txtSkore.setText(String.valueOf(sharedPreferences.getInt(SKORE, 0)));
        final GifImageButton playButton = (GifImageButton) findViewById(R.id.cookie_button);

        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //int skore = Integer.parseInt(txtSkore.getText().toString()) + 1;
                int skore = sharedPreferences.getInt(SKORE, 0) + 1;
                int superKlik = sharedPreferences.getInt(SUPERKLIK, 0);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SKORE, skore + superKlik);
                editor.commit();
                txtSkore.setText(String.valueOf(skore));
            }
        });

        final Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SKORE, 0);
                editor.putInt(SUPERKLIK, 0);
                editor.commit();
                txtSkore.setText(String.valueOf("0"));
            }
        });

        Button next = (Button) findViewById(R.id.shop_button);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Shop.class);
                //Bundle b = new Bundle();
                //b.putString("skore", txtSkore.getText().toString());
                //b.putString("superklik", String.valueOf(sharedPreferences.getInt(SUPERKLIK, 0)));
                //myIntent.putExtras(b);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
