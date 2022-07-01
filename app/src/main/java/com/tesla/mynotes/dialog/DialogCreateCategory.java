package com.tesla.mynotes.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class DialogCreateCategory extends AppCompatDialogFragment {
    private EditText editTextCategoryTittle;
    DialogCreateCategory.DialogTaskListener listener;
    private String tittleStr , contentStr;
    private RecyclerView recyclerViewColor;
    public DialogCreateCategory(DialogCreateCategory.DialogTaskListener listener){
        this.listener = listener;
    }
    int colorCard = 0;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog =  new AlertDialog.Builder(getActivity());
        LayoutInflater lI = getActivity().getLayoutInflater();
        View v = lI.inflate(R.layout.category_name_dialog,null);
        dialog.setView(v).setTitle("Tambahkan Kategori ")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String newTittle = editTextCategoryTittle.getText().toString();
                listener.apply(newTittle,colorCard);
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
        editTextCategoryTittle = v.findViewById(R.id.EditText_NameCategory);
        editTextCategoryTittle.setText(tittleStr, TextView.BufferType.NORMAL);

        editTextCategoryTittle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(editable.toString() +  " wasu");

            }
        });
        return dialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public interface DialogTaskListener{
        void apply(String newTittle, int color);
    }
}
