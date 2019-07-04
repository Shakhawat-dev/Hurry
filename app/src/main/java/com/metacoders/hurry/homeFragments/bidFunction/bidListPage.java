package com.metacoders.hurry.homeFragments.bidFunction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurry.R;
import com.metacoders.hurry.model.modelForBid;
import com.metacoders.hurry.viewHolders.viewholderForBidList;

public class bidListPage extends AppCompatActivity {

    RecyclerView mrecyclerview ;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference mref ;

    FirebaseRecyclerAdapter <modelForBid , viewholderForBidList> firebaseRecyclerAdapter  ;
    FirebaseRecyclerOptions<modelForBid>options ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_list_page);



        mrecyclerview = findViewById(R.id.recyclerviewInBidLIst) ;

        mref = FirebaseDatabase.getInstance().getReference("TempBidRepo");

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        mrecyclerview.setHasFixedSize(true);

        mref.keepSynced(true);


        loadDataToFirebase() ;



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
        viewholderForBidList  viewholderForBidList  = new viewholderForBidList(mview);


        return  viewholderForBidList;
    }
} ;

firebaseRecyclerAdapter.startListening();
mrecyclerview.setAdapter(firebaseRecyclerAdapter);






    }


}
