package com.metacoders.hurry.viewHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.hurry.R;
import com.metacoders.hurry.model.modelForCarRequest;

import java.util.List;

public class AllTripAdapter extends RecyclerView.Adapter<AllTripAdapter.ViewHolder> {
    List<modelForCarRequest> list ;
    Context context ;

    public AllTripAdapter(List<modelForCarRequest> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AllTripAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.last_trip_view_module, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllTripAdapter.ViewHolder holder,final  int position) {

        modelForCarRequest  item = list.get(position) ;
        holder.dateView.setText(item.getTimeDate());
        //    fareView.setText(fare);
        holder.locaTo.setText(item.getToLoc());
        holder.locaFrom.setText(item.getFromLoc());
        holder.statusTv.setText(item.getStatus());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public  TextView dateView , locaTo , locaFrom ,statusTv ;

        public ViewHolder(@NonNull View mview) {
            super(mview);

             dateView = mview.findViewById(R.id.dateOfRows);
            //  TextView fareView = mview.findViewById(R.id.fareRow);
             locaTo = mview.findViewById(R.id.locationTos);
             locaFrom = mview.findViewById(R.id.locationFroms);
             statusTv = mview.findViewById(R.id.statusRows) ;


        }


    }
}
