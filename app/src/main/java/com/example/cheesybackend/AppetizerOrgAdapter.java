package com.example.cheesybackend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        }

        @Override
        public void onClick(View v) {
            //add to cart logic here
        }
    }
}
