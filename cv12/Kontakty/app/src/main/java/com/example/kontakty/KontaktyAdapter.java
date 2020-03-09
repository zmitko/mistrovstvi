package com.example.kontakty;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class KontaktyAdapter extends ResourceCursorAdapter {
    private Bitmap fakeBmp;
    public KontaktyAdapter(Context ctx, int layout, Cursor c, int flags) {
        super(ctx, layout, c, flags);
        fakeBmp = BitmapFactory.decodeResource(ctx.getResources(),
                R.drawable.user);
    }
    @Override
    public View newView(Context ctx, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.kontakt, parent, false);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.meno);
        textView.setText(cursor.getString(cursor
                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        ImageView imageView = (ImageView) view.findViewById(R.id.foto);
        Bitmap bmp = fakeBmp;
        String obrUri = cursor.getString(cursor
                .getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
        if (obrUri != null) {
            InputStream is = null;
            try {
                is = context.getContentResolver().openInputStream(
                        Uri.parse(obrUri));
                if (is != null) bmp = BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                Log.i("ERR", "FileNotFoundException");
            }
        }
        imageView.setImageBitmap(bmp);
    }
}

