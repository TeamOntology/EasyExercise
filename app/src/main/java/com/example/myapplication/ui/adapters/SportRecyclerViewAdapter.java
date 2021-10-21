package com.example.myapplication.ui.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;
import com.example.myapplication.sportsImage.SportsImage;

import java.util.ArrayList;
import java.util.List;

public class SportRecyclerViewAdapter extends RecyclerView.Adapter<SportRecyclerViewAdapter.MyViewHolder> {
    private List<Sport> mSportList;
    public List<Sport> chosenSportList;
    private SportsImage sm;

    public SportRecyclerViewAdapter(List<Sport> sportList) {
        chosenSportList= new ArrayList<>();
        mSportList = sportList;
        sm= new SportsImage();
    }

    public List<Sport> getChosenSportList() {
        return chosenSportList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Sport sport = mSportList.get(position);
        holder.textView.setText(sport.getName());
        holder.imageView.setImageResource(sm.SportsToImage(sport));
        holder.cardView.setBackgroundColor(sport.isSelected() ? Color.CYAN : Color.WHITE);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sport.setSelected(!sport.isSelected());
                holder.cardView.setBackgroundColor(sport.isSelected() ? Color.CYAN : Color.WHITE);
            }
        });
        if(sport.isSelected()){
            chosenSportList.add(sport);
        }
        else if(!sport.isSelected()){
            if(chosenSportList.contains(sport)){
                chosenSportList.remove(sport);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mSportList == null ? 0 : mSportList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView textView;
        private ImageView imageView;
        private CardView cardView;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardView = (CardView) itemView.findViewById(R.id.sportView);
            textView = (TextView) itemView.findViewById(R.id.sport_title);
            imageView = (ImageView) itemView.findViewById(R.id.sport_image);
        }
    }
}
