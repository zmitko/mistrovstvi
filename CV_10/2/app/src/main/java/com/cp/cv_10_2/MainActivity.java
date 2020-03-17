package com.cp.cv_10_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements SensorEventListener {
    private RelativeLayout layout;
    private CifernikView cifernik;
    private SensorManager sm;
    private Sensor aMeter;
    private Sensor mMeter;
    private float[] zrychleni = null;
    private float[] magnet = null;
    private double natoceniStupne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.frame);
        cifernik = new CifernikView(getApplicationContext());
        layout.addView(cifernik);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        aMeter = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMeter = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (aMeter == null || mMeter == null) finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, aMeter, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, mMeter, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            zrychleni = new float[3];
            System.arraycopy(event.values, 0, zrychleni, 0, 3);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnet = new float[3];
            System.arraycopy(event.values, 0, magnet, 0, 3);
        }
        if (zrychleni != null && magnet != null) {
            float rotMatrix[] = new float[9];
            boolean bOK = SensorManager.getRotationMatrix(rotMatrix,
                    null, zrychleni, magnet);
            if (bOK) {
                float orientMatrix[] = new float[3];
                SensorManager.getOrientation(rotMatrix, orientMatrix);
                float radiany = orientMatrix[0];
                natoceniStupne = Math.toDegrees(radiany);
                cifernik.invalidate();
                zrychleni = magnet = null;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public class CifernikView extends View {
        int sirka, vyska, stredX, stredY, topX, leftY;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.kompas1);
        int bmpSirka = bmp.getWidth();
        int bmpVyska = bmp.getHeight();

        public CifernikView(Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            sirka = layout.getWidth();
            vyska = layout.getHeight();
            stredX = sirka / 2;
            stredY = vyska / 2;
            leftY = stredX - bmpSirka / 2;
            topX = stredY - bmpVyska / 2;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.save();
            canvas.rotate((float) -natoceniStupne, stredX, stredY);
            canvas.drawBitmap(bmp, leftY, topX, null);
            canvas.restore();
        }
    }

}



