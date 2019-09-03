package com.metacoders.hurry.SignInController;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.metacoders.hurry.R;
import com.metacoders.hurry.homeFragments.homePage;

public class accountSetupPage extends AppCompatActivity {

    EditText  fnameIn , snameIn ;
    String    fname , sname ;
    ImageButton imageButton ;
    FirebaseAuth mauth ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setup_page);

        fnameIn = findViewById(R.id.fnameIN);
        snameIn = findViewById(R.id.snameIN);
        imageButton = findViewById(R.id.nextBtn) ;

        mauth = FirebaseAuth.getInstance();




        fname = fnameIn.getText().toString();
        sname = snameIn.getText().toString();


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i  = new Intent(getApplicationContext()  , termsAndCondition.class);

                i.putExtra("FNAME", fname);
                i.putExtra("SNAME" , sname);

                startActivity(i);

            }
        });


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
