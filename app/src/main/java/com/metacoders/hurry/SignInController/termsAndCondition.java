package com.metacoders.hurry.SignInController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurry.R;

import java.util.HashMap;
import java.util.Vector;

public class termsAndCondition extends AppCompatActivity {

    String fname , uid    , sname , name ;
    DatabaseReference mref ;
    FirebaseAuth mauth ;
    ImageButton nextButton ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        getSupportActionBar().hide();

        nextButton = findViewById(R.id.signin_btn) ;


        mauth = FirebaseAuth.getInstance();
        uid = mauth.getUid() ;
        mref = FirebaseDatabase.getInstance().getReference("usersProfile").child(uid);



        Intent i = getIntent();
        fname = i.getStringExtra("FNAME");
        sname = i.getStringExtra("SNAME");



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // registering user to server
                name = fname + " "+ sname;
                HashMap map = new HashMap();
                map.put("name", name) ;

                mref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {



                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                               // new AwesomeErrorDialog()


                            }
                        });




            }
        });








    }
}
