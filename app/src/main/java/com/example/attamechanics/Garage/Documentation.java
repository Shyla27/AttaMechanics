package com.example.attamechanics.Garage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.attamechanics.Adapters.Admin;
import com.example.attamechanics.Adapters.GaragesAdapter;
import com.example.attamechanics.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

public class Documentation extends AppCompatActivity {


    private Button btnSelect,coninue,uploadcert, chosecert;
    private EditText idnumber;
    private ImageView imageView, imgView;
    private Uri filepath;
    private final int PICK_IMAGE_REQUEST = 22;
    Admin admin;
    private ProgressBar progressBar;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private static final int REQUEST_LOCATION = 1;
    private String garageID;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        idnumber = findViewById(R.id.idNumber);
        btnSelect = findViewById(R.id.btnChoose);
        Button btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imgView);
        coninue = findViewById(R.id.coninue);
        progressBar = findViewById(R.id.progressBar);
        uploadcert = findViewById(R.id.uploadcert);
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        storageReference = storage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("GarageInfo/Garages");
        uploadcert.setOnClickListener(view ->UploadCert());

        btnSelect.setOnClickListener(view -> SelectImage());

        btnUpload.setOnClickListener(view -> uploadImage());

        coninue.setOnClickListener(view -> {
            String idnum = idnumber.getText().toString().trim();

            if (TextUtils.isEmpty(idnum)) {
                Toast.makeText(getApplicationContext(), "Enter ID Number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (idnum.length() > 8) {

                Toast.makeText(getApplicationContext(), "Number should be less than 8 characters!", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            GaragesAdapter garagesAdapter = new GaragesAdapter(garageID,idnum);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String user_id = auth.getCurrentUser().getUid();
                    DatabaseReference current_user_id = databaseReference.child(user_id);
                    current_user_id.child("IDNumber").setValue(idnum);

                    Toast.makeText(getApplicationContext(), "Documents under review.You will be notified to create a garage account", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(Documentation.this, EditGarageProfile.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Documentation.this, "Fail to add Info..", Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    private void uploadImage() {
        if (filepath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading National ID...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/National ID/" + UUID.randomUUID().toString());
                    //randomUUID().toString());

            ref.putFile(filepath)
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                progressDialog.dismiss();

                                Toast.makeText(Documentation.this,
                                        "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                            }
                    )
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(Documentation.this, "Failed" + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    })



                    .addOnProgressListener(snapshot -> {
                        double progress  = (100.0  * snapshot.getBytesTransferred()
                                / snapshot.getTotalByteCount());
                        progressDialog.setMessage(
                                "Uploaded "
                                        + (int)progress + "%");
                    });
        }
    }

    private void SelectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent, "Select Image from here..."),
                PICK_IMAGE_REQUEST
        );
    }

    private void UploadCert() {

        if (filepath != null ) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Certificate ...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/BusinessCertificate/" + UUID.randomUUID().toString());
            ref.putFile(filepath)
                    .addOnSuccessListener(taskSnapshot -> {
                                progressDialog.dismiss();
                                Toast.makeText(Documentation.this, "Certificate Uploaded!!", Toast.LENGTH_SHORT).show();
                            }
                    ) .addOnFailureListener( e -> {
                progressDialog.dismiss();
                Toast.makeText(Documentation.this,"Failed" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            })
                    .addOnProgressListener(snapshot -> {
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage(
                                "Uploaded" + (int) progress + "%"
                        );
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null
                && data.getData() != null) {
            filepath = data.getData();
        } try {
            Bitmap bitmap = MediaStore.Images
                    .Media .getBitmap(
                            getContentResolver(),
                            filepath);
            imageView.setImageBitmap(bitmap);
        }
        catch (IOException e ) {
            e.printStackTrace();
        }
    }
}