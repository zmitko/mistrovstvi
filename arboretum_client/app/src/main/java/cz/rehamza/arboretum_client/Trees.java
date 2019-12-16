package cz.rehamza.arboretum_client;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trees implements Serializable {
    private List<Tree> trees = new ArrayList<>();

    public Tree get(int index) {
        return trees.get(index);
    }

    public List getList() {
        return trees;
    }

    public void add(Tree tree) {
        trees.add(tree);
    }

    public void remove(int index) {
        trees.remove(index);
    }

    public void  remove(Tree tree) {
        trees.remove(tree);
    }

    public void clear() {
        trees.clear();
    }

    public int size() {
        return trees.size();
    }

    public JSONArray getJson() throws JSONException {
        JSONArray treesJSON = new JSONArray();

        for (Tree tree: trees) {
            treesJSON.put(tree.getJson());
        }

        return treesJSON;
    }

    @Override
    public String toString() {
        return "Trees{" +
                "trees=" + trees +
                '}';
    }

    public void save(File path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.getPath()));
            oos.writeObject(trees);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(File path) {
        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(path.getPath()));
            trees = (ArrayList) oos.readObject();
            Collections.sort(trees);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
