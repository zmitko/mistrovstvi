package com.studio.tiny.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private static final int INTERVAL = 500;
    private SensorManager sensorManager;
    private Sensor akcelerometr;
    private TextView xHodnota, yHodnota, zHodnota;
    private long posledniAktualizace;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xHodnota = (TextView) findViewById(R.id.x_hodnota);
        yHodnota = (TextView) findViewById(R.id.y_hodnota);
        zHodnota = (TextView) findViewById(R.id.z_hodnota);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if ((akcelerometr = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) == null)
            finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, akcelerometr,
                SensorManager.SENSOR_DELAY_UI);
        posledniAktualizace = System.currentTimeMillis();
    }
    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long actualTime = System.currentTimeMillis();
            if (actualTime - posledniAktualizace > INTERVAL) {
                posledniAktualizace = actualTime;
                float x = event.values[0], y = event.values[1], z = event.values[2];
                xHodnota.setText(String.valueOf(x));
                yHodnota.setText(String.valueOf(y));
                zHodnota.setText(String.valueOf(z));
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}

