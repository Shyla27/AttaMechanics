package com.example.attamechanics.Auth;

import static com.example.attamechanics.Utils.Constants.USERS;
import static com.example.attamechanics.Utils.HelperClass.logErrorMessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attamechanics.Adapters.GlobalUser;
import com.example.attamechanics.Adapters.User;
import com.example.attamechanics.Admin.AdminDashboard;
import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Mechs.MechanicsDashboard;
import com.example.attamechanics.R;
import com.example.attamechanics.ViewModel.LogInViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@SuppressWarnings("ConstantConditions")
public class Login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private User user = new User();
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = rootRef.collection(USERS);
    private EditText inputEmail;
    private EditText inputPassword;
    private Button btn_logIn;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference admindatabase , mechsdatabase;
    FirebaseDatabase firebaseDatabase;
    private String type;
    LogInViewModel logInViewModel;
    String emailLog;
    String pwdLog;
    TextView textToSignUp;
    FirebaseUser currentUser;
    FrameLayout frameLayoutLogin;
    TextView tv_forgetPassword, btn_signup;
    CheckBox checkBox;

    private Uri imageUri;
//    private AppPermissions appPermissions;
//    private LoadingDialog loadingDialog;
    private String email, username, password;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authStateListener = firebaseAuth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {


//                if (type.equals("Mechanic Details")) {
//                    Toast.makeText(this, "Welcome Back! ", Toast.LENGTH_SHORT).show();
//
//                    Intent Signup = new Intent(getApplicationContext(), MechanicsDashboard.class);
//                    startActivity(Signup);
//                    finish();
//                } else if (type.equals("Garage Admin")) {
//                    DatabaseReference admindatabase = FirebaseDatabase.getInstance().getReference().child("Users");
//                    Toast.makeText(this, "Welcome Back! ", Toast.LENGTH_SHORT).show();
//
//                    Intent Signup = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(Signup);
//                    finish();
//                }
            }

        };

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btn_signup = findViewById(R.id.btn_signup);
        btn_logIn = findViewById(R.id.btn_login);
        TextView btnReset = findViewById(R.id.btn_reset_password);
        Spinner userType = findViewById(R.id.userType);

        checkBox = findViewById(R.id.showpasscode);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        firebaseDatabase = FirebaseDatabase.getInstance();
        admindatabase= firebaseDatabase.getReference("Users");
        logInViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(LogInViewModel.class);

        List<String> list = new ArrayList<>();
        list.add("Select Account Type");
        list.add("Mechanic Details");
        list.add("Garage Admin");

        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);


        userType.setAdapter(adapter);
        userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("Select Account Type")) {

                    type = adapterView.getItemAtPosition(i).toString();
                    inputPassword.setEnabled(false);
                    inputEmail.setEnabled(false);
                }
                else if (adapterView.getItemAtPosition(i).toString().equals("Mechanics")){
                    type= adapterView.getItemAtPosition(i).toString();
                    inputPassword.setEnabled(true);
                    inputEmail.setEnabled(true);
                } else {
                    type = adapterView.getItemAtPosition(i).toString();
                    inputEmail.setEnabled(true);
                    inputPassword.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        auth = FirebaseAuth.getInstance();
        listener();


     //   btn_signup.setOnClickListener(view -> startActivity(new Intent(Login.this,Signup.class)));

        btnReset.setOnClickListener(view -> startActivity(new Intent(Login.this, ForgotPassword.class)));

        btn_logIn.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();
            final String password = inputPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
               // Toast.makeText(getApplicationContext(), "Enter email address! ", Toast.LENGTH_SHORT).show();

               inputEmail.setError( "Enter email address! ");
               return;
            }
            if (TextUtils.isEmpty(password)) {
//                Toast.makeText(getApplicationContext(), "Enter Password!" , Toast.LENGTH_SHORT).show();
               inputPassword.setError("Enter Password!");
                return;
            } else {

            }
            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email, password)

                    .addOnCompleteListener(Login.this, task -> {
                        chooseUser();
                        progressBar.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
    //                                    GlobalUser.currentuser = (com.example.attamechanics.Adapters.User) snapshot.getValue();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            String user_id = auth.getCurrentUser().getUid();
                            DatabaseReference current_user_id = firebaseDatabase.getReference(user_id);
                            current_user_id.child("contact").setValue(password);
                            current_user_id.child("email").setValue(email);


                        }
                        else if (!task.isSuccessful()) {
                            if (password.length() < 6 ) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else  Toast.makeText(Login.this,getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                        }
                    });

        });



    }
    MutableLiveData<User> checkIfUserIsAuthenticatedInFirebase() {
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

    MutableLiveData<User> addUserToLiveData(String uid) {
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

    private void listener() {

        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        btn_logIn.setOnClickListener(v -> {
            inputEmail.clearFocus();
            inputPassword.clearFocus();
            v.startAnimation(buttonClick);
            dismissKeyboard();
            emailLog = inputEmail.getText().toString();
            pwdLog = inputPassword.getText().toString();

            if ((pwdLog.isEmpty() && emailLog.isEmpty())) {
                Toast.makeText(Login.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                inputEmail.requestFocus();
            } else if (emailLog.isEmpty()) {
                inputEmail.setError("Please enter your Email Id.");
                inputEmail.requestFocus();
            } else if (pwdLog.isEmpty()) {
                inputPassword.setError("Please enter your password.");
                inputPassword.requestFocus();
            } else {

                inputEmail.setClickable(false);
                inputPassword.setClickable(false);
                inputEmail.setClickable(false);
                btn_logIn.setClickable(true);
                progressBar.setVisibility(View.VISIBLE);

            }

        });

        btn_signup.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), Signup.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finish();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }
    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }



    private void chooseUser() {
        if (verifyType())

            return;
        if (type.equals("Mechanic Details")) {
            Toast.makeText(this, "Welcome Back! " ,Toast.LENGTH_SHORT).show();

            Intent Signup = new Intent(getApplicationContext(), MechanicsDashboard.class);
            startActivity(Signup);
            finish();
        }
        else if (type.equals("Garage Admin")) {
            DatabaseReference admindatabase = FirebaseDatabase.getInstance().getReference().child("Users");
            Toast.makeText(this, "Welcome Back! " ,Toast.LENGTH_SHORT).show();

            Intent Signup = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Signup);
            finish();
        }
    }
    private boolean verifyType(){
        if (type.equals("Select Account Type"))  {
            Toast.makeText(this, "Please select account type! " ,Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;

    }


}