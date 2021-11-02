package com.swe.tableable.prseat;

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

public class PrSeatRecyclerAdapter extends RecyclerView.Adapter<PrSeatRecyclerAdapter.ViewHolder> {

    private List<PrSeat> list;

    public PrSeatRecyclerAdapter(List<PrSeat>getList){
        list = getList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.prdt_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //텍스트 및 이미지 설정
        holder.placeName.setText(list.get(position).getPlaceName());
        holder.prSeat.setText(String.valueOf(list.get(position).getPr_seat()));
        holder.prTime.setText(list.get(position).getPr_time());

    }

    @Override
    public int getItemCount() {
        //리스트 개수 반환
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView placeName, prTime, prSeat ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.prdt_place_name);
            prSeat = itemView.findViewById(R.id.pr_seat);
            prTime = itemView.findViewById(R.id.pr_time);
        }
    }
}
