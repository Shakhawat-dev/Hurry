package com.metacoders.hurry.SignInController;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.metacoders.hurry.Constants.constants;
import com.metacoders.hurry.R;
import com.metacoders.hurry.Utils.SharedPrefManager;
import com.metacoders.hurry.model.userModel;
import com.santalu.maskara.widget.MaskEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import id.zelory.compressor.Compressor;


public class completeProfilePage extends AppCompatActivity {

    LinearLayout nid_back, nid_front;
    TextInputEditText fullName, cityname;
    MaskEditText birthdate;
    Uri mFilePathUri;
    Button complete;
    String nid_front_link, nid_back_link;
    ImageView front, back;
    String nid_name_repo = null;
    String fname, city_name, birth_date;
    StorageReference mStorageReference;
    FirebaseAuth mauth;
    String uid;
    DatabaseReference mref;
    ProgressDialog dialog;
    String[] nid_image_list = new String[2];

    /*
     0 = front
     1 = back
     */

    int tapped_image = -1;
    private Bitmap compressedImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dialog = new ProgressDialog(completeProfilePage.this);
        mauth = FirebaseAuth.getInstance();
        uid = mauth.getUid();

        mStorageReference = FirebaseStorage.getInstance().getReference(constants.userProfileDb).child(uid);
        mref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid);

        nid_back = findViewById(R.id.nid_back);
        nid_front = findViewById(R.id.nid_front);
        fullName = findViewById(R.id.full_name_et);
        cityname = findViewById(R.id.city_name_et);
        complete = findViewById(R.id.compeleteBtn);
        birthdate = findViewById(R.id.birth);
        front = findViewById(R.id.front);
        back = findViewById(R.id.back);
        // mask
//        MaskedFormatter formatter = new MaskedFormatter("##/##/####");
//        birthdate.addTextChangedListener(new MaskedWatcher(formatter, birthdate));

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = fullName.getText().toString();
                city_name = cityname.getText().toString();
                birth_date = birthdate.getMasked().toString();

                if (TextUtils.isEmpty(fname)
                        || TextUtils.isEmpty(city_name)
                        || TextUtils.isEmpty(birth_date)
                        || TextUtils.isEmpty(nid_image_list[1])
                        || TextUtils.isEmpty(nid_image_list[0])) {
                    Toast.makeText(getApplicationContext(), "Please Fill Up Accordingly !!"
                            + birth_date, Toast.LENGTH_SHORT).show();
                } else {
                    updateTheProfile(fname, city_name, birth_date);
                }


            }
        });
        nid_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tapped_image = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(completeProfilePage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(completeProfilePage.this,
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
        nid_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapped_image = 1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(completeProfilePage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(completeProfilePage.this,
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


    }

    private void updateTheProfile(String fnamee, String city_namee, String birth_datee) {

        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("nid_front", nid_image_list[0]);
        hashMap.put("nid_back", nid_image_list[1]);
        hashMap.put("birth_date", birth_datee);
        hashMap.put("full_name", fnamee);
        hashMap.put("city_name", city_namee);
        hashMap.put("is_completed" ,true) ;

        mref.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            userModel model = snapshot.getValue(userModel.class);
                            Gson gson = new Gson();
                            String ProfileData = gson.toJson(model);

                            Log.d("TAGEE", "onDataChange : " + ProfileData);
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .userLogin(ProfileData);

                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Try Again !! " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void BringImagePicker() {


        CropImage.activity()
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.RECTANGLE) //shaping the image
                .start(completeProfilePage.this);
    }

    @Override
    protected void onActivityResult(/*int requestCode, int resultCode, @Nullable Intent data*/
            int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mFilePathUri = result.getUri();

                if (tapped_image == 0) {
                    // nid front pic

                    front.setImageURI(mFilePathUri);

                } else {

                    back.setImageURI(mFilePathUri);
                }


                //sending data once  user select the image
                uploadPicToServer(mFilePathUri, tapped_image);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void uploadPicToServer(Uri mFilePathUri, int tapped_image) {
        if (mFilePathUri != null) {
            showMsg("Uploading Image...");
            //   final String randomName = UUID.randomUUID().toString();

            // PHOTO UPLOAD
            File newImageFile = new File(mFilePathUri.getPath());

            try {

                compressedImageFile = new Compressor(completeProfilePage.this)
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
            UploadTask filePath = mStorageReference.child(System.currentTimeMillis() + ".jpg").putBytes(imageData);

            filePath.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloaduri = uriTask.getResult();
                    nid_image_list[tapped_image] = downloaduri.toString();
                    //Log.d("TAG", "onSuccess lINK: " + nid_image_list[tapped_image]);
                    cancelMsg();
                    // mref.child("userProPic").setValue(downloaduri.toString());
                    //  mref.child("is_completed").setValue("false");


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    cancelMsg();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                }
            });


        } else {

            Toast.makeText(getApplicationContext(), "Please Choose A Valid Image", Toast.LENGTH_LONG).show();
        }
    }

    private void showMsg(String msg) {
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.show();

    }

    private void cancelMsg() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}
