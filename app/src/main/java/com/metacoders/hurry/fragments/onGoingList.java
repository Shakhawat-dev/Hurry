package com.metacoders.hurry.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurry.R;
import com.metacoders.hurry.Activity.Trip_Running_details;
import com.metacoders.hurry.model.modelForCarRequest;
import com.metacoders.hurry.viewHolders.viewholdersForCurrentTrip;

public class onGoingList extends Fragment {


    RecyclerView mrecyclerview  ;
    LinearLayoutManager linearLayoutManager ;
    DatabaseReference mref;

    FirebaseRecyclerOptions<modelForCarRequest > options ;
    FirebaseRecyclerAdapter<modelForCarRequest , viewholdersForCurrentTrip>firebaseRecyclerAdapter ;


    View view;

    public onGoingList() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_ongoing_list, container, false);

        mref = FirebaseDatabase.getInstance().getReference("reqCarDb"); // db link


        mrecyclerview = view.findViewById(R.id.currentList) ;

        linearLayoutManager = new LinearLayoutManager(getContext());


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        mrecyclerview.setHasFixedSize(true);

        mref.keepSynced(true);

        loadDataToFireBase()  ;




        return view ;
    }

    private void loadDataToFireBase() {

        options = new FirebaseRecyclerOptions.Builder<modelForCarRequest>().setQuery(mref , modelForCarRequest.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForCarRequest, viewholdersForCurrentTrip>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholdersForCurrentTrip viewholdersForCurrentTrip,final int i, @NonNull modelForCarRequest model) {


                viewholdersForCurrentTrip.setDataToView(getContext() ,
                        model.getPostId() , model.getUserId()  ,model.getUserNotificationID()   , model.getDriverId()  , model.getDriverNotificationID() ,
                        model.getToLoc() , model.getFromLoc() ,  model.getTimeDate() , model.getCarModl() , model.getDriverName() ,
                        model.getStatus()  , model.getCarLicNum() , model.getFare() , model.getCarType() ,
                        model.getReqDate() , model.getTripDetails() , model.getReturnTimee() );






            }

            @NonNull
            @Override
            public viewholdersForCurrentTrip onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_trip_view_module, parent, false);
                final viewholdersForCurrentTrip viewholders = new viewholdersForCurrentTrip(iteamVIew);



                viewholdersForCurrentTrip.setOnClickListener(new viewholdersForCurrentTrip.Clicklistener() {
                    @Override
                    public void onItemClick(View view, final  int postion) {

                        String DriverName  = getItem(postion).getDriverName() ;
                        String Status = getItem(postion).getStatus() ;

            // TODo Remember there is  4 status now


                        if ( Status.equals("Driver Found") )
                        {
                            //go to the Trip details page to mark it done

                            Intent o = new Intent(getContext() , Trip_Running_details.class );
                            //carry data to their
                            o.putExtra("STATUS", getItem(postion).getStatus()) ;
                            o.putExtra("DRIVERNAME" , getItem(postion).getDriverName()) ;
                            o.putExtra("CARMODEL", getItem(postion).getCarModl()) ;
                            o.putExtra("FORMLOC", getItem(postion).getFromLoc()) ;
                            o.putExtra("TOLOC", getItem(postion).getToLoc()) ;
                            o.putExtra("FARE", getItem(postion).getFare()) ;
                            o.putExtra("TIME", getItem(postion).getTimeDate()) ;
                            o.putExtra("POSTID", getItem(postion).getPostId()) ;
                            o.putExtra("DRIVERUID", getItem(postion).getDriverId()) ;
                            o.putExtra("DRIVERNOTIFICATIONID", getItem(postion).getDriverNotificationID()) ;
                            o.putExtra("DESC", getItem(postion).getTripDetails()) ;
                            o.putExtra("TYPE" , getItem(postion).getRideType()) ;

                            startActivity(o);


                        }

                        else if (Status.contains("Bid Found")) {

                            Intent o = new Intent(getContext() , bidListPage.class );
                            String postID  = getItem(postion).getPostId();


                            o.putExtra("POSTID" ,postID);

                            startActivity(o);

                        }

                        else {
                            Toast.makeText(getContext() , "No One Has Bidded On Your Request"  , Toast.LENGTH_SHORT).show();
                        }









                    }
                });


                return viewholders;
            }
        };
        mrecyclerview.setLayoutManager(linearLayoutManager) ;
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);






    }


}