package com.antarikshc.parallem.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.models.team.Member;
import com.antarikshc.parallem.util.Master;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamMemberRecyclerAdapter extends RecyclerView.Adapter<TeamMemberRecyclerAdapter.ViewHolder> {

    // Global params
    private Context context;
    private CustomItemClickListener listener;
    private List<Member> data;

    public TeamMemberRecyclerAdapter(Context context, CustomItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_team_member_recycler, viewGroup, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        // Pass OnClickListener from view to CustomOnItemClickListener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (data != null && data.size() > 0) {

            // Get the single User
            Member member = data.get(i);

            // Set Member name
            viewHolder.memberName.setText(member.getName());

            // Set Member role
            viewHolder.memberRole.setText(member.getRole());

            // Load image with Picasso and set to ImageView
            Picasso.get()
                    .load(Master.getProfileImageUrl(member.getProfileImage()))
                    .into(viewHolder.memberImage);

        }

    }

    public List<Member> getData() {
        return data;
    }

    public void setData(List<Member> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView memberImage;
        TextView memberName;
        TextView memberRole;

        ViewHolder(View itemView) {
            super(itemView);

            memberImage = itemView.findViewById(R.id.img_team_member_profile);
            memberName = itemView.findViewById(R.id.txt_team_member_name);
            memberRole = itemView.findViewById(R.id.txt_team_member_role);

        }
    }

}
