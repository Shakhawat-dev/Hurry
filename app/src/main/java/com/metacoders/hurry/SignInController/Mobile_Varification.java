package com.metacoders.hurry.SignInController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.metacoders.hurry.R;
import com.metacoders.hurry.homeFragments.homePage;
import java.util.concurrent.TimeUnit;
import in.aabhasjindal.otptextview.OtpTextView;
import ir.samanjafari.easycountdowntimer.CountDownInterface;
import ir.samanjafari.easycountdowntimer.EasyCountDownTextview;


public class Mobile_Varification extends AppCompatActivity {

    EasyCountDownTextview countDownTextView ;
    TextView  resendText ;
    String phone ;
    ImageButton backButton ;

    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private final static  int RC_SIGN_IN =2 ;
    FirebaseAuth.AuthStateListener mAuthListener ;
    SignInButton google_btn ;
    GoogleApiClient mGoogleApiClient ;
    private OtpTextView editText;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks ;
    ImageButton signInBtn ;
    FirebaseUser mUser ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__varification);

        getSupportActionBar().hide();
        //receiving  phone number from  the previous activity
        Intent  o = getIntent();
        phone = o.getStringExtra("PHONE") ;

        sendVerificationCode(phone);
        //init view
        countDownTextView = findViewById(R.id.easyCountDownTextview) ;
        resendText = findViewById(R.id.resendTv) ;
        backButton = findViewById(R.id.backbtn);
        signInBtn = findViewById(R.id.signin_btn) ;
        editText = findViewById(R.id.otp_view) ;
        progressBar = findViewById(R.id.progrssBar);





        //setting my views
        countDownTextView.setVisibility(View.VISIBLE);
        resendText.setVisibility(View.GONE);






        countDownTextView.setOnTick(new CountDownInterface() {
            @Override
            public void onTick(long time) {

            }

            @Override
            public void onFinish() {

                countDownTextView.setVisibility(View.GONE);
                resendText.setVisibility(View.VISIBLE);


            }
        });


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() !=null){
                    Intent i = new Intent(getApplicationContext() , homePage.class);
                    startActivity(i);
                }



            }
        };



        // setting on click

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String code = editText.getOTP();

                if ((code.isEmpty() || code.length() < 6)){

                    //  editText.setError("Enter code...");
                    Toast.makeText(getApplicationContext() , "PLease Enter The 6 Digit Code Properly" , Toast.LENGTH_SHORT)
                            .show();
                }
               // progressBar.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                    verifyCode(code);

            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        });

        resendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendText.setVisibility(View.GONE);
                countDownTextView.setVisibility(View.VISIBLE);
                sendVerificationCode(phone);
                countDownTextView.startTimer();
            }
        });





    }


    private void verifyCode(String code){
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
            signInWithCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Mobile_Varification.this,"GOOD", Toast.LENGTH_LONG).show();

                           // Intent intent = new Intent(Mobile_Varification.this, homePage.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //intent.putExtra("GOOGLE" , "Phone");
                           // startActivity(intent);
                          //  finish();

                        } else {

                            Toast.makeText(Mobile_Varification.this,"Eror: "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){


                verifyCode(code);
            }

            else {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Mobile_Varification.this,"Error: wrong Code  ", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(Mobile_Varification.this, e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };





}
