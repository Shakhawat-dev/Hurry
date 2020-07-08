package com.metacoders.hurry.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.metacoders.hurry.Constants.constants;
import com.metacoders.hurry.R;
import com.metacoders.hurry.model.driverProfileModel;
import com.metacoders.hurry.model.modelForCarRequest;
import com.metacoders.hurry.model.userModel;
import com.shuhart.stepview.StepView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Trip_Running_details extends AppCompatActivity {

String driverName , carModel  , fromLoc , toLoc , fare , time , postID , driverID   , driverNottificationID ,
        tripDetails ,status , triptype ,  driverFine , driverTotalTrip= "0" , driverIncome= "0" , driverTripCountThisMOn= "0" ,
        driverLifeTimeIncome = "0";
EditText description ;
String  userTotalTrip  = "0", userFined , userSpent  = "0" ;

Button submit ,cancel   ;
MaterialCardView  payInfoCard   , driverInfoCard ;
RatingBar mRateBar ;
String uid = "TEST" ;
TextView DriverNAME , CARMODEL ,drivername  , FROMLOC  ,TOLOC , FARE ,TIME , POSTID , DRIVERID , DRIVERNOTTIFICATIONID , descTv  , typeTv
        , fareTv ;
StepView stepView ;
String driverNewLifetimeEarn , driverNewThisMonthEarn  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip__running_details);

        getSupportActionBar().setTitle("Trip Details ");


        Intent i = getIntent();
        driverName =  i.getStringExtra("DRIVERNAME" ) ;
        carModel =  i.getStringExtra("CARMODEL")  ;
        fromLoc =  i.getStringExtra("FORMLOC") ;
        status = i.getStringExtra("STATUS") ;
        toLoc =  i.getStringExtra("TOLOC") ;
        fare =  i.getStringExtra("FARE") ;
        time =  i.getStringExtra("TIME");
        postID =  i.getStringExtra("POSTID") ;
        driverID  =  i.getStringExtra("DRIVERUID")  ;
        driverNottificationID  =  i.getStringExtra("DRIVERNOTIFICATIONID");
        tripDetails = i.getStringExtra("DESC") ;
        triptype = i.getStringExtra("TYPE") ;









        //init The View ;
        drivername= findViewById(R.id.driverNameinTripDetails);
    //   DriverNAME =findViewById(R.id.driverNameTripDeatils)  ;
     //  CARMODEL=findViewById(R.id.carModelTripDetails)  ;
        FROMLOC =findViewById(R.id.locationFromTripDetails) ;
        TOLOC=findViewById(R.id.locationToTripDetalis) ;
        FARE=findViewById(R.id.priceViewInTripDetails) ;
        TIME =findViewById(R.id.dateOfTripDetails);
        description = findViewById(R.id.feedBackDetails);
        submit = findViewById(R.id.sumbutBTnTripdetails);
        stepView  = findViewById(R.id.step_view);
        cancel = findViewById(R.id.cancelBTnTripdetails);
        payInfoCard =findViewById(R.id.advacePayInfoCard) ;
        driverInfoCard = findViewById(R.id.driverInfoCard) ;
        mRateBar= findViewById(R.id.ratingBarBidTripDetails);
        fareTv = findViewById(R.id.fareTv);
        typeTv = findViewById(R.id.typeTv);
        descTv = findViewById(R.id.descTv);







        //determine what to show

        if (status.equals("Driver Found"))
        {
            //TODO Bring The Advance Payment App Tab

            payInfoCard.setVisibility(View.VISIBLE);
            stepView.setVisibility(View.VISIBLE);
            driverInfoCard.setVisibility(View.GONE);
        }
        else {
            //TODO Show Dirver Details
             driverInfoCard.setVisibility(View.VISIBLE);
             payInfoCard.setVisibility(View.GONE);
             stepView.setVisibility(View.GONE);


        }

        //setting the data to the views ;

        //    DriverNAME.setText(driverName);
            drivername.setText(driverName);
        // CARMODEL.setText(carModel);
            FROMLOC.setText(fromLoc);
            TOLOC.setText(toLoc);
            TIME.setText(time);
            FARE.setText(fare);
            descTv.setText(tripDetails);
            fareTv.setText(fare);
            typeTv.setText(triptype);




        // setting the step view  listeners

        stepView.getState()
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        setAnimationtoStepVIews();


        // listening for submit button click
        payInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext() , MakeAdvancePaymentActivity.class);
                i.putExtra("ID" , postID) ;
                startActivity(i);


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startCancel() ;


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String date ="016/11/16 12:08:43"; //2016/11/16 12:08:43

                //1st Upload It To The Driver profile
                DatabaseReference driverRef  = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(driverID)
                        .child(constants.succfulllistDir);

                final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid)
                        .child(constants.succfulllistDir);

                final HashMap<String, String> map = new HashMap<>();
                map.put("tripID"  , postID ) ;
                map.put("status" , "Completed") ;
                map.put("fromLocation" ,fromLoc );
                map.put("toLocation" , toLoc) ;
                map.put("fareGained" , fare) ;
                map.put("CompleteDate" , date) ;

                driverRef.child(postID).setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                // Writing in user dir
                                userRef.child(postID).setValue(map)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {


                                                DatabaseReference mreff = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(driverID);

                                                final Map<String, Object> updates = new HashMap<String,Object>();
                                                updates.put("driverEarnedLifeLong" ,driverNewLifetimeEarn ) ;
                                                updates.put("driverEarnedThisMonth" , driverNewThisMonthEarn) ;
                                                updates.put("totalRides" , driverTotalTrip) ;
                                                updates.put("tripCounter", driverTripCountThisMOn) ;


                                                mreff.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                     DatabaseReference updateUserref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid);
                                                        Map<String, Object> updatesuserData = new HashMap<String,Object>();

                                                        updatesuserData.put("userTotalSpent" ,userSpent) ;
                                                        updatesuserData.put("userTripCount" , userTotalTrip) ;


                                                        updateUserref.updateChildren(updatesuserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                //TODO DONE ! ! UPDATING DATA
                                                                Toast.makeText(getApplicationContext() , "Error" , Toast.LENGTH_SHORT)
                                                                        .show();


                                                            }
                                                        })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {



                                                                    }
                                                                }) ;



                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {


                                                    }
                                                });



                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {


                                            }
                                        }) ;

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {



                            }
                        }) ;



            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference(constants.carRequestLink).child(postID);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        modelForCarRequest   model =  dataSnapshot.getValue(modelForCarRequest.class) ;

                        if(model.getStatus().equals("COMPLETED"))
                        {
                            //Trigger Dialogue
                            Toast.makeText(getApplicationContext() , "All Ready Completed" , Toast.LENGTH_LONG)
                                    .show();

                        }

                        else {

                            startCancel();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        downloadDriverData();
        dwldUserData();
    }

    private void startCancel() {


        DatabaseReference mrrf = FirebaseDatabase.getInstance().getReference(constants.carRequestLink).child(postID);
        final Map<String, Object> updatesuserMap = new HashMap<String,Object>();
        updatesuserMap.put("status" ,"CANCELED BY USER" );

         mrrf.updateChildren(updatesuserMap)
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {

                         DatabaseReference  updateRef = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid);

                         final Map<String, Object> updatesFineduserMap = new HashMap<String,Object>();

                         // adding fine to user ;

                         userFined = String.valueOf(Integer.valueOf(userFined) + 100 )  ;

                String n = userFined ;

                         updatesFineduserMap.put("userFined" , n ) ;

                         updateRef.updateChildren(updatesFineduserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {


                                 Toast.makeText(getApplicationContext()   ,  " DOne " , Toast.LENGTH_SHORT)
                                 .show();
                             }
                         })
                                 .addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {




                                     }
                                 });




                     }
                 }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {

             }
         })  ;




    }




    public  void downloadDriverData(){
        // Download Driver Data
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(driverID);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                driverProfileModel model = dataSnapshot.getValue(driverProfileModel.class) ;

                driverFine  = model.getDriverFined() ;
                driverTotalTrip = model.getTotalRides() ;
                driverIncome = model.getDriverEarnedThisMonth() ;
                driverTripCountThisMOn  = model.getTripCounter() ;

                driverLifeTimeIncome = model.getDriverEarnedLifeLong() ;

                driverNewLifetimeEarn = String.valueOf(Integer.valueOf(driverLifeTimeIncome)+Integer.valueOf(fare) ) ;
                driverNewThisMonthEarn = String.valueOf(Integer.valueOf(driverIncome)+ Integer.valueOf(fare)) ;
                driverTotalTrip = String.valueOf(Integer.valueOf(driverTotalTrip) + 1 ) ;
                driverTripCountThisMOn = String.valueOf(Integer.valueOf(driverTripCountThisMOn) + 1 ) ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext() , "Error" + databaseError.getMessage() , Toast.LENGTH_SHORT)
                        .show();

            }
        });






    }

    public  void dwldUserData(){

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child("TEST");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userModel  model =  dataSnapshot.getValue(userModel.class) ;

                    userFined = model.getUserFined();
                    userTotalTrip = model.getUserTripCount() ;
                    userSpent = model.getUserTotalSpent() ;


                userSpent = String.valueOf(Integer.valueOf(userSpent)+ Integer.valueOf(fare) ) ;
                userTotalTrip = String.valueOf(Integer.valueOf(userTotalTrip)+ 1 ) ;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext() , "Error" + databaseError.getMessage() , Toast.LENGTH_SHORT)
                        .show();
            }
        });


    }

    public  void setAnimationtoStepVIews(){

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepView.go(0, true);
            }
        }, 400);

        final Handler handsler = new Handler();
        handsler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepView.go(1, true);
            }
        }, 800);

        final Handler handdsler = new Handler();
        handdsler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepView.go(2, true);
            }
        }, 1200);



        final Handler handdr = new Handler();
        handdr.postDelayed(new Runnable() {
            @Override
            public void run() {
                stepView.go(3, true);
            }
        }, 1500);



    }




}
