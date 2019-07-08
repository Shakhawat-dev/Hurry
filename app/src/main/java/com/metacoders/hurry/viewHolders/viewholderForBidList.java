package com.metacoders.hurry.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metacoders.hurry.R;

public class viewholderForBidList extends RecyclerView.ViewHolder {
     View mview ;
    TextView  drivername , drivercarmodel ,  drivercarcondition , bidprice  ;

    public viewholderForBidList(@NonNull View itemView) {
        super(itemView);

        mview = itemView ;


        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mClicklistener.onItemClick(v , getAdapterPosition());

            }
        });



    }


    public  void setBidData(Context context , String tripID ,String driverUid ,String driverName ,String driverCarModel ,String driverRating  ,String bidPrice , String driverCarCondition , String driverImageLink )
    {


        RatingBar ratingBar ;
        ImageView driverimage ;


        drivername = mview.findViewById(R.id.driverNameOfBidRow);
        drivercarmodel = mview.findViewById(R.id.carModelBid);
        drivercarcondition = mview.findViewById(R.id.conditionRateBidRow) ;
        bidprice = mview.findViewById(R.id.priceViewInBidRow);


        //setting the data
        drivername.setText(driverName);
        drivercarmodel.setText(driverCarModel);
        drivercarcondition.setText(driverCarCondition);
        bidprice.setText(bidPrice);

        // loadthe image to the view



    }

    private  static  viewholderForBidList.Clicklistener mClicklistener ;



    // interface
    public  interface  Clicklistener{

        void onItemClick(View view ,  int   position ) ;



    }

    public  static void setOnClickListener(viewholderForBidList.Clicklistener clickListener){

        mClicklistener = clickListener ;


    }




}
