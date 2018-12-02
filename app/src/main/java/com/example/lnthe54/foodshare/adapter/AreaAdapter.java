package com.example.lnthe54.foodshare.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lnthe54.foodshare.R;
import com.example.lnthe54.foodshare.model.Area;

import java.util.ArrayList;

/**
 * @author lnthe54 on 11/10/2018
 * @project FoodShare
 */
public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaHolder> {

    private CallBack callBack;
    private ArrayList<Area> listArea;

    public AreaAdapter(CallBack callBack, ArrayList<Area> listArea) {
        this.callBack = callBack;
        this.listArea = listArea;
    }

    @NonNull
    @Override
    public AreaHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_area, parent, false);
        return new AreaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaHolder areaHolder, int position) {
        Area area = listArea.get(position);
        areaHolder.bindData(area);
    }

    @Override
    public int getItemCount() {
        return listArea.size();
    }

    public class AreaHolder extends RecyclerView.ViewHolder {

        private ImageView ivArea;
        private TextView tvNameArea;

        public AreaHolder(@NonNull View itemView) {
            super(itemView);

            ivArea = itemView.findViewById(R.id.iv_area);
            tvNameArea = itemView.findViewById(R.id.tv_name_area);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.itemAreaClick(getAdapterPosition());
                }
            });
        }

        public void bindData(Area area) {
            String pathImg = area.getImage();
            Glide.with(itemView.getContext())
                    .load(pathImg)
                    .into(ivArea);
            tvNameArea.setText(area.getName());
        }
    }

    public interface CallBack {
        void itemAreaClick(int position);
    }
}
