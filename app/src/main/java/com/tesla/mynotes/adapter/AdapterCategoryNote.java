package com.tesla.mynotes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tesla.mynotes.R;
import com.tesla.mynotes.model.ModelCategory;

import java.util.List;

public class AdapterCategoryNote extends RecyclerView.Adapter<AdapterCategoryNote.ViewHolder> {

    List<ModelCategory> modelCategoryList;
    AdapterCategoryNote.CategoryClickListener categoryClickListener;
    Context ctx;

    public AdapterCategoryNote(List<ModelCategory> modelCategoryList, CategoryClickListener categoryClickListener, Context ctx) {
        this.modelCategoryList = modelCategoryList;
        this.categoryClickListener = categoryClickListener;
        this.ctx = ctx;
    }

    public interface CategoryClickListener{
        void clickCategoryListener(int positionOfCategory,View v);
    };
    @Override
    public AdapterCategoryNote.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_category_note,parent,false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterCategoryNote.ViewHolder holder, int position) {
        holder.textViewCategory.setText(modelCategoryList.get(position).getNameCategory());
        holder.cardViewCategory.setCardBackgroundColor(ActivityCompat.getColor(ctx,modelCategoryList.get(position).getColorCategory()));
  //      System.out.println(modelCategoryList.get(position).getColorCategory() + " category of color ");
    }

    @Override
    public int getItemCount() {
        if(modelCategoryList == null){
            return 0;
        } else{
            return modelCategoryList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewCategory;
        CardView cardViewCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewCategory = itemView.findViewById(R.id.id_card_col);
            textViewCategory = itemView.findViewById(R.id.id_tv_category);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            categoryClickListener.clickCategoryListener(getAdapterPosition(),view);
        }
    }
}
