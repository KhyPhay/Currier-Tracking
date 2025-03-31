package com.example.couriertracking;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.ViewHolder> {

    private List<Shipment> shipments;
    private Context context;
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
        holder.itemView.setOnClickListener(view -> {
            Context context = holder.itemView.getContext(); // Get valid context
            Intent intent = new Intent(context, ParcelTrackingActivity.class);
            intent.putExtra("shipment_id", shipment.getId()); // Pass data if needed
            context.startActivity(intent);
        });
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
