package com.example.cheesybackend;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppetizerOrgAdapter extends RecyclerView.Adapter<AppetizerOrgAdapter.TaskViewHolder> {

    private ArrayList<Appetizer> taskList;
    private Context mCtx;


    public AppetizerOrgAdapter(Context mCtx, ArrayList<Appetizer> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.menu_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppetizerOrgAdapter.TaskViewHolder holder, int position) {
        Appetizer e = taskList.get(position);
        holder.itemName.setText(e.getName());
        holder.itemDescription.setText(e.getDescription());
        holder.itemPrice.setText(String.valueOf(e.getPrice()));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
                Appetizer a = taskList.get(getAdapterPosition());
                Log.d("APPETIZER", taskList.get(getAdapterPosition()).toString());
                Toast.makeText(mCtx, "Added to cart", Toast.LENGTH_SHORT).show();
                //addToCart(d);
            });
            builder.setNegativeButton("No", (V,A) ->{
                //cancelled the dialog
            });
            builder.create().show();
        }
    }
    }

