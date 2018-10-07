package com.antarikshc.parallem.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.util.Master;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AvatarRecyclerAdapter extends RecyclerView.Adapter<AvatarRecyclerAdapter.ViewHolder> {

    // Global params
    private Context context;
    private List<Integer> data;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_personal_avatar, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (data != null && data.size() > 0) {

            // Get the single Image ID
            Integer imageId = data.get(i);

            // Download Image bitmap with Picasso
            Picasso.get()
                    .load(Master.getProfileImageUrl(String.valueOf(imageId)))
                    .into(viewHolder.imgAvatar);

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

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
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
