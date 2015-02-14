package com.ezanvakti.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ezanvakti.R;
import com.ezanvakti.rest.RestClient;
import com.ezanvakti.rest.model.Ilce;
import com.ezanvakti.utils.DBUtils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 */
public class IlceListFragment extends ListFragment implements SearchView.OnQueryTextListener {
    private ArrayAdapter<Ilce> mAdapter = null;
    private SearchView searchView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SEHIR_ID = "sehirID";

    // TODO: Rename and change types of parameters
    private String mSehirID;

    // TODO: Rename and change types of parameters
    public static IlceListFragment newInstance(String sehirID) {
        IlceListFragment fragment = new IlceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SEHIR_ID, sehirID);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IlceListFragment() {
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
                bar.setTitle(R.string.select_ilce);
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
            mSehirID = getArguments().getString(ARG_SEHIR_ID);
        }

        RestClient.getAPIService().getIlceler(mSehirID, new Callback<List<Ilce>>() {
            @Override
            public void success(List<Ilce> ilceList, Response response) {
                mAdapter = new ArrayAdapter<Ilce>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, ilceList);
                setListAdapter(mAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),R.string.err_get_ilce_list,Toast.LENGTH_LONG).show();
                error.printStackTrace();
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        searchView.clearFocus();
        searchView.setQuery("", false);
        setListShown(false); // show loading indicator
        Ilce ilce = ((Ilce)l.getAdapter().getItem(position));
        DBUtils.fetchAndSaveVakits(ilce, new DBUtils.ProcessStatusListener() {
            @Override
            public void onSuccess() {
                getActivity().finish();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getActivity(),R.string.err_get_vakitler,Toast.LENGTH_LONG).show();
                setListShown(true); // hide loading indicator
            }
        });
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
