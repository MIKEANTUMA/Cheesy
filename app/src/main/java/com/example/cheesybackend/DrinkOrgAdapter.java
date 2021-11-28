package com.example.cheesybackend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrinkOrgAdapter extends RecyclerView.Adapter<DrinkOrgAdapter.TaskViewHolder> {

    private ArrayList<Drink> taskList;
    private Context mCtx;


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

        }

        @Override
        public void onClick(View v) {
            //add to cart logic here
        }
    }
}