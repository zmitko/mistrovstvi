package com.example.master_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

public class ZoznamFragment extends ListFragment {
    boolean mDvaPanely;
    // vybrany prvok ListView
    int mVybrany = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> connectArrayToListView = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                AndroidInfo.NAZVY);
        //Pripoj ListView na data
        setListAdapter(connectArrayToListView);
        View detailsFrame = getActivity().findViewById(R.id.detaily);
        // sú dva panely?
        mDvaPanely = detailsFrame !=
                null && detailsFrame.getVisibility() == View.VISIBLE;
        // onSaveInstanceState() pri rotácii
        if (savedInstanceState != null) {
            // obnov pozíciu
            mVybrany = savedInstanceState.getInt("curChoice", 0);
        }
        if (mDvaPanely) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(mVybrany);
        }
    }

    // zmena orientácie
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mVybrany);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    //zobraz detaily
    void showDetails(int index) {
        mVybrany = index;
        // Zoznam v ListView a detaily vedľa seba
        if (mDvaPanely) {
            getListView().setItemChecked(index, true);
            // zobrazenie detailov
            DetailFragment details = (DetailFragment)
                    getFragmentManager().findFragmentById(R.id.detaily);
            if (details == null || details.getShownIndex() != index) {
                // detail fragment pre vybraný index
                details = DetailFragment.newInstance(index);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                //nahradenie detail fragmentu aktuálnym
                ft.replace(R.id.detaily, details);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
            // DetailFragment v novej aktivite
            Intent intent = new Intent();
            intent.setClass(getActivity(), DetailsActivity.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }
}
