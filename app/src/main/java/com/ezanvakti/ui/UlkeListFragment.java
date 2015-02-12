package com.ezanvakti.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ezanvakti.R;
import com.ezanvakti.rest.RestClient;
import com.ezanvakti.rest.model.Ulke;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 */
public class UlkeListFragment extends ListFragment {


    // TODO: Rename and change types of parameters
    public static UlkeListFragment newInstance() {
        UlkeListFragment fragment = new UlkeListFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UlkeListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestClient.getAPIService().getUlkeler(new Callback<List<Ulke>>() {
            @Override
            public void success(List<Ulke> ulkeList, Response response) {
                ArrayAdapter<Ulke> adapter = new ArrayAdapter<Ulke>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, ulkeList);

                setListAdapter(adapter);
                getListView().setTextFilterEnabled(true);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(),R.string.err_get_ulke_list,Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStackImmediate();
            }
        });
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String name = ((Ulke)l.getAdapter().getItem(position)).getName();
        String ulkeID = ((Ulke)l.getAdapter().getItem(position)).getId();
        Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
        SehirListFragment sehirListFragment = SehirListFragment.newInstance(ulkeID);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
                .replace(R.id.container, sehirListFragment)
                .addToBackStack(null)
                .commit();
    }

}
