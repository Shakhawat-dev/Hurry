package com.metacoders.hurry.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.hurry.R;

public class viewholdersForCurrentTrip extends RecyclerView.ViewHolder {

    View mview ;


    public viewholdersForCurrentTrip(@NonNull View itemView) {
        super(itemView);

        mview = itemView ;


    }

    public   void setDataToView(Context context ,String postId  , String userId  ,String  userNotificationID  , String driverId  ,String driverNotificationID ,
                                String  toLoc ,String fromLoc , String timeDate ,String carModl ,String driverName ,
                                String status  ,String carLicNum ,String fare ,String carType ,
                                String reqDate ,String tripDetails   ){

        TextView dateView = mview.findViewById(R.id.dateOfRow);
        TextView fareView = mview.findViewById(R.id.fareRow);
        TextView locaTo = mview.findViewById(R.id.locationTo);
        TextView locaFrom = mview.findViewById(R.id.locationFrom);

        dateView.setText(timeDate);
        fareView.setText(fare);
        locaTo.setText(toLoc);
        locaFrom.setText(fromLoc);


    }



}
