package com.dision.android.beascan.broadcasts;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.dision.android.beascan.interfaces.BluetoothStateListener;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {

    // constants
    public static final String BASIC_TAG = BluetoothBroadcastReceiver.class.getName();

    // variables
    private IntentFilter mIntentFilter;
    private Context mContext;
    private BluetoothStateListener mCallback;

    // constructor
    public BluetoothBroadcastReceiver(Context context, BluetoothStateListener callback) {
        mContext = context;
        mCallback = callback;

        initVariables();
    }

    // methods
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        switch (action) {
            // Bluetooth state has changed.
            case BluetoothAdapter.ACTION_STATE_CHANGED: {
                int previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1);
                int currentState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);

                if (mCallback != null) {
                    if (previousState != BluetoothAdapter.STATE_ON &&
                            currentState == BluetoothAdapter.STATE_ON) {
                        mCallback.onBluetoothStateChanged(true);
                    }

                    if (previousState != BluetoothAdapter.STATE_OFF &&
                            currentState == BluetoothAdapter.STATE_OFF) {
                        mCallback.onBluetoothStateChanged(false);
                    }
                }

                break;
            }

            default: {
                break;
            }
        }
    }

    public void turnOn() {
        mIntentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mContext.registerReceiver(this, mIntentFilter);
    }

    public void turnOff() {
        try {
            mContext.unregisterReceiver(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void initVariables() {
        mIntentFilter = new IntentFilter();
    }
}
