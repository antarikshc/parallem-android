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
import com.antarikshc.parallem.models.user.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    // Global params
    private Context context;
    private List<Notification> data;

    public NotificationAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_notification_recycler, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (data != null && data.size() > 0) {

            Notification notification = data.get(i);

            viewHolder.notificationMessage.setText(notification.getMessage());

            if (notification.getStatus() != null) {
                if (!(notification.getStatus() == 0)) {
                    viewHolder.imgBtnAccept.setVisibility(View.GONE);
                    viewHolder.imgBtnDecline.setVisibility(View.GONE);
                }
            }

            viewHolder.imgBtnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            viewHolder.imgBtnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

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


    public List<Notification> getData() {
        return data;
    }

    public void setData(List<Notification> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView notificationMessage;
        ImageView imgBtnAccept;
        ImageView imgBtnDecline;

        ViewHolder(View itemView) {
            super(itemView);

            notificationMessage = itemView.findViewById(R.id.txt_notification_message);
            imgBtnAccept = itemView.findViewById(R.id.img_notification_accept);
            imgBtnDecline = itemView.findViewById(R.id.img_notification_decline);

        }

    }
}
