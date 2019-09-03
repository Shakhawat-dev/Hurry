package com.metacoders.hurry.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurry.PastTripDetails;
import com.metacoders.hurry.R;
import com.metacoders.hurry.model.modelForCarRequest;
import com.metacoders.hurry.viewHolders.viewholderForPastTrips;


public class pastTripListFragment extends  Fragment {

        String  uid ;
        FirebaseAuth  mauth ;
        RecyclerView mrecyclerview  ;
        LinearLayoutManager linearLayoutManager ;
        DatabaseReference mref;

FirebaseRecyclerOptions<modelForCarRequest > options ;
FirebaseRecyclerAdapter<modelForCarRequest , viewholderForPastTrips>firebaseRecyclerAdapter ;



        View view;

        public pastTripListFragment() {

        }
        @Nullable

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            view = inflater.inflate(R.layout.past_trip_fragment, container, false);

            mref = FirebaseDatabase.getInstance().getReference("test"); // db link


            mrecyclerview = view.findViewById(R.id.recycerviewForCurrentList) ;

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
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<modelForCarRequest, viewholderForPastTrips>(options) {
                @Override
                protected void onBindViewHolder(@NonNull viewholderForPastTrips viewholdersForCurrentTrip,final int i, @NonNull modelForCarRequest model) {


                    viewholdersForCurrentTrip.setDataToView(getContext() ,
                            model.getPostId() , model.getUserId()  ,model.getUserNotificationID()   , model.getDriverId()  , model.getDriverNotificationID() ,
                              model.getToLoc() , model.getFromLoc() ,  model.getTimeDate() , model.getCarModl() , model.getDriverName() ,
                             model.getStatus()  , model.getCarLicNum() , model.getFare() , model.getCarType() ,
                            model.getReqDate() , model.getTripDetails(), model.getReturnTimee());




                }

                @NonNull
                @Override
                public viewholderForPastTrips onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_trip_view_module, parent, false);
                    final viewholderForPastTrips viewholders = new viewholderForPastTrips(iteamVIew);



                    viewholderForPastTrips.setOnClickListener(new viewholderForPastTrips.Clicklistener() {
                        @Override
                        public void onItemClick(View view, final  int pos) {

                            String DriverName  = getItem(pos).getDriverName() ;
                            String Status = getItem(pos).getStatus() ;


                                //go to the Trip details page to mark it done



                      //      Toast.makeText(getContext() , "No One Has Bidded On Your Request"  , Toast.LENGTH_LONG).show();

                                Intent o = new Intent(getContext() , PastTripDetails.class );
                                //carry data to their
                                o.putExtra("DRIVERNAME" , getItem(pos).getDriverName()) ;
                                o.putExtra("CARMODEL", getItem(pos).getCarModl()) ;
                                o.putExtra("FORMLOC", getItem(pos).getFromLoc()) ;
                                o.putExtra("TOLOC", getItem(pos).getToLoc()) ;
                                o.putExtra("FARE", getItem(pos).getFare()) ;
                                o.putExtra("TIME", getItem(pos).getTimeDate()) ;
                                o.putExtra("POSTID", getItem(pos).getPostId()) ;
                                o.putExtra("DRIVERUID", getItem(pos).getDriverId()) ;
                                o.putExtra("DRIVERNOTIFICATIONID", getItem(pos).getDriverNotificationID()) ;

                                startActivity(o);















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


