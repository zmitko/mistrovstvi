package com.example.navrhui4;


import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

public class DetailFragment extends Fragment {
    public static DetailFragment newInstance(int index) {
        DetailFragment f = new DetailFragment();
        // načítanie indexu vybraneho objektu
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }
    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }
    // LayoutInflator umiestni Fragment na obrazovku
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());
        int padding = (int)
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        4, getActivity().getResources().getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroller.addView(text);
        text.setText(AndroidInfo.POPISY[getShownIndex()]);
        return scroller;
    }
}

