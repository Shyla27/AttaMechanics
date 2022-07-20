package com.example.attamechanics.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attamechanics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity {
    private TextInputEditText userNameEdt, passwordEdt;
    private Button loginBtn;
    private TextView newUserTV, addcar, skip;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_login);

            userNameEdt = findViewById(R.id.idEdtUserName);
            passwordEdt = findViewById(R.id.idEdtPassword);
            loginBtn = findViewById(R.id.idBtnLogin);
            addcar = findViewById(R.id.addcar);
            newUserTV = findViewById(R.id.idTVNewUser);
            mAuth = FirebaseAuth.getInstance();
            loadingPB = findViewById(R.id.idPBLoading);
            skip = findViewById(R.id.skip);
            skip.setOnClickListener(view -> {
                Intent ski = new Intent(UserLogin.this, UserDashboard.class);
                startActivity(ski);
            });

            addcar.setOnClickListener(view ->  {
                Intent i = new Intent(UserLogin.this, RegisterCar.class);
                startActivity(i);
            });
            newUserTV.setOnClickListener(view -> {
                Intent i = new Intent(UserLogin.this, UserSignUp.class);
                startActivity(i);
            });
            loginBtn.setOnClickListener(view -> {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text on below line.
                String email = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                // on below line validating the text input.
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(UserLogin.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // on below line we are hiding our progress bar.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(UserLogin.this, "Login Successful..", Toast.LENGTH_SHORT).show();
                            // on below line we are opening our mainactivity.
                            Intent i = new Intent(UserLogin.this, UserDashboard.class);
                            startActivity(i);
                            finish();
                        } else {
                            // hiding our progress bar and displaying a toast message.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(UserLogin.this, "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });
        }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // if the user is not null then we are
            // opening a main activity on below line.
            Intent i = new Intent(UserLogin.this, UserDashboard.class);
            startActivity(i);
            this.finish();
        }
    }
}