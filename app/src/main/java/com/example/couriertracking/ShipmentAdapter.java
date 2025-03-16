package com.example.couriertracking;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ViewHolder> {

    private List<Shipment> shipments;

    public ShipmentAdapter(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shipment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shipment shipment = shipments.get(position);
        holder.shipmentId.setText(shipment.getId());
        holder.status.setText(shipment.getStatus() + " • " + shipment.getDate());
    }

    @Override
    public int getItemCount() {
        return shipments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView shipmentId, status;

        public ViewHolder(View itemView) {
            super(itemView);
            shipmentId = itemView.findViewById(R.id.shipmentId);
            status = itemView.findViewById(R.id.status);
        }
    }
}
