package com.tesla.mynotes.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tesla.mynotes.R;
import com.tesla.mynotes.Util.UtilAdapter;
import com.tesla.mynotes.adapter.AdapterColor;

public class DialogEditContentCard extends AppCompatDialogFragment {
    private EditText editTextTittle, editTextContent;
    DialogEditContentCard.DialogTaskListener listener;
    private String tittleStr , contentStr;
    private RecyclerView recyclerViewColor;
    public DialogEditContentCard(String tittleCard, String contentCard, DialogEditContentCard.DialogTaskListener listener){
        this.tittleStr = tittleCard;
        this.contentStr = contentCard;
        this.listener = listener;
    }
    int colorCard = 0;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog =  new AlertDialog.Builder(getActivity());
        LayoutInflater lI = getActivity().getLayoutInflater();
        View v = lI.inflate(R.layout.edit_card_dialog,null);
        dialog.setView(v).setTitle("Edit Catatan ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }   
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String newTittle = editTextTittle.getText().toString();
                String newContent = editTextContent.getText().toString();
                System.out.println("color card = " + colorCard);
                listener.apply(newTittle,newContent,colorCard);
            }
        });

        recyclerViewColor = v.findViewById(R.id.id_rec_list_color);
        UtilAdapter utilAdapter = new UtilAdapter(recyclerViewColor );
        utilAdapter.initializeAdapterColorList(v.getContext());
        AdapterColor.colorListener colorListener = new AdapterColor.colorListener() {
            @Override
            public void clickColor(int positionOfAdapter) {

                colorCard = utilAdapter.getColorAvailable().get(positionOfAdapter);
            }
        };

        AdapterColor adapterColor = new AdapterColor(utilAdapter.getColorAvailable(),colorListener);
        utilAdapter.buildRecycleViewColor(adapterColor);

        editTextTittle = v.findViewById(R.id.EditText_NAMETASK);
        editTextContent = v.findViewById(R.id.EditText_URL_AIRDROP);
        editTextTittle.setText(tittleStr, TextView.BufferType.NORMAL);
        editTextContent.setText(contentStr, TextView.BufferType.NORMAL);
        return dialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public interface DialogTaskListener{
        void apply(String newTittle,String newContent,int color);
    }
}
