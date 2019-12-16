package cz.rehamza.arboretum_client;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class SensorEventListener implements android.hardware.SensorEventListener {
    MainActivity mainActivity;
    ImageView imgNeedle;
    float bearingDegrees;
    float directionDegrees;
    float result;

    public SensorEventListener(MainActivity mainActivity, ImageView img_needle){
        this.mainActivity = mainActivity;
        this.imgNeedle = img_needle;
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        bearingDegrees = mainActivity.lastLocation.bearingTo(mainActivity.tempTreeLocation);
        directionDegrees = event.values[0];

        if(bearingDegrees > directionDegrees){
            result = bearingDegrees - directionDegrees;
            imgNeedle.setRotation((int)result);
        }
        else{
            result = directionDegrees - bearingDegrees;
            result = 360 - result;
            imgNeedle.setRotation((int)result);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}
