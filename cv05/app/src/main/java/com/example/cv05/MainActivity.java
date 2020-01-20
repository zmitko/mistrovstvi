package com.example.cv05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout lLayout =
                (LinearLayout) findViewById(R.id.activity_main);
        lLayout.setOnTouchListener(
                new LinearLayout.OnTouchListener() {
                    public boolean onTouch(View v,
                                           MotionEvent me) {
                        ObsluhaDotyku(me);
                        return true;
                    }
                }
        );
    }

    void ObsluhaDotyku(MotionEvent me) {
        TextView tw1 = (TextView) findViewById(R.id.tw1);
        TextView tw2 = (TextView) findViewById(R.id.tw2);
        int pointerCount = me.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int x = (int) me.getX(i);
            int y = (int) me.getY(i);
            int id = me.getPointerId(i);
            int akcia = me.getActionMasked();
            int nAI = me.getActionIndex();
            String sAkcia;
            switch (akcia) {
                case MotionEvent.ACTION_DOWN:
                    sAkcia = "DOWN";
                    break;
                case MotionEvent.ACTION_UP:
                    sAkcia = "UP";
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    sAkcia = "PNTR DOWN";
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    sAkcia = "PNTR UP";
                    break;
                case MotionEvent.ACTION_MOVE:
                    sAkcia = "MOVE";
                    break;
                default:
                    sAkcia = "";
            }
            String sStav = "Akcia: " + sAkcia + " Index: " + nAI + " ID: " + id +
                    " X: " + x + " Y: " + y;
            if (id == 0) tw1.setText(sStav);
            else tw2.setText(sStav);
        }
    }
}

