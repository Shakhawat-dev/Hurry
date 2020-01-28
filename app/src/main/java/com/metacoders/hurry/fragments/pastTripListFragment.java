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
import com.metacoders.hurry.Activity.PastTripDetails;
import com.metacoders.hurry.Constants.constants;
import com.metacoders.hurry.R;
import com.metacoders.hurry.model.completedTestModel;
import com.metacoders.hurry.viewHolders.viewholderForPastTrips;


public class pastTripListFragment extends  Fragment {

        String  uid ;
        FirebaseAuth  mauth ;
        RecyclerView mrecyclerview  ;
        LinearLayoutManager linearLayoutManager ;
        DatabaseReference mref;

FirebaseRecyclerOptions<completedTestModel > options ;
FirebaseRecyclerAdapter<completedTestModel, viewholderForPastTrips>firebaseRecyclerAdapter ;



        View view;

        public pastTripListFragment() {

        }
        @Nullable

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



            uid = "TEST" ;
            view = inflater.inflate(R.layout.past_trip_fragment, container, false);

            mref = FirebaseDatabase.getInstance().getReference(constants.userProfileDb).child(uid).child("compeletedList"); // db link


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

            options = new FirebaseRecyclerOptions.Builder<completedTestModel>().setQuery(mref , completedTestModel.class).build();
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<completedTestModel, viewholderForPastTrips>(options) {
                @Override
                protected void onBindViewHolder(@NonNull viewholderForPastTrips viewholdersForCurrentTrip,final int i, @NonNull completedTestModel model) {

               //     String  toLoc , String fromLoc , String timeDate , String status
                    viewholdersForCurrentTrip.setDataToView(getContext() , model.getToLocation() , model.getFromLocation() , model.getCompleteDate()
                     , "Completed");

                }

                @NonNull
                @Override
                public viewholderForPastTrips onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_trip_view_module, parent, false);
                    final viewholderForPastTrips viewholders = new viewholderForPastTrips(iteamVIew);



                    viewholderForPastTrips.setOnClickListener(new viewholderForPastTrips.Clicklistener() {
                        @Override
                        public void onItemClick(View view, final  int pos) {


                          //  String Status = getItem(pos).getTripID() ;


                                //go to the Trip details page to mark it done

                      //      Toast.makeText(getContext() , "No One Has Bidded On Your Request"  , Toast.LENGTH_LONG).show();

                                Intent o = new Intent(getContext() , PastTripDetails.class );
                                //carry data to their
                                o.putExtra("POSTID", getItem(pos).getTripID()) ;


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


