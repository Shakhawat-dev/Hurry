package com.metacoders.hurry.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.hurry.R;

public class viewholderForPastTrips extends RecyclerView.ViewHolder {


    View mview ;

    public TextView statusTv ;

    public viewholderForPastTrips(@NonNull View itemView) {
        super(itemView);

        mview = itemView ;


        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mclicklistener.onItemClick(v , getAdapterPosition());

            }
        });


    }

    public   void setDataToView(Context context , String postId  , String userId  , String  userNotificationID  , String driverId  , String driverNotificationID ,
                                String  toLoc , String fromLoc , String timeDate , String carModl , String driverName ,
                                String status  , String carLicNum , String fare , String carType ,
                                String reqDate , String tripDetails ,String returntime   ){

        TextView dateView = mview.findViewById(R.id.dateOfRows);
        //  TextView fareView = mview.findViewById(R.id.fareRow);
        TextView locaTo = mview.findViewById(R.id.locationTos);
        TextView locaFrom = mview.findViewById(R.id.locationFroms);
        TextView statusTv = mview.findViewById(R.id.statusRows) ;





        dateView.setText(timeDate);
        //    fareView.setText(fare);
        locaTo.setText(toLoc);
        locaFrom.setText(fromLoc);
        statusTv.setText(status);

    }

    private  static  viewholderForPastTrips.Clicklistener mclicklistener ;



    public   interface  Clicklistener {

        void onItemClick( View view , int postion );

    }

    public  static  void  setOnClickListener (viewholderForPastTrips.Clicklistener clickListener ){


        mclicklistener = clickListener ;


    }

}
