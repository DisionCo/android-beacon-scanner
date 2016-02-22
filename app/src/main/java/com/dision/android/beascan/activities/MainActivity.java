package com.dision.android.beascan.activities;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

import com.dision.android.beascan.R;
import com.dision.android.beascan.activities.abstracts.ABeaconScannerActivity;
import com.dision.android.beascan.broadcasts.BluetoothBroadcastReceiver;
import com.dision.android.beascan.interfaces.BluetoothStateListener;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class MainActivity
        extends
            ABeaconScannerActivity
        implements
            BluetoothStateListener {

    // constants
    public static String BASIC_TAG = MainActivity.class.getName();

    private static final int REQUEST_ENABLE_BT = 1;

    // variables
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothBroadcastReceiver mReceiver;

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        initListeners();

        mReceiver.turnOn();
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        for (final Beacon beacon: beacons) {
            // Get data and add it to AdapterView/RecyclerView
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReceiver.turnOff();
    }

    private void initVariables() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mReceiver = new BluetoothBroadcastReceiver(this, this);
    }

    private void initListeners() {

        // Add button click listener for enable/disable bluetooth
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                } else {
                    mBluetoothAdapter.disable();
                }
            }
        });
        */
    }


    @Override
    public void onBluetoothStateChanged(boolean isTurnedOn) {
        // Do something, bluetooth state has changed!
    }
}
