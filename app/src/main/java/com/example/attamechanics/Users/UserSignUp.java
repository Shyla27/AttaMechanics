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

public class UserSignUp extends AppCompatActivity {

    private TextInputEditText userNameEdt, passwordEdt, confirmPwdEdt;
    private TextView loginTV;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loadingPB = findViewById(R.id.idPBLoading);
        confirmPwdEdt = findViewById(R.id.idEdtConfirmPassword);
        loginTV = findViewById(R.id.idTVLoginUser);
        registerBtn = findViewById(R.id.idBtnRegister);
        mAuth = FirebaseAuth.getInstance();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserSignUp.this, UserLogin.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);

                // getting data fro =m our edit text.
                String userName = userNameEdt.getText().toString();
                String pwd = passwordEdt.getText().toString();
                String cnfPwd = confirmPwdEdt.getText().toString();

                // checking if the password and confirm password is equal or not.
                if (!pwd.equals(cnfPwd)) {
                    Toast.makeText(UserSignUp.this, "Please check both having same password..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)) {

                    // checking if the text fields are empty or not.
                    Toast.makeText(UserSignUp.this, "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // in on success method we are hiding our progress bar and opening a login activity.
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(UserSignUp.this, "User Registered..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UserSignUp.this, RegisterCar.class);
                                startActivity(i);
                                finish();
                            } else {

                                // in else condition we are displaying a failure toast message.
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(UserSignUp.this, "Fail to register user..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}