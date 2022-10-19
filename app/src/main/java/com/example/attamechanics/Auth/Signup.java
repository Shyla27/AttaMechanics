package com.example.attamechanics.Auth;

import static com.example.attamechanics.Utils.Constants.RC_SIGN_IN;
import static com.example.attamechanics.Utils.HelperClass.logErrorMessage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.attamechanics.Adapters.User;
import com.example.attamechanics.Constants.AllConstant;
import com.example.attamechanics.Constants.AppPermissions;
import com.example.attamechanics.Garage.AllGarages;
import com.example.attamechanics.Garage.GarageSpeciality;
import com.example.attamechanics.Garage.Garageinfo;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Mechs.MechanicsDashboard;
import com.example.attamechanics.R;
import com.example.attamechanics.ViewModel.SignInViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity {

    private static final String USER = "FirebaseAuthAppTag";
    CircleImageView userphoto;
    static int PreReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;
    private FirebaseDatabase database;
    private DatabaseReference mdatabase;
    String userID;
    FirebaseFirestore fStore;
    private Spinner userType;
    private String type;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText name;
    private EditText mobilenumber;
    private EditText confirmpassword;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private User user;
    private String username, fname, email, password, phone, confirmpass;
    private static final String USERS = "users";
    private String TAG = "Signup";
    private String memberID;
    CheckBox checkBox;
    private  SignInViewModel authViewModel;
    private GoogleSignInClient googleSignInClient;


    private Uri imageUri;
   private AppPermissions appPermissions;
   // private LoadingDialog loadingDialog;
    //private String email, username, password;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        auth = FirebaseAuth.getInstance();
        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.useremail);
        inputPassword = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        mobilenumber = findViewById(R.id.mobilenumber);
        name = findViewById(R.id.username);
        progressBar = findViewById(R.id.progressBar);

        checkBox = findViewById(R.id.showpassword);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        userType=(Spinner)findViewById(R.id.userType);
        database= FirebaseDatabase.getInstance();
        mdatabase = database.getReference("Users");

        List<String> list = new ArrayList<>();
        list.add("Select Account Type");
        list.add("Mechanic Details");
        list.add("Admin Details");

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
                } else if (adapterView.getItemAtPosition(i).toString().equals("Mechanics Details")) {
                    type = adapterView.getItemAtPosition(i).toString();
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

        btnSignIn.setOnClickListener(view -> startActivity(new Intent(Signup.this, Login.class)));

        auth = FirebaseAuth.getInstance();


        btnSignUp.setOnClickListener(view -> {
                    String fullname = name.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String mobilenumber = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                    String confirmpass = confirmpassword.getText().toString().trim();

                    memberID = mobilenumber;



                    if (email.isEmpty() || fullname.isEmpty() || mobilenumber.isEmpty() ||
                            password.isEmpty() || !password.equals(confirmpass)) {
                        showMessage("Please Verify all fields");
                        btnSignUp.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        CreateUserAccount(email, userID, fullname, password, mobilenumber);
                    }
                }
        );


    }


//
//    private void initGoogleSignInClient() {
//        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
//                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
//    }
//
//    private void initAuthViewModel() {
//        authViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
//    }
//
//
//    private void initSignInButton() {
//        SignInButton googleSignInButton = findViewById(R.id.google_sign_in_button);
//        googleSignInButton.setOnClickListener(v -> signIn());
//    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    getGoogleAuthCredential(googleSignInAccount);
                }
            } catch (ApiException e) {
                logErrorMessage(e.getMessage());
            }
        }
    }

    private void getGoogleAuthCredential(GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithGoogleAuthCredential(googleAuthCredential);
    }

    private void signInWithGoogleAuthCredential(AuthCredential googleAuthCredential) {
        authViewModel.signInWithGoogle(googleAuthCredential);
        authViewModel.authenticatedUserLiveData.observe(this, authenticatedUser -> {
            if (authenticatedUser.isNew) {
                createNewUser(authenticatedUser);
            } else {
                goToMainActivity(authenticatedUser);
            }
        });
    }

    private void createNewUser(User authenticatedUser) {
        authViewModel.createUser(authenticatedUser);
        authViewModel.createdUserLiveData.observe(this, user -> {
            if (user.isCreated) {
                toastMessage(user.name);
            }
            goToMainActivity(user);
        });
    }

    private void goToMainActivity(User user) {
      Intent intent = new Intent(Signup.this, MainActivity.class);
        intent.putExtra(USER, String.valueOf(user));
        startActivity(intent);
        finish();
    }


    private void CreateUserAccount(String email, String userID, String fullname, String password, String mobilenumber) {
        User user = new User(fullname, userID, email);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                                String user_id = auth.getCurrentUser().getUid();
                                DatabaseReference current_user_id = mdatabase.child(user_id);
                                current_user_id.child("Username").setValue(fullname);
                                current_user_id.child("contact").setValue(mobilenumber);
                                current_user_id.child("email").setValue(email);
                                current_user_id.child("password").setValue(password);
                        Intent Signup = new Intent(getApplicationContext(), Garageinfo.class);
                        startActivity(Signup);
                        finish();

                                updateUI();

                    }else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(Signup.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                });

    }

    public void updateUI() {
        String keyid = mdatabase.push().getKey();

        if (verifyType())

            return;
        if (type.equals("Mechanic Details")) {
            Intent Signup = new Intent(getApplicationContext(), MechanicsDashboard.class);
            startActivity(Signup);
            finish();
        }
        else if (type.equals("Admin Details")) {
            Intent Signup = new Intent(getApplicationContext(), Garageinfo.class);
            startActivity(Signup);
            finish();
        }
    }

    private boolean verifyType() {
        if (type.equals("Select Account Type"))  {
            Toast.makeText(this, "Please select account type! " ,Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void showMessage(String please_verify_all_fields) {
        Toast.makeText(getApplicationContext(), please_verify_all_fields, Toast.LENGTH_LONG).show();

    }    private void toastMessage(String name) {
        Toast.makeText(this, "Hi " + name + "!\n" + "Your account was successfully created.", Toast.LENGTH_LONG).show();
    }
    }
