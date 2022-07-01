package com.tesla.mynotes.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tesla.mynotes.R;
import com.tesla.mynotes.adapter.AdapterCategoryNote;
import com.tesla.mynotes.adapter.AdapterColor;
import com.tesla.mynotes.model.ModelCategory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UtilAdapter {
    RecyclerView recyclerViewTarget;
    UtilAdapter.UtilColor utilColor;
    UtilAdapter.clickListenerCategoryNote listenerCategoryNote;
    Context ctx;
    public interface UtilColor{

        void getColor(int color);
    }
    public interface UtilGetCategory{
        String getCategory(String category);
    }

    public UtilAdapter(RecyclerView recyclerView){
        this.recyclerViewTarget = recyclerView;
    }

    public UtilAdapter(RecyclerView recyclerView,Context ctx){
        this.recyclerViewTarget = recyclerView;
        this.ctx = ctx;
    }
    public UtilAdapter(RecyclerView recyclerView,UtilAdapter.UtilColor utilColor){
        this.recyclerViewTarget = recyclerView;
        this.utilColor = utilColor;
    }

    public interface clickListenerCategoryNote{
        void clickCategory(int position);
    }


    public List<Integer> getColorAvailable(){
        List<Integer> listColor = new ArrayList<>();
        listColor.add(R.color.white);
        listColor.add(R.color.card_blue);
        listColor.add(R.color.card_green);
        listColor.add(R.color.card_yellow);
        listColor.add(R.color.card_pink);
        listColor.add(R.color.card_tosca);
        listColor.add(R.color.teal_700);
        listColor.add(R.color.purple_200);
        listColor.add(R.color.card_darkmode);
        listColor.add(R.color.black);
        return listColor;
    }
    private AdapterColor.colorListener colorListener;
    private AdapterColor adapterColor;
    int color;
    public AdapterColor initializeAdapterColorList(CardView Target, Context context){
        colorListener = new AdapterColor.colorListener() {
            @Override
            public void clickColor(int positionOfAdapter) {
                color = getColorAvailable().get(positionOfAdapter);
                utilColor.getColor(color);
                Target.setCardBackgroundColor(ActivityCompat.getColor(context,color));
                System.out.println(color + "color ");
            }
        };
        adapterColor = new AdapterColor(getColorAvailable(),colorListener);
        return adapterColor;
    }

    public AdapterColor initializeAdapterColorList(Context context){
        colorListener = new AdapterColor.colorListener() {
            @Override
            public void clickColor(int positionOfAdapter) {
                color = getColorAvailable().get(positionOfAdapter);
                utilColor.getColor(color);
                System.out.println(color + "color ");
            }
        };
        adapterColor = new AdapterColor(getColorAvailable(),colorListener);
        return adapterColor;
    }

    public int getColorOfAdapter(){
            return this.color;
    }
    public AdapterColor initializeAdapterColorList(ScrollView Target, Context context){
        colorListener = new AdapterColor.colorListener() {
            @Override
            public void clickColor(int positionOfAdapter) {
                if(getColorAvailable().get(positionOfAdapter) == R.color.white){
                    TextView tvListNotes = Target.findViewById(R.id.id_textview_list_notes),
                    tvDeskripsi = Target.findViewById(R.id.id_textview_deskripsi_list_notes);
                    tvListNotes.setTextColor(ActivityCompat.getColor(context, R.color.black));
                    tvDeskripsi.setTextColor(ActivityCompat.getColor(context, R.color.black));
                } else{
                    TextView tvListNotes = Target.findViewById(R.id.id_textview_list_notes),
                            tvDeskripsi = Target.findViewById(R.id.id_textview_deskripsi_list_notes);
                    tvListNotes.setTextColor(ActivityCompat.getColor(context, R.color.white));
                    tvDeskripsi.setTextColor(ActivityCompat.getColor(context, R.color.white));
                }
                Target.setBackgroundColor(ActivityCompat.getColor(context, getColorAvailable().get(positionOfAdapter)));
            }
        };
        adapterColor = new AdapterColor(getColorAvailable(),colorListener);
        return adapterColor;
    }

    public void buildRecycleViewCategory(AdapterCategoryNote adapterCategoryNote){
        recyclerViewTarget.setAdapter(adapterCategoryNote);;
    }
    public void buildRecycleViewColor(AdapterColor adapterColor){
        recyclerViewTarget.setAdapter(adapterColor);
    }
}
