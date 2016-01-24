package com.ezanvakti;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,DataApi.DataListener {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mVakitLabel;

    private TextView mVakitTV;


    private static final String TAG = "Wear-WearService";

    GoogleApiClient mGoogleApiClient;

    long[] vakits = new long[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mGoogleApiClient  = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(Wearable.API)
                .build();
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mVakitLabel = (TextView) findViewById(R.id.title);
        mVakitTV = (TextView) findViewById(R.id.vakit);
        showNextVakit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.d(TAG, "onStart Event");
    }

    // Disconnect from the data layer when the Activity stops
    @Override
    protected void onStop() {
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
           Wearable.DataApi.removeListener(mGoogleApiClient,this);
            mGoogleApiClient.disconnect();
        }
        Log.d(TAG, "onStop Event");
        super.onStop();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mVakitLabel.setTextColor(getResources().getColor(android.R.color.white));
            mVakitTV.setVisibility(View.VISIBLE);

            mVakitTV.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mVakitLabel.setTextColor(getResources().getColor(android.R.color.black));
            mVakitTV.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected Event");
        Wearable.DataApi.addListener(mGoogleApiClient, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended Event");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed event");

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/get-day-vakit") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    vakits = dataMap.getLongArray("Vakits");
                    for(long vakit : vakits) {
                        Log.i("DataItem","Vakit: "+new Date(vakit));
                    }
                    showNextVakit();
                    Log.i("DataItem","received and updated");
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
                Log.i("DataItem","deleted");

            }
        }
    }

    public void showNextVakit() {
        Date vakit = null;

        Log.i("showNextVakit()","Setting next vakit");
        Date now = new Date();
        // first 6 item contains the vakits, ignore the rest
        for (int i=0;i<Math.min(vakits.length,6); ++i) {
            Date tmp = new Date(vakits[i]);
            if(tmp.before(now)) {
                if(i == 5) {
                    mVakitTV.setText("Elhamdulillah!");
                    vakit = null;
                    break;
                } else
                    vakit = tmp;
            } else {
                vakit = tmp;
                break;
            }
        }

        if(vakit != null)
            mVakitTV.setText(AMBIENT_DATE_FORMAT.format(vakit));
    }

}
