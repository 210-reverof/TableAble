package com.swe.tableable.density;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swe.tableable.R;

import java.util.List;


public class DensityRecyclerAdapter extends RecyclerView.Adapter<DensityRecyclerAdapter.ViewHolder> {

    private List<Density> list;

    public DensityRecyclerAdapter(List<Density>getList){
        list = getList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.density_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //텍스트 및 이미지 설정
        holder.placeName.setText(list.get(position).getPlaceName());

        int getDensity =  list.get(position).getDensity();

        Drawable drawable = holder.densityImg.getResources().getDrawable(R.drawable.density_img_0);

        if (getDensity >= 0 && getDensity < 20) {
            holder.density.setText("여유");
            holder.densityImg.setImageDrawable(drawable);
        }
        else if (getDensity >= 20 && getDensity < 40) {
            holder.density.setText("여유");
            holder.densityImg.getResources().getDrawable(R.drawable.density_img_1);
        }
        else if ((getDensity >= 40 && getDensity < 60)) {
            holder.density.setText("보통");
            holder.densityImg.getResources().getDrawable(R.drawable.density_img_2);
        }
        else if (getDensity >= 60 && getDensity < 80) {
            holder.density.setText("혼잡");
            holder.densityImg.getResources().getDrawable(R.drawable.density_img_3);
        }
        else{
            holder.density.setText("혼잡");
            holder.densityImg.getResources().getDrawable(R.drawable.density_img_4);
        }
        holder.distance.setText(String.valueOf(list.get(position).getDistance()));
    }

    @Override
    public int getItemCount() {
        //리스트 개수 반환
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView placeName, distance, density;
        private ImageView densityImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.density_place_name);
            distance = itemView.findViewById(R.id.density_distance);
            density = itemView.findViewById(R.id.density);
            densityImg = itemView.findViewById(R.id.density_img);
        }
    }
}
