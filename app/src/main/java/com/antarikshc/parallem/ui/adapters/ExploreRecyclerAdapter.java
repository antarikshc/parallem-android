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
import com.antarikshc.parallem.models.user.User;

public class ExploreRecyclerAdapter extends RecyclerView.Adapter<ExploreRecyclerAdapter.ViewHolder> {

    // Global params
    private Context context;
    private User[] data;

    public ExploreRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_explore_items_recycler, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreRecyclerAdapter.ViewHolder viewHolder, int i) {

        if (data != null && data.length > 0) {

            // Get the single User
            User user = data[i];

            // Set Profile name
            //viewHolder.profileName.setText(user.getName());

            // Set Profile headline
            //viewHolder.profileHeadline.setText(user.getHeadline());

        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.length;
        }
    }

    public User[] getData() {
        return data;
    }

    public void setData(User[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        TextView profileName;
        TextView profileHeadline;

        public ViewHolder(View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.img_explore_profile);
            profileName = itemView.findViewById(R.id.txt_explore_name);
            profileHeadline = itemView.findViewById(R.id.txt_explore_headline);


        }
    }

}
