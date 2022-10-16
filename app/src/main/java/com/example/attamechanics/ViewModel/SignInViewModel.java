package com.example.attamechanics.ViewModel;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.attamechanics.Adapters.User;
import com.example.attamechanics.Utils.AuthRepository;
import com.google.firebase.auth.AuthCredential;

public class SignInViewModel extends AndroidViewModel {

    private AuthRepository authRepository;
    public MutableLiveData<com.example.attamechanics.Adapters.User> authenticatedUserLiveData;
    public MutableLiveData<com.example.attamechanics.Adapters.User> createdUserLiveData;

    public SignInViewModel(Application application) {
        super(application);
        authRepository = new AuthRepository();
    }

    public void signInWithGoogle(AuthCredential googleAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignInWithGoogle(googleAuthCredential);
    }

    public void createUser(User authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestoreIfNotExists(authenticatedUser);
    }

}
