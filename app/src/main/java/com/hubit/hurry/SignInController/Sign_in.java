package com.hubit.hurry.SignInController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hubit.hurry.R;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Sign_in extends AppCompatActivity {

    EditText phoneNumEdit;
    String phone;
    ImageButton nextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getting_phone_number);

        getSupportActionBar().hide();

        //init view
        nextBtn = findViewById(R.id.nextBtnmobileverification);

        phoneNumEdit = findViewById(R.id.phonenumEditText);

        // getting number from the edit text  ;

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((phoneNumEdit.getText().toString().length()) == 11) {


                    phone = "+88" + phoneNumEdit.getText().toString();

                    Intent i = new Intent(getApplicationContext(), Mobile_Varification.class);

                    i.putExtra("PHONE", phone);
                    phoneNumEdit.setText("");//seting the text to zero
                    phoneNumEdit.setHint("017784455...."); //setting the random hint
                    startActivity(i);

                } else {

                    FancyToast.makeText(Sign_in.this, "Number Format Invalid!!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser muser = FirebaseAuth.getInstance().getCurrentUser();



    }
}
