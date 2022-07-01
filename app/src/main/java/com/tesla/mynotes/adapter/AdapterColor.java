package com.tesla.mynotes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tesla.mynotes.R;

import java.util.List;

public class AdapterColor extends RecyclerView.Adapter<AdapterColor.ViewHolder> {
    List<Integer> listColor;

    AdapterColor.colorListener listenerColor;

    public interface colorListener{
        void clickColor(int positionOfAdapter);
    }

    public AdapterColor(List<Integer> listColor, colorListener listenerColor) {
        this.listColor = listColor;
        this.listenerColor = listenerColor;
    }

    @Override
    public AdapterColor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_list_color, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterColor.ViewHolder holder, int position) {
        holder.colorCard.setCardBackgroundColor(ContextCompat.getColor(holder.colorLay.getContext(), listColor.get(position)));
    }

    @Override
    public int getItemCount() {
        return listColor.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ConstraintLayout colorLay;
        CardView colorCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        colorLay = itemView.findViewById(R.id.id_constraint_color);
        colorCard = itemView.findViewById(R.id.id_card_color);
        itemView.setOnClickListener(this::onClick);

        }

        @Override
        public void onClick(View view) {
            listenerColor.clickColor(getAdapterPosition());
        }
    }
}
