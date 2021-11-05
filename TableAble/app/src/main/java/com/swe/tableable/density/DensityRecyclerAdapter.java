package com.swe.tableable.density;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swe.tableable.R;

import java.util.List;

import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;


public class DensityRecyclerAdapter extends RecyclerView.Adapter<DensityRecyclerAdapter.ViewHolder> {

    private List<Density> list;

    public DensityRecyclerAdapter(List<Density>getList){
        list = getList;
    }
    private double currentLatitude = 36.76907;
    private double currentLongitude = 126.93482;

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

        double getDensity =  list.get(position).getDensity();

        Drawable drawable = holder.densityImg.getResources().getDrawable(R.drawable.density_img_0);

        if (getDensity >= 0 && getDensity < 20) {
            drawable = holder.densityImg.getResources().getDrawable(R.drawable.density_img_0);
            holder.density.setText("여유");
            holder.densityImg.setImageDrawable(drawable);
        }
        else if (getDensity >= 20 && getDensity < 40) {
            drawable = holder.densityImg.getResources().getDrawable(R.drawable.density_img_1);
            holder.density.setText("여유");
            holder.densityImg.setImageDrawable(drawable);
        }
        else if ((getDensity >= 40 && getDensity < 60)) {
            drawable = holder.densityImg.getResources().getDrawable(R.drawable.density_img_2);
            holder.density.setText("보통");
            holder.densityImg.setImageDrawable(drawable);
        }
        else if (getDensity >= 60 && getDensity < 80) {
            drawable = holder.densityImg.getResources().getDrawable(R.drawable.density_img_3);
            holder.density.setText("혼잡");
            holder.densityImg.setImageDrawable(drawable);
        }
        else{
            drawable = holder.densityImg.getResources().getDrawable(R.drawable.density_img_4);
            holder.density.setText("혼잡");
            holder.densityImg.setImageDrawable(drawable);
        }
        // 현재 위치, 가게 위도경도로 직선 거리 구해서 홀더에 넣기
        int distance = getDistance(currentLatitude, currentLongitude, (list.get(position).getLatitude()), (list.get(position).getLongitude()));
        holder.distance.setText(String.valueOf(distance)+"m");
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

    public int getDistance(double lat1, double lon1, double lat2, double lon2) {
        Location location1 = new Location("a");
        location1.setLongitude(lon1);
        location1.setLatitude(lat1);
        Location location2 = new Location("b");
        location2.setLongitude(lon2);
        location2.setLatitude(lat2);
        double distance = location1.distanceTo(location2);
        return (int)distance;
    }
}
