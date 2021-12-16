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

public class OrdersAdapter extends FirebaseRecyclerAdapter<UserOrder, OrdersAdapter.ordersViewHolder> {
    private Context mCtx;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OrdersAdapter(Context mCtx, @NonNull FirebaseRecyclerOptions<UserOrder> options) {
        super(options);
        this.mCtx = mCtx;

    }





    @Override
    protected void onBindViewHolder(@NonNull OrdersAdapter.ordersViewHolder holder, int position, @NonNull UserOrder model) {

        holder.restaurantName.setText(model.getRestaurantName());
        holder.PhoneNumber.setText(model.getPhoneNumber());
        holder.time.setText(model.getDateTime());

        holder.totalPrice.setText("$"+String.valueOf(model.getTotalPrice()));
        holder.itemCount.setText("Total Items:"+String.valueOf(model.getTotalItems()));
//        holder.restaurantName.setOnClickListener(v -> {
//            Intent intent = new Intent(mCtx, RestaurantPage.class);
//            intent.putExtra("Restaurant", restaurant);
//            mCtx.startActivity(intent);
//
//        });
    }

    @NonNull
    @Override
    public OrdersAdapter.ordersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_orders, parent, false);
        return new OrdersAdapter.ordersViewHolder(view);

    }



    class ordersViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName, time, totalPrice, itemCount,PhoneNumber;

        public ordersViewHolder(@NonNull View itemView)
        {
            super(itemView);

            restaurantName = itemView.findViewById(R.id.txtRestName);
            PhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            time = itemView.findViewById(R.id.txtTime);
            totalPrice = itemView.findViewById(R.id.txtPrice);
            itemCount = itemView.findViewById(R.id.txtItemCount);
        }
    }

}
