package com.ezanvakti.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ezanvakti.MainActivity;
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
public class IlceListFragment extends ListFragment {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mSehirID = getArguments().getString(ARG_SEHIR_ID);
        }

        RestClient.getAPIService().getIlceler(mSehirID, new Callback<List<Ilce>>() {
            @Override
            public void success(List<Ilce> ilceList, Response response) {
                setListAdapter(new ArrayAdapter<Ilce>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, ilceList));
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
        Ilce ilce = ((Ilce)l.getAdapter().getItem(position));
        DBUtils.fetchAndSaveVakits(ilce, new DBUtils.ProcessStatusListener() {
            @Override
            public void onSuccess() {
                getFragmentManager().popBackStack(VakitFragment.FRAGMENT_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getActivity(),R.string.err_get_vakitler,Toast.LENGTH_LONG).show();
            }
        });
    }

}
