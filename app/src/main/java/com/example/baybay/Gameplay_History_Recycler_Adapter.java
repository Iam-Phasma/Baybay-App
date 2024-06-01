package com.example.baybay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Gameplay_History_Recycler_Adapter extends RecyclerView.Adapter<Gameplay_History_Recycler_Adapter.MyViewHolder> {

    private ArrayList<Gameplay> gameplayList;
    public Gameplay_History_Recycler_Adapter(ArrayList<Gameplay> usersList){
        this.gameplayList = usersList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView TVGameRank;

        public MyViewHolder(final View view){
            super(view);
            TVGameRank = view.findViewById(R.id.tv_game_rank);
        }
    }

    @NonNull
    @Override
    public Gameplay_History_Recycler_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Gameplay_History_Recycler_Adapter.MyViewHolder holder, int position) {
        String name = gameplayList.get(position).getGameplay();
        holder.TVGameRank.setText(name);
    }

    @Override
    public int getItemCount() {
        return gameplayList.size();
    }
}
