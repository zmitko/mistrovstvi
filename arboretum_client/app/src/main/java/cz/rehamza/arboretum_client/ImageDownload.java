package cz.rehamza.arboretum_client;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import cz.rehamza.arboretum_client.MainActivity;

public class ImageDownload extends AsyncTask<String, String, String> {
    private MainActivity mainActivity;

    public ImageDownload(MainActivity mainA) {
        super();
        this.mainActivity = mainA;
    }

    @Override
    protected String doInBackground(String... input) {
        String url = input[0];
        String imgPath = input[1];

        Log.d("MyLog", url);
        Log.d("MyLog", imgPath);

        try {
            URL u = new URL(url);
            InputStream is = u.openStream();

            DataInputStream dis = new DataInputStream(is);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(imgPath));
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

        } catch (MalformedURLException mue) {
            Log.e("SYNC getUpdate", "malformed url error", mue);
        } catch (IOException ioe) {
            Log.e("SYNC getUpdate", "io error", ioe);
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
        }

        //Like this?
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mainActivity.updateProgress();
    }
}