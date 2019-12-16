package cz.rehamza.arboretum_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.File;

public class TreesAdapter extends RecyclerView.Adapter {

    private ListActivity listActivity;
    private File localTreesFilePath;
    private File localImgPath;

    public class TreeViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView latin;
        public CheckBox chBoxVisited;

        public TreeViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.txt_name);
            latin = view.findViewById(R.id.txt_latin);
            chBoxVisited = view.findViewById(R.id.checkBox);
        }
    }

    private Trees trees = new Trees();

    public TreesAdapter(ListActivity listAct, File treesPath, File imgPath) {
        listActivity = listAct;
        localTreesFilePath = treesPath;
        localImgPath = imgPath;
    }

    public TreesAdapter(Trees trees) {
        this.trees = trees;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TreeViewHolder(view);
    }

    //Tried to put final here
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((TreeViewHolder) holder).name.setText(trees.get(position).getName());
        ((TreeViewHolder) holder).latin.setText(trees.get(position).getLatin());
        ((TreeViewHolder) holder).chBoxVisited.setChecked(trees.get(position).hasBeenVisited());
        ((TreeViewHolder) holder).chBoxVisited.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                trees.get(position).setVisited(b);
                trees.save(localTreesFilePath);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = listActivity.getIntent();
                resultIntent.putExtra("resultIndex", position);
                listActivity.setResult(Activity.RESULT_OK, resultIntent);
                listActivity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trees.size();
    }

    public TreesAdapter setTrees(Trees trees) {
        this.trees = trees;
        return this;
    }

    public Trees getTrees() {
        return trees;
    }
}

