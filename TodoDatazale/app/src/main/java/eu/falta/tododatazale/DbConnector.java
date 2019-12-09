package eu.falta.tododatazale;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DbConnector {

    ToDoDbHelper dbHelper;

    public DbConnector(ToDoDbHelper helper) {
        this.dbHelper = helper;
    }

    public long AddEntry(String title) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DtbDefinition.ToDoEntry.COLUMN_NAME_TITLE, title);
        values.put(DtbDefinition.ToDoEntry.COLUMN_NAME_STATE, 1);

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DtbDefinition.ToDoEntry.TABLE_NAME, null, values);

        db.close();

        return newRowId;
    }

    public List<ToDoItem> GetEntries() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] columns = {
                BaseColumns._ID,
                DtbDefinition.ToDoEntry.COLUMN_NAME_TITLE,
                DtbDefinition.ToDoEntry.COLUMN_NAME_STATE
        };

        Cursor cursor = db.query(DtbDefinition.ToDoEntry.TABLE_NAME, null, null, null, null, null, null);

        List<ToDoItem> res = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
                String title = cursor.getString(cursor.getColumnIndex(DtbDefinition.ToDoEntry.COLUMN_NAME_TITLE));
                int doneVal = cursor.getInt(cursor.getColumnIndex(DtbDefinition.ToDoEntry.COLUMN_NAME_STATE));
                boolean done = doneVal > 0;
                ToDoItem item = new ToDoItem();
                item.id = id;
                item.title = title;
                item.done = done;

                res.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return res;
    }

    public void Update(long id, boolean done) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(DtbDefinition.ToDoEntry.COLUMN_NAME_STATE, done ? 1 : 0);

// Which row to update, based on the title
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int count = db.update(
                DtbDefinition.ToDoEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        db.close();
    }


}
