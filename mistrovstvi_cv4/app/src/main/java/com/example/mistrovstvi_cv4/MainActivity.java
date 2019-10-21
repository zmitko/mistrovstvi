package com.example.mistrovstvi_cv4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editJmeno;
    private Button btnJmeno;
    private Button btnStranka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editJmeno = findViewById(R.id.edit_jmeno);
        btnJmeno = findViewById(R.id.btn_jmeno);

        btnJmeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StrankaSeJmenemActivity.class);
                intent.putExtra("jmeno", editJmeno.getText().toString());
                startActivity(intent);
            }
        });

        btnStranka = findViewById(R.id.btn_stranka);
        btnStranka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.albatrosmedia.cz"));
                startActivity(intent);
            }
        });
    }
}
