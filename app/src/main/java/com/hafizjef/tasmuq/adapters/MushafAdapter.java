package com.hafizjef.tasmuq.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hafizjef.tasmuq.R;
import com.hafizjef.tasmuq.model.ImageModel;
import com.hafizjef.tasmuq.model.MushafResponse;
import com.hafizjef.tasmuq.ui.activity.DetailActivity;
import com.hafizjef.tasmuq.utils.AndroidUtils;
import com.hafizjef.tasmuq.utils.Constants;

import java.util.List;

public class MushafAdapter extends RecyclerView.Adapter<MushafAdapter.ViewHolder> {

    private static final String TAG = "MushafAdapter";

    private List<ImageModel> imageModels;
    private String serverURL;
    private Context context;

    public MushafAdapter(MushafResponse mushafResponse, Context context, String serverURL) {
        this.imageModels = mushafResponse.getImageModels();
        this.context = context;
        this.serverURL = serverURL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_holder, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ImageModel imageModel = imageModels.get(position);
        holder.email.setText(imageModel.getTimestamp());
        holder.status.setText(imageModel.getStatus());

        try {
            Glide.with(context)
                    .load(serverURL + "files/frame/text/" + imageModel.getMFile().getPath())
                    .into(holder.imageView);
        } catch (Exception err) {
            Log.d(TAG, err.getMessage());
        }

        holder.constraintLayout.setOnClickListener((View v) -> {

            if (holder.status.getText().equals("COMPLETE")) {
                Intent intent = new Intent(AndroidUtils.getAppContext(), DetailActivity.class);
                intent.putExtra(Constants.UUID_EXTRA, imageModel.getUuid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AndroidUtils.getAppContext().startActivity(intent);
            } else {
                AndroidUtils.showToast("Detailed Data Not Available");
            }

        });
    }

    @Override
    public int getItemCount() {
        if(imageModels != null) {
            return imageModels.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView email, status;
        ImageView imageView;
        ConstraintLayout constraintLayout;

        ViewHolder(View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.statusText);
            email = itemView.findViewById(R.id.emailText);
            imageView = itemView.findViewById(R.id.imgView);
            constraintLayout = itemView.findViewById(R.id.constraintContainer);
        }
    }
}
