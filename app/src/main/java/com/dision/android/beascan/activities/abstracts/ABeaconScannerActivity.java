package com.dision.android.beascan.activities.abstracts;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

public abstract class ABeaconScannerActivity
        extends
            AppCompatActivity
        implements
            BeaconConsumer,
            RangeNotifier {

    // constants
    public static final String BASIC_TAG = ABeaconScannerActivity.class.getName();

    private static final String REGION_ID = "all-beacons-region";
    private static final String BEACON_LAYOUT_URL = "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v";
    private static final String BEACON_LAYOUT_UID = "s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19";
    private static final String BEACON_LAYOUT_TLM = "x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15";


    // variables
    private BeaconManager mBeaconManager;

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        setBeaconLayouts();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBeaconManager.bind(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBeaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        Region region = new Region(REGION_ID, null, null, null);

        try {
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        mBeaconManager.setRangeNotifier(this);
    }

    protected BeaconManager getBeaconManager() {
        return mBeaconManager;
    }

    private void initVariables() {
        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
    }

    private void setBeaconLayouts() {
        // Detect the URL frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BEACON_LAYOUT_URL));

        // Detect the main Eddystone-UID frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BEACON_LAYOUT_UID));

        // Detect the telemetry Eddystone-TLM frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BEACON_LAYOUT_TLM));
    }
}
