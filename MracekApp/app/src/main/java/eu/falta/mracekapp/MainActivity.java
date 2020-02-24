package eu.falta.mracekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bData = getIntent().getExtras();

        if (bData != null) {
            TextView textView = (TextView) findViewById(R.id.tw);
            textView.setText(bData.getString("Miesto"));
        }

    }
}
