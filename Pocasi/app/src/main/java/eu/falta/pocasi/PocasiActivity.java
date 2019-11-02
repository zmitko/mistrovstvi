package eu.falta.pocasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PocasiActivity extends AppCompatActivity {
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocasi);
        Intent received = getIntent();
        Bundle extras = received.getExtras();
        if (extras == null || !extras.containsKey("name") || !extras.containsKey("url")) {
            finishActivity(0);
            return;
        }
        url = extras.getString("url");

        TextView chosen = findViewById(R.id.textChosen);
        String name = extras.getString("name");
        chosen.setText(name);

        // Potvrzení už při zadávání města
        final Button confirm = findViewById(R.id.Btn_Search);
        EditText txtCity = findViewById(R.id.Txt_City);
        txtCity.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    confirm.performClick();
                    return true;
                }
                return false;
            }
        });

    }

    public void search_onClick(View v) {
        EditText search = findViewById(R.id.Txt_City);
        String city = search.getText().toString();
        if (city.isEmpty()) {
            return;
        }
        Uri uri = Uri.parse(url + city);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void back_onClick(View v) {
        finish();
    }

}
