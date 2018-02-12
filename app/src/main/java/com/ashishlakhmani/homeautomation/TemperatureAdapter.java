package com.ashishlakhmani.homeautomation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class TemperatureAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<TemperatureDetails> temperatureDetailsList;

    public TemperatureAdapter(Context context, List<TemperatureDetails> temperatureDetailsList) {
        this.context = context;
        this.temperatureDetailsList = temperatureDetailsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_details, parent, false);
        return new TemperatureAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).temperature.setText(temperatureDetailsList.get(position).getTemperature() + (char) 0x00B0 + "C");
        ((MyViewHolder) holder).humidity.setText(temperatureDetailsList.get(position).getHumidity()+"%");
        ((MyViewHolder) holder).dateTime.setText(temperatureDetailsList.get(position).getDateTime());
    }

    @Override
    public int getItemCount() {
        return temperatureDetailsList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView temperature, humidity, dateTime;

        private MyViewHolder(View itemView) {
            super(itemView);
            temperature = itemView.findViewById(R.id.temperature);
            humidity = itemView.findViewById(R.id.humidity);
            dateTime = itemView.findViewById(R.id.date_time);
        }
    }
}
