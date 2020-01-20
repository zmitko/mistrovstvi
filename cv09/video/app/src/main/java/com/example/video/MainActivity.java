package com.example.video;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    VideoView videoView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) findViewById(R.id.prehravac);
        final MediaController mediaController =
                new MediaController(MainActivity.this, true);
        mediaController.setEnabled(false);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("https://www.sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4"));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaController.setEnabled(true);
            }
        });
    }
    @Override protected void onPause() {
        if (videoView != null && videoView.isPlaying()) {
            videoView.stopPlayback();
            videoView = null;
        }
        super.onPause();
    }
}

