package com.example.myparking.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myparking.R;
import com.example.myparking.ReservationDetails;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> parking ;
    List<String> time ;
    List<String> day ;
    List<String> price ;
    public Adapter( List<String> parking,List<String> time,List<String> day,List<String> price){
        this.parking = parking;
        this.time = time;
        this.day = day;
        this.price = price;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_view_layout,parent,false);
        return new ViewHolder(view);
    }
    //##############################################################################################
// ################cette fonction nous permet de transmettre les données vers les Views#############
    //###############################################################################################

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.parkingTitle.setText(parking.get(position));
        holder.dayContent.setText(day.get(position));
        holder.timeContent.setText(time.get(position));
        holder.priceContent.setText(price.get(position));
        //si l'utilisateur clique sur un ITEM
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"VOUS AVEZ CLIQUER SUR UN ITEM",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(v.getContext(), ReservationDetails.class);
                i.putExtra("parking",parking.get(position));
                i.putExtra("day",day.get(position));
                i.putExtra("time",time.get(position));
                i.putExtra("price",price.get(position));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parking.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
            TextView parkingTitle,timeContent,dayContent,priceContent;
            View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parkingTitle = itemView.findViewById(R.id.Res_Park);
            timeContent = itemView.findViewById(R.id.Time_Park);
            dayContent = itemView.findViewById(R.id.Date_Park);
            priceContent = itemView.findViewById(R.id.Price_Park);
            view =itemView;
        }
    }
}
