package com.antarikshc.parallem.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antarikshc.parallem.R;
import com.antarikshc.parallem.models.user.Certification;

import java.util.List;

public class CertificationRecyclerAdapter extends RecyclerView.Adapter<CertificationRecyclerAdapter.ViewHolder> {

    // Global params
    private Context context;
    private List<Certification> data;

    public CertificationRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_profile_certification, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        if (data != null && data.size() > 0) {

            // Get the single Experience
            Certification certification = data.get(i);

            // Set Certificate name
            viewHolder.txtCertName.setText(certification.getName());

            // Set Certificate Authority
            viewHolder.txtCertAuthority.setText(certification.getAuthority());


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


    public List<Certification> getData() {
        return data;
    }

    public void setData(List<Certification> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCertName;
        TextView txtCertAuthority;

        ViewHolder(View itemView) {
            super(itemView);

            txtCertName = itemView.findViewById(R.id.txt_certification_name);
            txtCertAuthority = itemView.findViewById(R.id.txt_certification_authority);

        }
    }

}
