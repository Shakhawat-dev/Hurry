package com.hubit.hurry.SignInController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.hubit.hurry.Activity.homePage;
import com.hubit.hurry.Constants.constants;
import com.hubit.hurry.R;
import com.hubit.hurry.Utils.SharedPrefManager;
import com.hubit.hurry.model.userModel;
import com.onesignal.OneSignal;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import ir.samanjafari.easycountdowntimer.CountDownInterface;
import ir.samanjafari.easycountdowntimer.EasyCountDownTextview;


public class Mobile_Varification extends AppCompatActivity {

    private final static int RC_SIGN_IN = 2;
    EasyCountDownTextview countDownTextView;
    TextView resendText;
    String phone;
    ImageButton backButton;
    FirebaseAuth.AuthStateListener mAuthListener;
    SignInButton google_btn;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;
    ImageButton signInBtn;
    FirebaseUser mUser;
    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private OtpTextView editText;
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
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(Mobile_Varification.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile__varification);

        getSupportActionBar().hide();
        //receiving  phone number from  the previous activity
        Intent o = getIntent();
        phone = o.getStringExtra("PHONE");

        sendVerificationCode(phone);

        //init view
        countDownTextView = findViewById(R.id.easyCountDownTextview);
        resendText = findViewById(R.id.resendTv);
        backButton = findViewById(R.id.backbtn);
        signInBtn = findViewById(R.id.signin_btn);
        editText = findViewById(R.id.otp_view);
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

                if (firebaseAuth.getCurrentUser() != null) {
                    Intent i = new Intent(getApplicationContext(), homePage.class);
                    startActivity(i);
                    finish();
                }


            }
        };

        // setting on click

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String code = editText.getOTP();

                if ((code.isEmpty() || code.length() < 6)) {

                    //  editText.setError("Enter code...");
                    Toast.makeText(getApplicationContext(), "PLease Enter The 6 Digit Code Properly", Toast.LENGTH_SHORT)
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

    private void verifyCode(String code) {
        try {
            if (!code.isEmpty()) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
                signInWithCredential(credential);
            } else {
                Log.d("TAG", "verifyCode: " + code);
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            Log.d("TAG", "verifyCode: " + e.getMessage());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            mAuth = FirebaseAuth.getInstance();
                            checkUser(mAuth.getUid());
                            Log.d("TAG", "onComplete: " + mAuth.getUid());

                        } else {

                            Toast.makeText(Mobile_Varification.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }

                });
    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private void checkUser(String uid) {
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid);
        mref.keepSynced(true);
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // user exist
                    userModel model = snapshot.getValue(userModel.class);
                    Gson gson = new Gson();
                    String ProfileData = gson.toJson(model);

                    SharedPrefManager.getInstance(getApplicationContext())
                            .userLogin(ProfileData);

                    String playerId = "NULL";
                    try {
                        OneSignal.sendTag("ID", "USER");
                        playerId = OneSignal.getDeviceState().getUserId();
                        mref.child("notification_id").setValue(playerId);
                    } catch (Exception e) {
                    }


                    Intent intent = new Intent(Mobile_Varification.this, homePage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    // user doesnt exist
                    Intent intent = new Intent(Mobile_Varification.this, accountSetupPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error " + error, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}
