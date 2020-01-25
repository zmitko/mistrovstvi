package com.example.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int hlasitost = 10, max = 10, min = 0;
    private SoundPool soundPool;
    private int id;
    private AudioManager audioManager;
    private boolean bPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setText(String.valueOf(hlasitost));
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        final Button upButton = (Button) findViewById(R.id.up);
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
                if (hlasitost < max) {
                    hlasitost++;
                    tv.setText(String.valueOf(hlasitost));
                }
            }
        });
        final Button downButton = (Button) findViewById(R.id.down);
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
                if (hlasitost > min) {
                    hlasitost--;
                    tv.setText(String.valueOf(hlasitost));
                }
            }
        });
        final Button playButton = (Button) findViewById(R.id.play);
        playButton.setEnabled(false);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        id = soundPool.load(this, R.raw.zvuk, 1);
        soundPool.setOnLoadCompleteListener(
                new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId,
                                               int status) {
                        if (status == 0) playButton.setEnabled(true);
                    }
                });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bPlay)
                    soundPool.play(id, (float) hlasitost / max,
                            (float) hlasitost / max, 1, 0, 1.0f);
            }
        });
        int result = audioManager.requestAudioFocus(afChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        bPlay = AudioManager.AUDIOFOCUS_REQUEST_GRANTED == result;
    }
    @Override
    protected void onResume() {
        super.onResume();
        audioManager.setSpeakerphoneOn(true);
        audioManager.loadSoundEffects();
    }
    @Override
    protected void onPause() {
        if (soundPool != null) {
            soundPool.unload(id);
            soundPool.release();
            soundPool = null;
        }
        audioManager.setSpeakerphoneOn(false);
        audioManager.unloadSoundEffects();
        super.onPause();
    }
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        audioManager.abandonAudioFocus(afChangeListener);
                        bPlay = false;
                    }
                }
            };
}

