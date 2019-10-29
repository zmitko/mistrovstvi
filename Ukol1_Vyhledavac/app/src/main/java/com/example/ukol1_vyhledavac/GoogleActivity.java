package com.example.ukol1_vyhledavac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class GoogleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
    }

    public void GoogleWordSearch(View view) {
        EditText searchBar = findViewById(R.id.searchGoogle);
        String searchTerm = String.valueOf(searchBar.getText());
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.google.cz/search?&q=" + searchTerm));
        startActivity(intent);

    }

    public void fuckGoBack(View view) {
        Intent iBack = new Intent(this, MainActivity.class);
        final int result = 1;
        startActivityForResult(iBack, result);
    }
}
