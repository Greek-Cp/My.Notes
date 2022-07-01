package com.tesla.mynotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tesla.mynotes.R;
import com.tesla.mynotes.model.ModelCategory;
import com.tesla.mynotes.model.ModelNotes;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class AdapterCardNote extends RecyclerView.Adapter<AdapterCardNote.ViewHolder> {

    List<ModelNotes> listDataNote;
    Context ctx;
    AdapterCardNote.CardNoteListener listenerDelete;

    String categori;
    public interface CardNoteListener{
        void deleteItemClickListener(int positionOfItem);
        void editItemClickListener(int positionOfCard);
        void clickItemClickListener(int positionOfCard);

    }



    public AdapterCardNote(List<ModelNotes> listDataNote,Context context,AdapterCardNote.CardNoteListener listener,String categori){
        this.listDataNote = listDataNote;
        this.ctx = context;
        this.listenerDelete=  listener;
        this.categori = categori;

    }
    @Override
    public AdapterCardNote.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter_card, parent,false );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCardNote.ViewHolder holder, int position) {
             //   System.out.println("categoriy == " + categori + " \n" + listDataNote.get(position).getNameCategory() + " catge" + " \nname tittle" + listDataNote.get(position).getTittleNotes() );
                holder.textViewTittle.setText(listDataNote.get(position).getTittleNotes());
                holder.textViewContent.setHtml(listDataNote.get(position).getContentNotes());
                holder.textViewContent.setBackgroundColor(ActivityCompat.getColor(ctx,listDataNote.get(position).getColor()));

                holder.cardColor.setCardBackgroundColor(ActivityCompat.getColor(ctx,listDataNote.get(position).getColor()));

                holder.showMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(ctx.getApplicationContext(), holder.showMenu);
                        popupMenu.inflate(R.menu.menu_nav_card);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.id_btn_delete_card:
                                        Toast.makeText(ctx.getApplicationContext(), "Menghapus Catatan", Toast.LENGTH_LONG).show();
                                        listenerDelete.deleteItemClickListener(holder.getAdapterPosition());
                                        break;
                                    case R.id.id_btn_edit:
                                        Toast.makeText(ctx.getApplicationContext(), "Mengedit Catatan", Toast.LENGTH_LONG).show();
                                        listenerDelete.editItemClickListener(holder.getAdapterPosition());
                                        notifyDataSetChanged();
                                        break;
                                    default:

                                        break;
                                }
                                return false;

                            }
                        });
                        popupMenu.show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        if(listDataNote != null){
            return listDataNote.size();
        } else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardColor;
        TextView textViewTittle;
        RichEditor textViewContent;
        ImageView  showMenu, hiddenMenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardColor = itemView.findViewById(R.id.id_card_color_list_notes);
            textViewTittle = itemView.findViewById(R.id.id_textview_tittle_notes);
            textViewContent = itemView.findViewById(R.id.id_textview_content_cards);
            showMenu = itemView.findViewById(R.id.id_btn_show);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {
            listenerDelete.clickItemClickListener(getAdapterPosition());
        }
    }
}
