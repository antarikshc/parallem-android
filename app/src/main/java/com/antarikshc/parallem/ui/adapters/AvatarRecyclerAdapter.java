package com.antarikshc.parallem.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.models.user.ProfileAvatar;
import com.antarikshc.parallem.util.API;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AvatarRecyclerAdapter extends RecyclerView.Adapter<AvatarRecyclerAdapter.ViewHolder> {

    // Global params
    private Context context;
    private CustomItemClickListener listener;
    private List<ProfileAvatar> data;

    public AvatarRecyclerAdapter(Context context, CustomItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_personal_avatar, viewGroup, false);

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

            // Get the single Avatar
            ProfileAvatar avatar = data.get(i);

            Integer avatarIdId = avatar.getAvatarId();

            // Download Image bitmap with Picasso
            Picasso.get()
                    .load(API.getProfileImageUrl(String.valueOf(avatarIdId)))
                    .into(viewHolder.imgAvatar);

            if (avatar.getSelected()) {
                viewHolder.imgAvatar.setBackgroundResource(R.drawable.bg_avatar_selected);
            } else {
                viewHolder.imgAvatar.setBackground(null);
            }
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

    public List<ProfileAvatar> getData() {
        return data;
    }

    public void setData(List<ProfileAvatar> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;

        ViewHolder(View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.img_recycler_avatar);

        }
    }

}
