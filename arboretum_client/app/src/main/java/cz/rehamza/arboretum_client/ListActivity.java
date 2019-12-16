package cz.rehamza.arboretum_client;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private Trees trees;
    private File treesPath;
    private File imgPath;
    private TreesAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_hamza_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setContentView(R.layout.activity_list);

        treesPath = (File) getIntent().getExtras().get("treesPath");
        imgPath = (File) getIntent().getExtras().get("imgPath");
        trees = new Trees();
    }

    @Override
    protected void onResume(){
        super.onResume();

        recyclerView = findViewById(R.id.trees_view);
        layoutManager = new LinearLayoutManager(this);

        trees.load(treesPath);
        adapter = new TreesAdapter(ListActivity.this, treesPath, imgPath);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.getTrees().load(treesPath);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = this.getIntent();
        resultIntent.putExtra("resultIndex", -1);
        this.setResult(Activity.RESULT_OK, resultIntent);
        this.finish();
    }
}
