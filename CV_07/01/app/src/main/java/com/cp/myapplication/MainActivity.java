package com.cp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static String TOP_SKORE = "top_skore";

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        final TextView topSkore = (TextView) findViewById(R.id.top_text);

        topSkore.setText(String.valueOf(sharedPreferences.getInt(TOP_SKORE, 0)));

        final TextView aktualSkore = (TextView) findViewById(R.id.skore_text);

        aktualSkore.setText(String.valueOf("0"));

        final Button playButton = (Button) findViewById(R.id.hra_button);

        playButton.setOnClickListener(new View.OnClickListener()

        {

            @Override

            public void onClick(View v)

            {

                Random r = new Random();

                int skore = r.nextInt(1000);

                aktualSkore.setText(String.valueOf(skore));

                if (skore > sharedPreferences.getInt(TOP_SKORE, 0))

                {

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putInt(TOP_SKORE, skore);

                    editor.commit();

                    topSkore.setText(String.valueOf(skore));

                }

            }

        });

        final Button resetButton = (Button) findViewById(R.id.nuluj_button);

        resetButton.setOnClickListener(new View.OnClickListener()

        {

            @Override

            public void onClick(View v)

            {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt(TOP_SKORE, 0);

                editor.commit();

                topSkore.setText(String.valueOf("0"));

                aktualSkore.setText(String.valueOf("0"));

            }

        });

    }
}
