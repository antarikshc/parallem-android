package com.antarikshc.parallem.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.antarikshc.parallem.R;
import com.antarikshc.parallem.models.user.Notification;
import com.antarikshc.parallem.util.API;
import com.antarikshc.parallem.util.ParallemApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private static final String LOG_TAG = NotificationAdapter.class.getSimpleName();

    // Global params
    private Context context;
    private RequestQueue requestQueue;
    private List<Notification> data;

    public NotificationAdapter(Context context, RequestQueue requestQueue) {
        this.context = context;
        this.requestQueue = requestQueue;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_notification_recycler, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        if (data != null && data.size() > 0) {

            // Store the position of item as a tag
            // To be referenced later in OnClickListeners
            viewHolder.itemView.setTag(R.id.item_number, i);

            Notification notification = data.get(i);

            viewHolder.notificationMessage.setText(notification.getMessage());

            // If user has interacted with notification then hide the buttons
            if (notification.getStatus() != null) {
                if (!(notification.getStatus() == 0)) {
                    viewHolder.imgBtnAccept.setVisibility(View.GONE);
                    viewHolder.imgBtnDecline.setVisibility(View.GONE);
                }
            }

            // OnClick listener to Accept request
            viewHolder.imgBtnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ParallemApp.isTeamIdExist()) {
                        Toast.makeText(context, "Please leave your current team", Toast.LENGTH_SHORT).show();
                    } else {

                        Integer position = (Integer) viewHolder.itemView.getTag(R.id.item_number);

                        // Get the Notification object from data
                        Notification currentNotification = data.get(position);

                        addUserToTeam(currentNotification);
                        removeNotification(currentNotification, position);

                    }

                }
            });

            // OnClick listener to Decline request
            viewHolder.imgBtnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Integer position = (Integer) viewHolder.itemView.getTag(R.id.item_number);

                    // Get the Notification object from data
                    Notification currentNotification = data.get(position);

                    removeNotification(currentNotification, position);

                }
            });

        }

    }

    /**
     * Contains Volley request to add User to Team
     *
     * @param notification Contains team_id
     */
    private void addUserToTeam(final Notification notification) {

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("user", ParallemApp.getUserId());
            requestObject.put("role", "Member");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Volley request to Add member in team
        JsonObjectRequest teamMemberRequest = new JsonObjectRequest(
                Request.Method.POST,
                API.getTeamMembers(notification.getTeamId()),
                requestObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(LOG_TAG, "HTTP Response received for TeamMemberRequest");
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(LOG_TAG, "HTTP Error occurred: " + error);
                    }
                }
        ) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

                // Retrieve status code
                Integer statusCode = response.statusCode;

                try {
                    if (statusCode == 201) {

                        ParallemApp.saveTeamId(notification.getTeamId());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return super.parseNetworkResponse(response);
            }
        };

        // Queue the API Call
        requestQueue.add(teamMemberRequest);

    }

    /**
     * Contains request to remove all notifications from Team ID
     *
     * @param notification contains the team_id
     * @param position     to remove the item from recycler
     */
    private void removeNotification(Notification notification, final Integer position) {

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("team_id", notification.getTeamId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Volley request to Add member in team
        JsonObjectRequest removeNotificationRequest = new JsonObjectRequest(
                Request.Method.PUT,
                API.getRemoveNotificationEndpoint(ParallemApp.getUserId()),
                requestObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(LOG_TAG, "HTTP Response received for RemoveNotification");

                        removeAt(position);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(LOG_TAG, "HTTP Error occurred: " + error);
                    }
                }
        );

        // Queue the API Call
        requestQueue.add(removeNotificationRequest);

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

    private void removeAt(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
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
