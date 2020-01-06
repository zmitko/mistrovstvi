package com.example.animace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private AnimationDrawable anim;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.animovany_obrazek);
        imageView.setBackgroundResource(R.drawable.animace);
        anim = (AnimationDrawable) imageView.getBackground();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (anim.isRunning()) anim.stop();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) anim.start();
    }
}
