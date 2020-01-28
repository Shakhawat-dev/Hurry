package com.metacoders.hurry.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurry.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MakeAdvancePaymentActivity extends AppCompatActivity {
     TextInputEditText amountin  ,  trxidin ;
    Button mbtn ;
    String postID ;
    String uid ;
    RadioGroup acRadioGroup , carSeatNumber ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        //TODO connect the views and plz get the value in string and upload it to the transaction folder

       uid = "TEST" ;

        acRadioGroup = findViewById(R.id.numberOFPPl) ;
        mbtn = findViewById(R.id.submitBtn);
        amountin = findViewById(R.id.amountEdit) ;
        trxidin = findViewById(R.id.trxID) ;

        Intent i = getIntent();

       postID  = i.getStringExtra("ID") ;




        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amt , tx , paymentType ;

                int i1 = acRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(i1) ;

                amt = amountin.getText().toString();
                tx = trxidin.getText().toString();
                paymentType =radioButton.getText().toString() ;



                if(!TextUtils.isEmpty(amt) ||!TextUtils.isEmpty(tx) )
                {
                    uploadTransaction(amt , tx ,paymentType);

                }


            }
        });
    }



public  void  uploadTransaction(String amt, String tx, String paymentType){
    final DatabaseReference transaction = FirebaseDatabase.getInstance().getReference("transaction_List").child(postID);

   String date  = getDateTime();


    HashMap  map = new HashMap();
    map.put("userUid" , uid) ;
    map.put("amountPaid",amt ) ;
    map.put("trxID", tx) ;
    map.put("tripId",postID ) ;
    map.put("time" , date) ;
    map.put("payment_type" , paymentType) ;

    transaction.setValue(map)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(getApplicationContext() , "DONE!!" , Toast.LENGTH_SHORT)
                            .show();
                    //  finish();

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                }
            }) ;




}
public  void sentNotificationToAdmin(){
}
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
