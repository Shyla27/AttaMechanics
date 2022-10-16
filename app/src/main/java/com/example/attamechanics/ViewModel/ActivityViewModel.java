package com.example.attamechanics.ViewModel;


import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.onesignal.OSEmailSubscriptionObserver;
import com.onesignal.OSEmailSubscriptionStateChanges;
import com.onesignal.OSPermissionObserver;
import com.onesignal.OSPermissionStateChanges;
import com.onesignal.OSSubscriptionObserver;
import com.onesignal.OSSubscriptionStateChanges;

public class ActivityViewModel implements OSPermissionObserver, OSSubscriptionObserver, OSEmailSubscriptionObserver {

    Activity getActivity() {
        return null;
    }

    AppCompatActivity getAppCompatActivity() {
        return null;
    }

    ActivityViewModel onActivityCreated(Context context) {
        return null;
    }

    public ActivityViewModel setupInterfaceElements() {
        return null;
    }

    void setupToolbar() {

    }

    void networkConnected() {

    }

    void networkDisconnected() {

    }


    @Override
    public void onOSPermissionChanged(OSPermissionStateChanges osPermissionStateChanges) {

    }

    @Override
    public void onOSEmailSubscriptionChanged(OSEmailSubscriptionStateChanges osEmailSubscriptionStateChanges) {

    }

    @Override
    public void onOSSubscriptionChanged(OSSubscriptionStateChanges osSubscriptionStateChanges) {

    }
}
