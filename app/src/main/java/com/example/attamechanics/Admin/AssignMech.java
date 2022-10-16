package com.example.attamechanics.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.attamechanics.R;
import com.example.attamechanics.ViewModel.MainActivityViewModel;
import com.onesignal.OneSignal;

public class AssignMech extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "de917e11-b228-49ce-ab2e-d6b436fceb48";
    //private MainActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_mech);

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications();
//
//        viewModel = new MainActivityViewModel();
//        OneSignal.addPermissionObserver(viewModel);
//        OneSignal.addSubscriptionObserver(viewModel);
//        OneSignal.addEmailSubscriptionObserver(viewModel);
//        viewModel.onActivityCreated(this)
//                .setupInterfaceElements();
    }
}