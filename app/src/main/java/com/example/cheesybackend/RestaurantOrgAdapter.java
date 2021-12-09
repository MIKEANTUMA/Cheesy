package com.example.cheesybackend;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RestaurantOrgAdapter extends FirebaseRecyclerAdapter<Restaurant, RestaurantOrgAdapter.restaurantsViewholder> {

    private Context mCtx;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RestaurantOrgAdapter(Context mCtx, @NonNull FirebaseRecyclerOptions<Restaurant> options) {
        super(options);
        this.mCtx = mCtx;

    }





    @Override
    protected void onBindViewHolder(@NonNull restaurantsViewholder holder, int position, @NonNull Restaurant model) {
        // Add restaurantName from model class (here
        // "restaurant.class")to appropriate view in Card
        // view (here "recylerview_restaurant.xml")
        holder.restaurantName.setText(model.getName());
        Log.d("menu", model.getMenu().getAppetizer().toString());
        // Add address from model class (here
        // "restaurant.class")to appropriate view in Card
        // view (here "person.xml")
        holder.address.setText(model.getLocation());

        // Add WebsiteLink from model class (here
        // "restaurant.class")to appropriate view in Card
        // view (here "recylerview_restaurant.xml")
        holder.WebsiteLink.setText(model.getWebsite());

        holder.PhoneNumber.setText(model.getPhoneNumber());
        holder.ratingBar.setRating(model.getRating());
        holder.restaurantName.setOnClickListener(v -> {
            Log.d("PIZZZZAAAAA", "YOU CLICKED MY NAME");
            Intent intent = new Intent(mCtx, RestaurantPage.class);
            Restaurant restaurant = new Restaurant(model.getName(),model.getLocation(),model.getMenu(),model.getRating(),model.getPhoneNumber(),model.getWebsite(),model.getDescription());
            intent.putExtra("Restaurant", restaurant);
            mCtx.startActivity(intent);

        });
        holder.PhoneNumber.setOnClickListener(v -> {
            Log.d("PIZZZZAAAAA", "YOU CLICKED MY PHONE NUMBER");
        });
    }

    @NonNull
    @Override
    public restaurantsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_restraunt, parent, false);
        return new RestaurantOrgAdapter.restaurantsViewholder(view);

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



        public void onClick() {
            Log.d("PIZZZZAAAAA", "YOU CLICKED ME");
        }
    }

}