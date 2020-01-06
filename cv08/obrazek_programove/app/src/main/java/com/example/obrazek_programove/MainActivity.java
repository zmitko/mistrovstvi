package com.example.obrazek_programove;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout relativeLayout = (RelativeLayout)
                findViewById(R.id.activity_main);
        ImageView imgView = new ImageView(getApplicationContext());
        imgView.setImageDrawable(getResources().getDrawable(R.drawable.obr));
        int width = (int) getResources().getDimension(R.dimen.image_width);
        int height = (int) getResources().getDimension(R.dimen.image_height);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        imgView.setLayoutParams(params);
        relativeLayout.addView(imgView);
    }
}
