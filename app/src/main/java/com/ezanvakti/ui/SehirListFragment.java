package com.ezanvakti.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ezanvakti.R;
import com.ezanvakti.rest.RestClient;
import com.ezanvakti.rest.model.Sehir;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 */
public class SehirListFragment extends ListFragment implements SearchView.OnQueryTextListener{
    private ArrayAdapter<Sehir> mAdapter = null;
    private SearchView searchView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ULKE_ID = "ulkeId";

    // TODO: Rename and change types of parameters
    private String mUlkeID;


    // TODO: Rename and change types of parameters
    public static SehirListFragment newInstance(String ulkeID) {
        SehirListFragment fragment = new SehirListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ULKE_ID, ulkeID);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SehirListFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_add_location, menu);
        searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        if(searchView != null)
            searchView.setOnQueryTextListener(this);
    }

    private void setTitle() {
        Activity act = getActivity();
        if(act instanceof ActionBarActivity) {
            ActionBar bar = ((ActionBarActivity) act).getSupportActionBar();
            if(bar != null)
                bar.setTitle(R.string.select_sehir);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setTitle();
        if (getArguments() != null) {
            mUlkeID = getArguments().getString(ARG_ULKE_ID);
        }

        RestClient.getAPIService().getSehirler(mUlkeID, new Callback<List<Sehir>>() {
            @Override
            public void success(List<Sehir> sehirList, Response response) {
                mAdapter = new ArrayAdapter<Sehir>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, sehirList);
                setListAdapter(mAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(),R.string.err_get_sehir_list,Toast.LENGTH_LONG).show();
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        searchView.clearFocus();
        searchView.setQuery("",false);
        String sehirId = ((Sehir)l.getAdapter().getItem(position)).getId();
        IlceListFragment IlceFragment = IlceListFragment.newInstance(sehirId);

        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_from_right,R.anim.slide_out_to_left,R.anim.slide_in_from_left,R.anim.slide_out_to_right)
                .replace(R.id.container, IlceFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if(mAdapter == null)
            return false;
        mAdapter.getFilter().filter(newText);
        mAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
