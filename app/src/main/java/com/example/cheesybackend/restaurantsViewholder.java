package com.example.cheesybackend;

import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class restaurantsViewholder extends RecyclerView.ViewHolder {
    TextView restaurantName, address, WebsiteLink,PhoneNumber;
    RatingBar ratingBar;
    public restaurantsViewholder(@NonNull View itemView)
    {
        super(itemView);

        restaurantName = itemView.findViewById(R.id.txtRestName);
        address = itemView.findViewById(R.id.txtAddress);
        WebsiteLink = itemView.findViewById(R.id.txtWebsite);
        PhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
        ratingBar = itemView.findViewById(R.id.Ratingbar);
    }



    public void onClick() {
        Log.d("PIZZZZAAAAA", "YOU CLICKED ME");
    }

}
