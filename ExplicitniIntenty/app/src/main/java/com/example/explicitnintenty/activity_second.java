package com.example.explicitnintenty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class activity_second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //intent ktorý otvoril túto aktivitu
        Intent iVolajucejAktivity = getIntent();
// načítanie odoslaných údajov
        String previousActivity =
                iVolajucejAktivity.getExtras().getString("volajúcaAktivita");

        TextView callingActivityMessage = (TextView)
                findViewById(R.id.sprava_od_aktivity);
        callingActivityMessage.append(" " + previousActivity);

    }

    public void onPosliMeno(View view) {
// mano z prvku EditText
        EditText usersNameET = (EditText) findViewById(R.id.zadane_meno);
        String usersName = String.valueOf(usersNameET.getText());
// intent pre návrat hodnoty volajúcej aktivite
        Intent goingBack = new Intent();
        goingBack.putExtra("ZadaneMeno", usersName);
        setResult(RESULT_OK, goingBack);
        finish();
    }

}
