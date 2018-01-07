package com.yourzeromax.zympro.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yourzeromax.zympro.JavaBeans.Community;
import com.yourzeromax.zympro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yourzeromax on 2017/11/6.
 */

public class CommunityRecyclerVIewAdapter extends RecyclerView.Adapter<CommunityRecyclerVIewAdapter.ViewHolder> {
    Context context;
    List<Community> communities = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_1, imageView_2, imageView_3;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_1 = (ImageView) itemView.findViewById(R.id.item_image_1);
            imageView_2 = (ImageView) itemView.findViewById(R.id.item_image_2);
            imageView_3 = (ImageView) itemView.findViewById(R.id.item_image_3);
            tvTitle = (TextView) itemView.findViewById(R.id.item_title);
        }
    }

    public CommunityRecyclerVIewAdapter(Context context, List<Community> communities) {
        this.context = context;
        this.communities = communities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_community, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Community community = communities.get(position);
        Glide.with(context).load(community.getPic_1()).into(holder.imageView_1);
        Glide.with(context).load(community.getPic_2()).into(holder.imageView_2);
        Glide.with(context).load(community.getPic_3()).into(holder.imageView_3);
        holder.tvTitle.setText(community.getDescription());
    }

    @Override
    public int getItemCount() {
        return communities.size();
    }
}
