package eu.falta.tododatazale;

import androidx.annotation.NonNull;

public class ToDoItem {
    long id;
    String title;
    boolean done;

    @NonNull
    @Override
    public String toString() {
        return this.title;
    }
}
