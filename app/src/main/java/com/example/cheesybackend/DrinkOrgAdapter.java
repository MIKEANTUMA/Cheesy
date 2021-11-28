package com.example.cheesybackend;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrinkOrgAdapter extends RecyclerView.Adapter<DrinkOrgAdapter.TaskViewHolder> {

    private ArrayList<Drink> taskList;
    private Context mCtx;
    private Cart cart = Cart.getInstance();

    public DrinkOrgAdapter(Context mCtx, ArrayList<Drink> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public DrinkOrgAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.menu_card, parent, false);
        return new DrinkOrgAdapter.TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkOrgAdapter.TaskViewHolder holder, int position) {
        Drink e = taskList.get(position);
        holder.itemName.setText(e.getName());
        holder.itemDescription.setText(e.getDescription());
        holder.itemPrice.setText(String.valueOf(e.getPrice()));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemName, itemDescription, itemPrice;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.txtmenuItemName);
            itemDescription = itemView.findViewById(R.id.tv_description);
            itemPrice = itemView.findViewById(R.id.tv_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //add to cart logic here
            AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
            builder.setMessage("Would you like to add to cart?").setTitle("Order");
            builder.setPositiveButton("Ok", (V,A) -> {
                    Drink d = taskList.get(getAdapterPosition());
                    Log.d("DRINK", taskList.get(getAdapterPosition()).toString());
                    Toast.makeText(mCtx, "Added to cart", Toast.LENGTH_SHORT).show();
                cart.addToCart(d);
            });
            builder.setNegativeButton("No", (V,A) ->{
                //cancelled the dialog
            });
            builder.create().show();
        }
    }
}