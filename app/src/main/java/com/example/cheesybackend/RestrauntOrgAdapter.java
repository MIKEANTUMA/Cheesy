package com.example.cheesybackend;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestrauntOrgAdapter  extends FirebaseRecyclerAdapter<
        Restaurant, RestrauntOrgAdapter.restaurantsViewholder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RestrauntOrgAdapter(@NonNull FirebaseRecyclerOptions<Restaurant> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull restaurantsViewholder holder, int position, @NonNull Restaurant model) {
        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.restaurantName.setText(model.getName());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.address.setText(model.getLocation());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.WebsiteLink.setText(model.getWebsite());

        holder.PhoneNumber.setText(model.getPhoneNumber());
        holder.ratingBar.setRating(model.getRating());

    }

    @NonNull
    @Override
    public restaurantsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_restraunt, parent, false);
        return new RestrauntOrgAdapter.restaurantsViewholder(view);
    }

    class restaurantsViewholder extends RecyclerView.ViewHolder {
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
    }
}