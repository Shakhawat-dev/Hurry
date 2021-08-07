package com.hubit.hurry.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hubit.hurry.Constants.constants;
import com.hubit.hurry.R;
import com.hubit.hurry.driverProfile.driverProfile;
import com.hubit.hurry.model.driverProfileModel;
import com.hubit.hurry.model.modelForBid;
import com.hubit.hurry.model.modelForCarRequest;
import com.hubit.hurry.viewHolders.viewholderForBidList;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;

public class bidListPage extends AppCompatActivity {

    RecyclerView mrecyclerview;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference mref;
    String db;
    ImageView imageView;
    ProgressBar progressBar;
    TextView textView;
    FirebaseRecyclerAdapter<modelForBid, viewholderForBidList> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<modelForBid> options;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_list_page);
        dialog = new ProgressDialog(bidListPage.this);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + "Bid List" + "</font>"));
        textView = findViewById(R.id.textOnBidList);
        progressBar = findViewById(R.id.progressBarONBidList);
        imageView = findViewById(R.id.imageOnBid);

        progressBar.setVisibility(View.VISIBLE);

        Intent o = getIntent();

        db = o.getStringExtra("POSTID");


        mrecyclerview = findViewById(R.id.recyclerviewInBidLIst);

        mref = FirebaseDatabase.getInstance().getReference("reqCarDb").child(db).child("bids");

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        mrecyclerview.setLayoutManager(linearLayoutManager);
        mrecyclerview.setHasFixedSize(true);

        mref.keepSynced(true);


        loadDataToFirebase();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (linearLayoutManager.getItemCount() == 0) {
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "You Do not Have Any Service", Toast.LENGTH_SHORT).show();


                } else {

                    progressBar.setVisibility(View.GONE);

                }

            }
        }, 4500);


    }


    public void loadDataToFirebase() {

        options = new FirebaseRecyclerOptions.Builder<modelForBid>().setQuery(mref, modelForBid.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForBid, viewholderForBidList>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholderForBidList viewholderForBidList, final int postion, @NonNull modelForBid model) {


                viewholderForBidList.setBidData(getApplicationContext(), model.getTripID(), model.getDriverUid(), model.getDriverName()
                        , model.getDriverCarModel(), model.getDriverRating(), model.getBidPrice(), model.getDrivercarcondition(), model.getDriverImageLink());

                viewholderForBidList.acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*
                         add the bid to the data
                         */
                        addBidToData(getItem(postion).getBidPrice(), getItem(postion).getDriverUid());

                    }
                });
            }

            @NonNull
            @Override
            public viewholderForBidList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressBar.setVisibility(View.GONE);
                View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.bid_row_view_model, parent, false);
                final viewholderForBidList viewholder = new viewholderForBidList(mview);


                viewholderForBidList.setOnClickListener(new viewholderForBidList.Clicklistener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                        Intent o = new Intent(getApplicationContext(), driverProfile.class);
                        // feed some data to the driverpage
                        o.putExtra("DRIVERUID", getItem(position).getDriverUid());
                        o.putExtra("FARE", getItem(position).getBidPrice());

                        startActivity(o);


                    }
                });


                return viewholder;
            }
        };
        mrecyclerview.setLayoutManager(linearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);
        //   Log.d("TAG", "loadDataToFirebase: " +db);

    }

    private void addBidToData(final String bidPrice, String driverUid) {
        Context context;

        dialog.setMessage("Accepting Bid...");
        dialog.show();
        final DatabaseReference mref2 = FirebaseDatabase.getInstance().getReference("reqCarDb")
                .child(db);
        DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink)
                .child(driverUid);

        driverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                driverProfileModel profile = snapshot.getValue(driverProfileModel.class);
                // Log.d("TAG", "addBidToData: " + profile.getAcType());
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("carLicNum", profile.getCarLic());
                map.put("carModl", profile.getCarModel());
                map.put("carType", profile.getCarType());
                map.put("driverId", profile.getUserID());
                map.put("driverName", profile.getDriverName());
                map.put("status", "Accepted");
                map.put("driverNotificationID", profile.getDriverNotificationID());
                map.put("fare", bidPrice);

                mref2.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        gotoTripDetailsPage(db);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        FancyToast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                FancyToast.makeText(getApplicationContext(), "Error : " + error.getMessage(), Toast.LENGTH_LONG, FancyToast.ERROR, false).show();

            }
        });


    }

    private void gotoTripDetailsPage(String db) {

        DatabaseReference reqDb = FirebaseDatabase.getInstance().getReference("reqCarDb").child(db);

        reqDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    modelForCarRequest model = snapshot.getValue(modelForCarRequest.class);
                    Intent o = new Intent(getApplicationContext(), Trip_Running_details.class);
                    //carry data to their
                    o.putExtra("STATUS", model.getStatus());
                    o.putExtra("DRIVERNAME", model.getDriverName());
                    o.putExtra("CARMODEL", model.getCarModl());
                    o.putExtra("FORMLOC", model.getFromLoc());
                    o.putExtra("TOLOC", model.getToLoc());
                    o.putExtra("FARE", model.getFare());
                    o.putExtra("TIME", model.getTimeDate());
                    o.putExtra("POSTID", model.getPostId());
                    o.putExtra("DRIVERUID", model.getDriverId());
                    o.putExtra("DRIVERNOTIFICATIONID", model.getDriverNotificationID());
                    o.putExtra("DESC", model.getTripDetails());
                    o.putExtra("TYPE", model.getRideType());
                    o.putExtra("TRANS", model.getTransId());
                    o.putExtra("MODEL", model);

                    startActivity(o);

                    dialog.dismiss();
                    finish();
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
