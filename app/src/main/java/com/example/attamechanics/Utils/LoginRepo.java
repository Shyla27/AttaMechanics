package com.example.attamechanics.Utils;

import static com.example.attamechanics.Utils.Constants.USERS;
import static com.example.attamechanics.Utils.HelperClass.logErrorMessage;

import androidx.lifecycle.MutableLiveData;

import com.example.attamechanics.Adapters.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

@SuppressWarnings("ConstantConditions")
public class LoginRepo {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private User user = new User();
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = rootRef.collection(USERS);

    public MutableLiveData<User> checkIfUserIsAuthenticatedInFirebase() {
        MutableLiveData<User> isUserAuthenticateInFirebaseMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            user.isAuthenticated = false;
            isUserAuthenticateInFirebaseMutableLiveData.setValue(user);
        } else {
            user.uid = firebaseUser.getUid();
            user.isAuthenticated = true;
            isUserAuthenticateInFirebaseMutableLiveData.setValue(user);
        }
        return isUserAuthenticateInFirebaseMutableLiveData;
    }

    public MutableLiveData<User> addUserToLiveData(String uid) {
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        usersRef.document(uid).get().addOnCompleteListener(userTask -> {
            if (userTask.isSuccessful()) {
                DocumentSnapshot document = userTask.getResult();
                if(document.exists()) {
                    User user = document.toObject(User.class);
                    userMutableLiveData.setValue(user);
                }
            } else {
                logErrorMessage(userTask.getException().getMessage());
            }
        });
        return userMutableLiveData;
    }

}
