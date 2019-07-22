package com.metacoders.hurry.homeFragments;

import android.content.Context;
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
import com.metacoders.hurry.R;
import com.metacoders.hurry.Trip_Running_details;
import com.metacoders.hurry.homeFragments.bidFunction.bidListPage;
import com.metacoders.hurry.model.modelForCarRequest;
import com.metacoders.hurry.viewHolders.viewholdersForCurrentTrip;

public class pastTripFragment extends  Fragment {

        String  uid ;
        FirebaseAuth  mauth ;
        RecyclerView mrecyclerview  ;
        LinearLayoutManager linearLayoutManager ;
        DatabaseReference mref;

FirebaseRecyclerOptions<modelForCarRequest > options ;
FirebaseRecyclerAdapter<modelForCarRequest , viewholdersForCurrentTrip>firebaseRecyclerAdapter ;



        View view;

        public pastTripFragment() {

        }
        @Nullable

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            view = inflater.inflate(R.layout.past_trip_fragment, container, false);

            mref = FirebaseDatabase.getInstance().getReference("reqCarDb"); // db link


            mrecyclerview = view.findViewById(R.id.recycerviewForCurrentList) ;

            linearLayoutManager = new LinearLayoutManager(getContext());


            linearLayoutManager.setStackFromEnd(true);
            linearLayoutManager.setReverseLayout(true);

            mrecyclerview.setLayoutManager(linearLayoutManager) ;
            mrecyclerview.setHasFixedSize(true);

            mref.keepSynced(true);

     //       loadDataToFireBase()  ;




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
                            model.getReqDate() , model.getTripDetails() );






                }

                @NonNull
                @Override
                public viewholdersForCurrentTrip onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_trip_view_module, parent, false);
                    viewholdersForCurrentTrip viewholders = new viewholdersForCurrentTrip(iteamVIew);



                    viewholdersForCurrentTrip.setOnClickListener(new viewholdersForCurrentTrip.Clicklistener() {
                        @Override
                        public void onItemClick(View view, final  int postion) {

                            String DriverName  = getItem(postion).getDriverName() ;
                            String Status = getItem(postion).getStatus() ;


                            if (!DriverName.contains("drivernamee") && Status.equals("Pending"))
                            {
                                //go to the Trip details page to mark it done

                                Intent o = new Intent(getContext() , Trip_Running_details.class );
                                //carry data to their
                                o.putExtra("DRIVERNAME" , getItem(postion).getDriverName()) ;
                                o.putExtra("CARMODEL", getItem(postion).getCarModl()) ;
                                o.putExtra("FORMLOC", getItem(postion).getFromLoc()) ;
                                o.putExtra("TOLOC", getItem(postion).getToLoc()) ;
                                o.putExtra("FARE", getItem(postion).getFare()) ;
                                o.putExtra("TIME", getItem(postion).getTimeDate()) ;
                                o.putExtra("POSTID", getItem(postion).getPostId()) ;
                                o.putExtra("DRIVERUID", getItem(postion).getDriverId()) ;
                                o.putExtra("DRIVERNOTIFICATIONID", getItem(postion).getDriverNotificationID()) ;

                                startActivity(o);


                            }
                            else {

                                Intent o = new Intent(getContext() , bidListPage.class );
                                String postID  = getItem(postion).getPostId();


                                o.putExtra("POSTID" ,postID);

                                startActivity(o);

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


