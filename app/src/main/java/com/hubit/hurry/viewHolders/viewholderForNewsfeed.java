package com.hubit.hurry.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hubit.hurry.R;
import com.hubit.hurry.model.NewsfeedModel;

public class viewholderForNewsfeed extends RecyclerView.ViewHolder {


   // private static viewholderForNewsfeed.Clicklistener mclicklistener;
    public TextView statusTv;
    View mview;

    public viewholderForNewsfeed(@NonNull View itemView) {
        super(itemView);

        mview = itemView;


        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // mclicklistener.onItemClick(v, getAdapterPosition());

            }
        });


    }
//
//    public static void setOnClickListener(viewholderForNewsfeed.Clicklistener clickListener) {
//
//
//        mclicklistener = clickListener;
//
//
//    }

    public void setDataToView(Context context, NewsfeedModel model) {

        TextView title = mview.findViewById(R.id.title);
        TextView desc = mview.findViewById(R.id.descTv);
        ImageView image = mview.findViewById(R.id.image);

        title.setText(model.getTitle());
        //    fareView.setText(fare);
        desc.setText(model.getDesc());

        Glide.with(context)
                .load(model.getImage())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .override(250, 250)
                .placeholder(R.drawable.placeholder)
                .into(image);

    }

//    public interface Clicklistener {
//
//        void onItemClick(View view, int postion);
//
//    }

}
