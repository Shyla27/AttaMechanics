package com.example.attamechanics.Callback;

import android.util.Pair;

public interface AddPairAlertDialogCallback {
    void onSuccess(Pair<String, Object> pair);
    void onFailure();

}
