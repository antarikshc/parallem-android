package com.antarikshc.parallem.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.models.user.Experience;

import java.util.List;

public class ExperienceRecyclerAdapter extends RecyclerView.Adapter<ExperienceRecyclerAdapter.ViewHolder> {

    // Global params
    private Context context;
    private List<Experience> data;

    public ExperienceRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_profile_experience, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (data != null && data.size() > 0) {

            // Get the single Experience
            Experience experience = data.get(i);

            // Set Experience name
            viewHolder.txtExpName.setText(experience.getName());

            // Set Experience Designation
            viewHolder.txtExpDesignation.setText(experience.getDesgn());

            // Hide duration until we figure something out
            viewHolder.txtExpDuration.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    public List<Experience> getData() {
        return data;
    }

    public void setData(List<Experience> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtExpName;
        TextView txtExpDesignation;
        TextView txtExpDuration;

        ViewHolder(View itemView) {
            super(itemView);

            txtExpName = itemView.findViewById(R.id.txt_experience_name);
            txtExpDesignation = itemView.findViewById(R.id.txt_experience_designation);
            txtExpDuration = itemView.findViewById(R.id.txt_experience_duration);

        }
    }
}
