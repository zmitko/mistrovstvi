package eu.falta.tododatazale;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    DbConnector dbConnector;
    private final static Logger logger = Logger.getLogger(MainActivity.class.getName());

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbConnector = new DbConnector(new ToDoDbHelper(this));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                long id = dbConnector.AddEntry("Test");
                logger.log(Level.INFO, "Nové ID: " + id);
                Snackbar.make(view, "Nové ID: " + id, Snackbar.LENGTH_LONG).show();
                Refresh();
            }
        });

        Refresh();
    }

    public void Refresh() {
        List<ToDoItem> items = dbConnector.GetEntries();

        ArrayAdapter adapter = new ArrayAdapter<ToDoItem>(this, android.R.layout.simple_list_item_multiple_choice, items);

        listView = findViewById(R.id.listView_Items);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                ToDoItem todo = (ToDoItem) listView.getItemAtPosition(position);
                todo.done = currentCheck;
                dbConnector.Update(todo.id, todo.done);
            }
        });

        listView.setAdapter(adapter);

        for (int i = 0; i < items.size(); i++) {
            listView.setItemChecked(i, items.get(i).done);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
