package com.example.obrazek_internet;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    public static final String sURL = "https://pizzaburgermalse.cz/wp-content/uploads/2018/08/obrazek-pripravujeme-300x150.png";
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView1);
        WebovyObrazek uloha = new WebovyObrazek();
        uloha.execute(new String[] {
                sURL
        });
    }
    private class WebovyObrazek extends AsyncTask< String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String...urls) {
            Bitmap bmp = null;
            for (String url: urls) {
                bmp = nactiBmp(url);
            }
            return bmp;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
        private Bitmap nactiBmp(String url) {
            Bitmap bmp = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
            try {
                stream = vytvorStream(url);
                bmp = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bmp;
        }
        private InputStream vytvorStream(String urlString) throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            try {
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                httpCon.connect();

                if (httpCon.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpCon.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }
}
