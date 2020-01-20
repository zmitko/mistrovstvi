package com.example.nahravanividea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int VIDEO_CAPTURE = 101;
    private Uri fileUri;
    private static final String AUTHORITY = "sk.pcrevue.llacko.kamera";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button recordButton =
                (Button) findViewById(R.id.btZaznam);
        if (!jeKamera())
            recordButton.setEnabled(false);
    }
    private boolean jeKamera() {
        return (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY));
    }
    public void onClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        Uri videoUri = data.getData();
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video uložené do :\n" +
                        videoUri, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Nahrávanie prerušené.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Chyba",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}

