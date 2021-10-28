package com.example.cheesybackend;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestrauntOrgAdapter  extends RecyclerView.Adapter<RestrauntOrgAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<Restaurant> taskList;

    public RestrauntOrgAdapter(Context mCtx, List<Restaurant> rest) {
        this.mCtx = mCtx;
        this.taskList = rest;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_restraunt, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Restaurant t = taskList.get(position);
        holder.restName.setText(t.getName());
        holder.address.setText(t.getLocation());
        holder.phoneNumber.setText(t.getPhoneNumber());
        holder.webLink.setText(t.getWebsite());
        holder.rating.setRating(t.getRating());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  restName, address, phoneNumber, webLink;
        RatingBar rating;

        public TasksViewHolder(View itemView) {
            super(itemView);

            restName = itemView.findViewById(R.id.txtRestName);
            address = itemView.findViewById(R.id.txtAddress);
            phoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            webLink = itemView.findViewById(R.id.txtWebsite);
            rating = itemView.findViewById(R.id.Ratingbar);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            NoteOrgRoom note = taskList.get(getBindingAdapterPosition());
//
//            Intent intent = new Intent(mCtx, UpdateNoteActivity.class);
//            intent.putExtra("Note", note);
//
//            mCtx.startActivity(intent);
        }
    }
}