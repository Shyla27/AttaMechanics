package com.example.attamechanics.ViewModel;

import android.app.Application;

import com.example.attamechanics.Adapters.User;
import com.example.attamechanics.Utils.FirebaseLoginInstance;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.attamechanics.Utils.LoginRepo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInViewModel extends AndroidViewModel {


    private FirebaseLoginInstance loginInstance;
    private LoginRepo splashRepository;
    LiveData<User> isUserAuthenticatedLiveData;
    LiveData<User> userLiveData;

    public LogInViewModel(Application application) {
        super(application);
        splashRepository = new LoginRepo();
    }

    void checkIfUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    void setUid(String uid) {
        userLiveData = splashRepository.addUserToLiveData(uid);
    }
}
