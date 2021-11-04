package com.swe.tableable.afterdensity;

import android.content.Context;
import android.graphics.Color;
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


public class AfterDensityRecyclerAdapter extends RecyclerView.Adapter<AfterDensityRecyclerAdapter.ViewHolder> {

    private List<AfterDensity> list;

    public AfterDensityRecyclerAdapter(List<AfterDensity>getList){
        list = getList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.afterdensity_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //텍스트 및 이미지 설정
        holder.placeName.setText(list.get(position).getPlaceName());
        holder.currentDensity.setText("현재 "+toStateString(list.get(position).getCurrentDensity()));

        holder.nextTime1.setText(String.valueOf(list.get(position).getNextTime1()+"시"));
        holder.nextDensity1.setText(toStateString(list.get(position).getNextDensity1()));
        holder.nextDensity1.setTextColor(toStateColor(list.get(position).getNextDensity1()));

        holder.nextTime2.setText(String.valueOf(list.get(position).getNextTime2()+"시"));
        holder.nextDensity2.setText(toStateString(list.get(position).getNextDensity2()));
        holder.nextDensity2.setTextColor(toStateColor(list.get(position).getNextDensity2()));
    }

    @Override
    public int getItemCount() {
        //리스트 개수 반환
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView placeName, currentDensity, nextTime1, nextDensity1, nextTime2, nextDensity2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.afterdensity_place_name);
            currentDensity= itemView.findViewById(R.id.afterdensty_current_denstiy);
            nextTime1 = itemView.findViewById(R.id.aftertime1);
            nextDensity1 = itemView.findViewById(R.id.afterdensity_den1);
            nextTime2 = itemView.findViewById(R.id.aftertime2);
            nextDensity2 = itemView.findViewById(R.id.afterdensity_den2);
        }
    }

    //double 복잡도를 넣으면, String 한글 설명으로 반환하는 함수
    public String toStateString(double den) {
        String state = "";
        if (den <= 25.0) {state = "여유";}
        else if ( 25.0 < den && den <= 75.0) state = "보통";
        else {  state = "혼잡";  }

        return state;
    }

    //double 복잡도를 넣으면, 글자 색 반환하는 함수
    public int toStateColor(double den) {
        int state = Color.RED;

        if (den <= 25.0) { state = 0xff70AD47; }
        else if ( 25.0 < den && den <= 75.0) state = 0xff595959;
        else { state = 0xffED7D31;  }

        return state;
    }
}
