package com.example.attamechanics.Garage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.attamechanics.Auth.Signup;
import com.example.attamechanics.MainActivity;
import com.example.attamechanics.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditGarageProfile extends AppCompatActivity {

    private Button proceedtohome, uploadimages, chooseimages, addlogo;
    private CircleImageView profile_image;
    private EditText description;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private ImageView imageView;
    private Uri filepath;
    private final int PICK_IMAGE_REQUEST = 22;
    private static final int REQUEST_LOCATION = 1;
    private String garageID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_garage_profile);
        profile_image = findViewById(R.id.profile_image);
        proceedtohome = findViewById(R.id.proceedtohome);
        chooseimages = findViewById(R.id.chooseimages);
        uploadimages = findViewById(R.id.uploadgarageimages);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.imageView);
        addlogo = findViewById(R.id.addlogo);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("garagedets");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        chooseimages.setOnClickListener(view ->ChooseImages());
        uploadimages.setOnClickListener(view -> UploadGarageImage());

        profile_image.setOnClickListener(view -> UploadLogo());
        addlogo.setOnClickListener(view -> ChoseLogo());

        proceedtohome.setOnClickListener(view -> startActivity(new Intent(EditGarageProfile.this, Signup.class)));




    }

    private void UploadLogo() {
        Intent intet =new Intent();
        intet.setType("image/*");
        intet.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intet, "Select Logo from here..."), PICK_IMAGE_REQUEST);

    }


    private void ChoseLogo() {

        if (filepath != null ) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Logo");
            progressDialog.show();
            StorageReference reference = storageReference.child("images/CompanyLogos/" + UUID.randomUUID().toString());
reference.putFile(filepath)
        .addOnSuccessListener(taskSnapshot -> {
            progressDialog.dismiss();
            Toast.makeText(EditGarageProfile.this, "Logo Uploaded", Toast.LENGTH_SHORT).show();
        }
        )
        .addOnFailureListener( e -> {
            progressDialog.dismiss();
            Toast.makeText(EditGarageProfile.this,"Failed" + e.getMessage(),
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

    private void UploadGarageImage() {

        if (filepath != null ) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Image...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/GaragePhotos/" + UUID.randomUUID().toString());
            ref.putFile(filepath)
                    .addOnSuccessListener(taskSnapshot -> {
                                progressDialog.dismiss();
                                Toast.makeText(EditGarageProfile.this, "Photo Uploaded Add More photos?!!", Toast.LENGTH_SHORT).show();
                            }
                    ) .addOnFailureListener( e -> {
                progressDialog.dismiss();
                Toast.makeText(EditGarageProfile.this,"Failed" + e.getMessage(),
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

    private void ChooseImages() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent, "Select Image from here..."),
                PICK_IMAGE_REQUEST
        );
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