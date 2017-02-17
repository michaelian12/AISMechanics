package com.aisautocare.aismechanics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aisautocare.aismechanics.R;
import com.aisautocare.aismechanics.model.History;

import java.util.ArrayList;

/**
 * Created by Michael on 2/16/2017.
 */

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder> {

    private Context context;
    ArrayList<History> histories = new ArrayList<History>();

    public HistoryRecyclerViewAdapter(Context context, ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history, parent, false);
        HistoryViewHolder view = new HistoryViewHolder(viewItem);

        return view;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.date.setText(histories.get(position).getDate());
        holder.price.setText(histories.get(position).getPrice());
        holder.name.setText(histories.get(position).getCustomerName());
        holder.address.setText(histories.get(position).getAddress());
        holder.vehicleName.setText(histories.get(position).getVehicleName());
        holder.vehicleYear.setText(histories.get(position).getVehicleYear());
        holder.serviceName.setText(histories.get(position).getServiceName());
        holder.paymentMethod.setText(histories.get(position).getPaymentMethod());
        holder.rate.setNumStars(histories.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView date, price, name, address, vehicleName, vehicleYear, serviceName, paymentMethod;
        RatingBar rate;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.history_date_text_view);
            price = (TextView) itemView.findViewById(R.id.history_price_text_view);
            name = (TextView) itemView.findViewById(R.id.history_name_text_view);
            address = (TextView) itemView.findViewById(R.id.history_address_text_view);
            vehicleName = (TextView) itemView.findViewById(R.id.history_vehicle_name_text_view);
            vehicleYear = (TextView) itemView.findViewById(R.id.history_vehicle_year_text_view);
            serviceName = (TextView) itemView.findViewById(R.id.history_service_name_text_view);
            paymentMethod = (TextView) itemView.findViewById(R.id.history_payment_method_text_view);
            rate = (RatingBar) itemView.findViewById(R.id.history_rating_bar);
        }
    }
}
