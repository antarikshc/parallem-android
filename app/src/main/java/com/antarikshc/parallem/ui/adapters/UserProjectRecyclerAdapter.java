package com.antarikshc.parallem.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.models.user.UserProject;

import java.util.List;

public class UserProjectRecyclerAdapter extends RecyclerView.Adapter<UserProjectRecyclerAdapter.ViewHolder> {

    // Global params
    private Context context;
    private List<UserProject> data;

    public UserProjectRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_profile_projects, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (data != null && data.size() > 0) {

            // Get the single Experience
            UserProject userProject = data.get(i);

            // Set Experience name
            viewHolder.txtProjectName.setText(userProject.getName());

            // Set Experience Designation
            viewHolder.txtProjectDesc.setText(userProject.getDesc());

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


    public List<UserProject> getData() {
        return data;
    }

    public void setData(List<UserProject> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtProjectName;
        TextView txtProjectDesc;

        ViewHolder(View itemView) {
            super(itemView);

            txtProjectName = itemView.findViewById(R.id.txt_user_project_name);
            txtProjectDesc = itemView.findViewById(R.id.txt_user_project_desc);

        }
    }

}
