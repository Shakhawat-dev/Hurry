package com.metacoders.hurry.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurry.R;
import com.metacoders.hurry.driverProfile.driverProfile;
import com.metacoders.hurry.model.modelForBid;
import com.metacoders.hurry.viewHolders.viewholderForBidList;

public class bidListPage extends AppCompatActivity {

    RecyclerView mrecyclerview ;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference mref ;
    String db  ;
    ImageView imageView ;
    ProgressBar progressBar ;

    TextView textView ;



    FirebaseRecyclerAdapter <modelForBid , viewholderForBidList> firebaseRecyclerAdapter  ;
    FirebaseRecyclerOptions<modelForBid>options ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_list_page);


        textView = findViewById(R.id.textOnBidList);
        progressBar = findViewById(R.id.progressBarONBidList);
        imageView = findViewById(R.id.imageOnBid);

        progressBar.setVisibility(View.VISIBLE);

        Intent o = getIntent();

        db = o.getStringExtra("POSTID");



        mrecyclerview = findViewById(R.id.recyclerviewInBidLIst) ;

        mref = FirebaseDatabase.getInstance().getReference("reqCarDb").child(db).child("bids");

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        mrecyclerview.setHasFixedSize(true);

        mref.keepSynced(true);


       loadDataToFirebase() ;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(linearLayoutManager.getItemCount()==0){
                    progressBar.setVisibility(View.GONE);

                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "You Do not Have Any Service", Toast.LENGTH_SHORT).show();


                }
                else {

                    progressBar.setVisibility(View.GONE);

                }

            }
        }, 3000);





    }


    public  void  loadDataToFirebase(){

options = new FirebaseRecyclerOptions.Builder<modelForBid>().setQuery(mref, modelForBid.class).build();
firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForBid, viewholderForBidList>(options) {
    @Override
    protected void onBindViewHolder(@NonNull viewholderForBidList viewholderForBidList, final  int i, @NonNull modelForBid model) {



        viewholderForBidList.setBidData(getApplicationContext() ,model.getTripID() , model.getDriverUid() , model.getDriverName()
         , model.getDriverCarModel() , model.getDriverRating() , model.getBidPrice() , model.getDrivercarcondition() , model.getDriverImageLink());



    }

    @NonNull
    @Override
    public viewholderForBidList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.bid_row_view_model, parent, false);
        final viewholderForBidList  viewholder  = new viewholderForBidList(mview);


        viewholderForBidList.setOnClickListener(new viewholderForBidList.Clicklistener() {
            @Override
            public void onItemClick(View view,final int position) {


        //        Toast.makeText(getApplicationContext() , "ITEM CLICKED" , Toast.LENGTH_LONG).show();

                Intent o = new Intent(getApplicationContext() , driverProfile.class);
                // feed some data to the driverpage
                o.putExtra("DRIVERUID" , getItem(position).getDriverUid()) ;
                o.putExtra("FARE" , getItem(position).getBidPrice());

                startActivity(o);


            }
        });



        return  viewholder;
    }
} ;
 mrecyclerview.setLayoutManager(linearLayoutManager) ;
firebaseRecyclerAdapter.startListening();
mrecyclerview.setAdapter(firebaseRecyclerAdapter);






    }




}
