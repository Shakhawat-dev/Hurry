package com.hubit.hurry.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hubit.hurry.Constants.constants;
import com.hubit.hurry.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MakeAdvancePaymentActivity extends AppCompatActivity {
    TextInputEditText amountin, trxidin;
    Button mbtn;
    String postID;
    String uid;
    RadioGroup acRadioGroup, carSeatNumber;
    String amt, tx, paymentType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);


        uid = FirebaseAuth.getInstance().getUid();

        acRadioGroup = findViewById(R.id.numberOFPPl);
        mbtn = findViewById(R.id.submitBtn);
        amountin = findViewById(R.id.amountEdit);
        trxidin = findViewById(R.id.trxID);

        Intent i = getIntent();

        postID = i.getStringExtra("ID");

        String amount = i.getStringExtra("AMT");
        amountin.setText(amount);


        mbtn.setOnClickListener(view -> {

            int i1 = acRadioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(i1);
            amt = amountin.getText().toString();
            tx = trxidin.getText().toString();
            paymentType = radioButton.getText().toString();


            if ( tx.length() != 0 ) {
                uploadTransaction(amt, tx, paymentType);

            }else {
                trxidin.setError("Cant Be Empty");
                Toast.makeText(getApplicationContext() , "Please Fill The Trx Id" , Toast.LENGTH_LONG).show();
            }


        });
    }


    public void uploadTransaction(String amt, final String trx, String paymentType) {
        final DatabaseReference transaction = FirebaseDatabase.getInstance().getReference("transaction_List").child(postID);
        final DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.carRequestLink).child(postID)
                .child("transId");

        String date = getDateTime();


        HashMap map = new HashMap();
        map.put("userUid", uid);
        map.put("amountPaid", amt);
        map.put("trxID", trx);
        map.put("tripId", postID);
        map.put("time", date);
        map.put("payment_type", paymentType);
        map.put("status", "pending");


        transaction.setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//
//                    Toast.makeText(getApplicationContext() , "DONE!!" , Toast.LENGTH_SHORT)
//                            .show();
//                     finish();
                        //trx
                        mref.setValue("NULL").addOnSuccessListener(aVoid -> {

                            Toast.makeText(getApplicationContext(), "DONE!!", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }).addOnFailureListener(e -> {

                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });


    }

    public void sentNotificationToAdmin() {
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
