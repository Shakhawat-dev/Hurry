package com.metacoders.hurry.SignInController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.metacoders.hurry.Constants.constants;
import com.metacoders.hurry.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class accountSetupPage extends AppCompatActivity {

    EditText fnameIn, snameIn;
    String fname, sname;
    ImageButton imageButton;
    FirebaseAuth mauth;
    CircleImageView proPicChooser;
    ProgressDialog mprogressDialog;
    TextView title;
    String uid;

    Button takePhotoBtn;
    StorageReference mStorageReference;

    private Bitmap compressedImageFile;
    Uri mFilePathUri;
    DatabaseReference mref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup_page);

        fnameIn = findViewById(R.id.fnameIN);
        snameIn = findViewById(R.id.snameIN);
        imageButton = findViewById(R.id.nextBtn);
        proPicChooser = findViewById(R.id.imageViewOnProfileSetup);


        mauth = FirebaseAuth.getInstance();
        uid = "TEST";

        mStorageReference = FirebaseStorage.getInstance().getReference(constants.userProfileDb).child(uid);
        mref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid);

        //pregress dialog
        mprogressDialog = new ProgressDialog(accountSetupPage.this);

        mprogressDialog.setCancelable(false);

        proPicChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(accountSetupPage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(accountSetupPage.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                        BringImagePicker();


                    } else {

                        BringImagePicker();

                    }

                } else {

                    BringImagePicker();

                }


            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fname = fnameIn.getText().toString();
                sname = snameIn.getText().toString();

                Intent i = new Intent(getApplicationContext(), termsAndCondition.class);

                i.putExtra("FNAME", fname);
                i.putExtra("SNAME", sname);

                startActivity(i);

            }
        });


    }

    private void BringImagePicker() {


        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL) //shaping the image
                .start(accountSetupPage.this);


    }

    @Override
    protected void onActivityResult(/*int requestCode, int resultCode, @Nullable Intent data*/
            int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mFilePathUri = result.getUri();

                proPicChooser.setImageURI(mFilePathUri);


                //sending data once  user select the image
                uploadPicToServer(mFilePathUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }


    private void uploadPicToServer(Uri mFilePathUri) {

        if (mFilePathUri != null) {
            final String randomName = UUID.randomUUID().toString();

            // PHOTO UPLOAD
            File newImageFile = new File(mFilePathUri.getPath());

            try {

                compressedImageFile = new Compressor(accountSetupPage.this)
                        .setMaxHeight(920)
                        .setMaxWidth(920)
                        .setQuality(60)
                        .compressToBitmap(newImageFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageData = baos.toByteArray();
            UploadTask filePath = mStorageReference.child(randomName + uid + ".jpg").putBytes(imageData);

            filePath.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloaduri = uriTask.getResult();


                    //   String ts =mref.push().getKey() ;


                    mref.child("userProPic").setValue(downloaduri.toString());
                    mprogressDialog.hide();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mprogressDialog.hide();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    mprogressDialog.setTitle("Uploading.......");
                    mprogressDialog.show();


                }
            });


        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        FirebaseUser mause  ;

        mause = mauth.getCurrentUser()  ;

        if (mause != null){


            Intent i  = new Intent(getApplicationContext()  , homePage.class);
            startActivity(i);
            finish();

        }

         */

    }


}
