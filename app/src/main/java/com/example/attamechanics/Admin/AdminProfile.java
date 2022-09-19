package com.example.attamechanics.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.Adapters.User;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.Mechs.MechProfile;
import com.example.attamechanics.Mechs.Mytasks;
import com.example.attamechanics.Notifications;
import com.example.attamechanics.R;
import com.example.attamechanics.Users.NearbyGarages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Map;
import java.util.Objects;

public class AdminProfile extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    TextView profile_tv,username,bio_tv;
    EditText  bio_et;

    ImageView edit_img;
    Button save;
    FirebaseUser fuser;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    private String garageID;
    private String onlineUserID;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final int IMAGE_REQUEST = 1;
    private Uri imageUri;
    private StorageTask uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        username = findViewById(R.id.adminusername);
        bio_tv = findViewById(R.id.bio_tv);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        onlineUserID = fuser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(onlineUserID);


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId())
            {
                case R.id.myaccount:

                    return true;
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.notificationsmechs:
                startActivity(new Intent(getApplicationContext(), Notifications.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.action_nearby:
                    startActivity(new Intent(getApplicationContext(), GoogleMaps.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });


       databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getname());
                bio_tv.setText(user.getEmail());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}

