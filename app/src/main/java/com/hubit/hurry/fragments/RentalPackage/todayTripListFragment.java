package com.hubit.hurry.fragments.RentalPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hubit.hurry.Activity.PastTripDetails;
import com.hubit.hurry.Constants.constants;
import com.hubit.hurry.R;
import com.hubit.hurry.model.completedTestModel;
import com.hubit.hurry.model.modelForCarRequest;
import com.hubit.hurry.viewHolders.AllTripAdapter;
import com.hubit.hurry.viewHolders.viewholderForPastTrips;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class todayTripListFragment extends Fragment {

    String uid;
    FirebaseAuth mauth;
    RecyclerView mrecyclerview;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference mref;
    String date;
    List<modelForCarRequest> dataSet = new ArrayList<>();
    FirebaseRecyclerOptions<completedTestModel> options;
    FirebaseRecyclerAdapter<completedTestModel, viewholderForPastTrips> firebaseRecyclerAdapter;


    View view;

    public todayTripListFragment() {

    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        uid = FirebaseAuth.getInstance().getUid();
        view = inflater.inflate(R.layout.today_trip_fragment, container, false);

        mref = FirebaseDatabase.getInstance().getReference(constants.carRequestLink); // db link

        mrecyclerview = view.findViewById(R.id.recycerviewForCurrentList1);

        linearLayoutManager = new LinearLayoutManager(getContext());


        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        mrecyclerview.setLayoutManager(linearLayoutManager);
        mrecyclerview.setHasFixedSize(true);

        mref.keepSynced(true);

        //  loadDataToFireBase();

        //  loadTodayTrips();
        return view;
    }

    private void loadTodayTrips() {

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date = formatter.format(todayDate);
        Query query = mref.orderByChild("userId").equalTo(uid);
        query.keepSynced(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("TAG ", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    modelForCarRequest post = postSnapshot.getValue(modelForCarRequest.class);
                    dataSet.add(post);
                }
                setupRecylerVIew(dataSet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("The read failed: ", error.getMessage());
            }
        });


    }

    private void setupRecylerVIew(List<modelForCarRequest> dataSet) {
        List<modelForCarRequest> fillteredList = new ArrayList<>();
        fillteredList.clear();
        Log.d("TAG", "setupRecylerVIew: " + date);
        // clear up the dataset for today
        for (modelForCarRequest item : dataSet) {
            if (item.getReqDate().equals(date.toString())) {
                Log.e("TAG ", "");
                fillteredList.add(item);
            }
        }

        // Log.d("TAG", "setupRecylerVIew: " + fillteredList.size());
        mrecyclerview.setAdapter(new AllTripAdapter(fillteredList, getContext()));

    }

    private void loadDataToFireBase() {


        options = new FirebaseRecyclerOptions.Builder<completedTestModel>().setQuery(mref, completedTestModel.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<completedTestModel, viewholderForPastTrips>(options) {
            @Override
            protected void onBindViewHolder(@NonNull viewholderForPastTrips viewholdersForCurrentTrip, final int i, @NonNull completedTestModel model) {

                //     String  toLoc , String fromLoc , String timeDate , String status
                viewholdersForCurrentTrip.setDataToView(getContext(), model.getToLocation(), model.getFromLocation(), model.getCompleteDate()
                        , "Completed");

            }

            @NonNull
            @Override
            public viewholderForPastTrips onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View iteamVIew = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_trip_view_module, parent, false);
                final viewholderForPastTrips viewholders = new viewholderForPastTrips(iteamVIew);


                viewholderForPastTrips.setOnClickListener(new viewholderForPastTrips.Clicklistener() {
                    @Override
                    public void onItemClick(View view, final int pos) {


                        Intent o = new Intent(getContext(), PastTripDetails.class);
                        //carry data to their
                        o.putExtra("POSTID", getItem(pos).getTripID());

                        startActivity(o);


                    }
                });


                return viewholders;
            }
        };
        mrecyclerview.setLayoutManager(linearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mrecyclerview.setAdapter(firebaseRecyclerAdapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //  firebaseRecyclerAdapter.stopListening();

    }


    @Override
    public void onResume() {
        super.onResume();
         loadTodayTrips();
    }
}


