package com.lalita.bankargapp.Clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lalita.bankargapp.R;

import java.util.List;
import android.widget.TextView;

public class TransaccionAdapter extends RecyclerView.Adapter<TransaccionAdapter.TransaccionViewHolder> {

    private List<Transaccion> transaccionList;
    private Context context;

    public TransaccionAdapter(List<Transaccion> transaccionList, Context context) {
        this.transaccionList = transaccionList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransaccionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_transaccion, parent, false);
        return new TransaccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaccionViewHolder holder, int position) {
        Transaccion transaccion = transaccionList.get(position);
        holder.descripcion.setText(transaccion.getDescripcion());
        holder.fechaTransaccion.setText(transaccion.getFecha_transaccion());
        holder.monto.setText("$" + String.format("%.2f", transaccion.getMonto()));

        // Cambiar color dependiendo del id_tipo_transaccion
        if (transaccion.getId_tipo_transaccion() == 0 || transaccion.getId_tipo_transaccion() == 5 ||
                transaccion.getId_tipo_transaccion() == 6 || transaccion.getId_tipo_transaccion() == 7) {
            // Mostrar en verde para los tipos 1, 6, 7 y 8
            holder.monto.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark));
        } else {
            // Mostrar en rojo para los demás tipos
            holder.monto.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
        }
    }

    @Override
    public int getItemCount() {
        return transaccionList.size();
    }

    public class TransaccionViewHolder extends RecyclerView.ViewHolder {
        TextView descripcion, monto, fechaTransaccion;

        public TransaccionViewHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.tvDescripcion);
            monto = itemView.findViewById(R.id.tvMonto);
            fechaTransaccion = itemView.findViewById(R.id.tvFechaTransaccion);
        }
    }
}

