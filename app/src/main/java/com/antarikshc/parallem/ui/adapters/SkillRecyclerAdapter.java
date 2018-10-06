package com.antarikshc.parallem.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.models.Skill;

import java.util.List;

public class SkillRecyclerAdapter extends RecyclerView.Adapter<SkillRecyclerAdapter.ViewHolder> {

    // Global params
    private Context context;
    private List<Skill> data;

    public SkillRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_profile_skills, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (data != null && data.size() > 0) {

            // Get the single Experience
            Skill skill = data.get(i);

            // Set Certificate name
            viewHolder.txtSkillName.setText(String.valueOf(skill.getSkillId()));

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


    public List<Skill> getData() {
        return data;
    }

    public void setData(List<Skill> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtSkillName;

        ViewHolder(View itemView) {
            super(itemView);

            txtSkillName = itemView.findViewById(R.id.txt_skill_name);

        }
    }

}
