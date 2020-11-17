package com.metacoders.hurry.SignInController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurry.Activity.homePage;
import com.metacoders.hurry.Constants.constants;
import com.metacoders.hurry.R;

import java.util.HashMap;

public class termsAndCondition extends AppCompatActivity {

    String fname , uid    , sname , name ;
    DatabaseReference mref ;
    FirebaseAuth mauth ;
    ImageButton nextButton ;
    String phon ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        getSupportActionBar().hide();

        nextButton = findViewById(R.id.signin_btn) ;

        //TODO init nottificaiton id


        mauth = FirebaseAuth.getInstance();
        uid = mauth.getUid() ;
        mref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid);
        FirebaseUser  use  = FirebaseAuth.getInstance().getCurrentUser();

        phon = use.getPhoneNumber() ;





        Intent i = getIntent();
        fname = i.getStringExtra("FNAME");
        sname = i.getStringExtra("SNAME");




        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // registering user to server
                name = fname + " "+ sname;
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("userName", name) ;
                map.put("phone", phon) ;
                map.put("isBanned" ,"NO") ;
                map.put("isFined" , "0") ;
                map.put("userFined" , "0") ;
                map.put("userTripList" , "0") ;
                map.put("userTotalSpent" , "0") ;
                map.put("userTripCount", "0") ;
                //TODO ADD NOTIFICATION
                map.put("notification_id", "test") ;



                mref.updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Intent  i = new Intent(getApplicationContext() , homePage.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: "+ e.getMessage());
                    }
                });




            }
        });








    }
}
