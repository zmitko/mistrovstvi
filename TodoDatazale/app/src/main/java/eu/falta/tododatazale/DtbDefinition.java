package eu.falta.tododatazale;

import android.provider.BaseColumns;

public class DtbDefinition {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DtbDefinition() {
    }

    /* Inner class that defines the table contents */
    public static class ToDoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_STATE = "state";
    }
}
