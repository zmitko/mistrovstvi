package eu.falta.cviceni5_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    private TextView tw1;
    private GestureDetectorCompat gDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tw1 = (TextView) findViewById(R.id.tw1);
        this.gDet = new GestureDetectorCompat(this, this);
        gDet.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        this.tw1.onTouchEvent(e);
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        tw1.setText("onDown");
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        tw1.setText("onFling");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        tw1.setText("onLongPress");
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {
        tw1.setText("onScroll");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        tw1.setText("onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        tw1.setText("onSingleTapUp");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        tw1.setText("onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        tw1.setText("onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        tw1.setText("onSingleTapConfirmed");
        return true;
    }

}
