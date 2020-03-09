package com.example.kontakty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

public class MainActivity extends ListActivity implements
        LoaderManager.LoaderCallbacks < Cursor > {
    private KontaktyAdapter kontaktyAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kontaktyAdapter = new KontaktyAdapter(this, R.layout.kontakt, null, 0);
        setListAdapter(kontaktyAdapter);
        getLoaderManager().initLoader(0, null, this);
    }
    static final String[] CONTACTS_ROWS = new String[] {
            ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
    };
    @Override
    public Loader< Cursor > onCreateLoader(int id, Bundle args) {
        String where = " (("+ ContactsContract.Contacts.DISPLAY_NAME + "NOTNULL) AND("+ ContactsContract.Contacts.DISPLAY_NAME + " != ‚‘))";

        String sort = ContactsContract.Contacts._ID + " ASC";
        return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, CONTACTS_ROWS,
                where, null, sort);
    }
    @Override
    public void onLoadFinished(Loader < Cursor > loader, Cursor data) {
        kontaktyAdapter.swapCursor(data);
    }
    @Override
    public void onLoaderReset(Loader < Cursor > loader) {
        kontaktyAdapter.swapCursor(null);
    }
}


