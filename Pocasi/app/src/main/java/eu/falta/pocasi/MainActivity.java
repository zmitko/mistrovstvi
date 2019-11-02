package eu.falta.pocasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        String btnTag = v.getTag().toString();
        String url = "";
        String name = "";
        switch (btnTag) {
            case "yr":
                url = "https://www.yr.no/en/search?q=";
                name = "Yr.no";
                break;
            case "open":
                url = "https://openweathermap.org/find?q=";
                name = "OpenWeather";
                break;
        }
        Intent intent = new Intent(this, PocasiActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("name", name);
        startActivityForResult(intent, 1);
    }
}
