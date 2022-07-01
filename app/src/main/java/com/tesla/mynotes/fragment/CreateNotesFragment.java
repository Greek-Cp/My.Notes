package com.tesla.mynotes.fragment;

import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.reflect.TypeToken;
import com.tesla.mynotes.R;
import com.tesla.mynotes.Util.UtilAdapter;
import com.tesla.mynotes.adapter.AdapterCategoryNote;
import com.tesla.mynotes.dialog.DialogCreateCategory;
import com.tesla.mynotes.model.ModelCategory;
import com.tesla.mynotes.model.ModelNotes;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.richeditor.RichEditor;

public class CreateNotesFragment extends Fragment {

    public CreateNotesFragment() {
        // Required empty public constructor
    }

    DialogFragment dialogFailed;
    void failedDialogCategoryShow(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.pop_up_error_save_category,null);
        dialog.setView(v);
        dialog.setCancelable(true);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.hide();

            }
        },1000);
    }
    void succesSaveNoteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.pop_up_succes_save_notes,null);
        dialog.setView(v);
        dialog.setCancelable(true);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.hide();

            }
        },1000);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeID(view);
        initializeAdapterColor("");
        initializeAdapterCategory(getActivity().getApplicationContext());
        sharedPreferences = getActivity().getSharedPreferences("LIST_PREF", Context.MODE_PRIVATE);
        try {
            setImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    void checkDarkMode(){
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){

        }
    }
    TextInputEditText editTextTittleNotes;
    RichEditor editTextMyNotes;
    MaterialButton buttonSaveNotes;
    RecyclerView recyclerViewListColor,recyclerViewListCategory;
    SharedPreferences sharedPreferences;
    ImageView btnChangeWallpaper,wallpaperMain;
    CardView cardNotes;
    List<ModelNotes> retriveListNotesFromSharedPref(){
        try{
            Gson gson = new Gson();
            Type type = new TypeToken<List<ModelNotes>>(){}.getType();
            List<ModelNotes> list = (List<ModelNotes>) gson.fromJson(sharedPreferences.getString("NOTES_LIST", ""), type);;
            return list;
        } catch (NullPointerException e){
            return new ArrayList<>();
        }
    }

    ActivityResultLauncher<String> mActivityResult = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result != null){
                editTextMyNotes.insertImage(result.toString(),"image",200);
                editTextMyNotes.setAlignCenter();
                editTextMyNotes.setAlignLeft();
            }

        }
    });
    void initializeID(View v){
        editTextMyNotes = v.findViewById(R.id.id_edit_text_create_note);
        editTextMyNotes.setPadding(10,10,10,10);
        editTextMyNotes.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(),editTextMyNotes);
                popupMenu.inflate(R.menu.menu_item_add_note);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.id_menu_add_image:
                                mActivityResult.launch("image/*");
                                break;
                            case R.id.id_menu_add_bold:
                                editTextMyNotes.setBold();
                                break;
                        }
                        return true;

                    }
                });
                return true;
            }
        });
        editTextTittleNotes = v.findViewById(R.id.id_edit_text_create_tittle_notes);
        buttonSaveNotes = v.findViewById(R.id.id_btn_save_note);
        recyclerViewListColor = v.findViewById(R.id.id_rec_list_color);
        recyclerViewListCategory = v.findViewById(R.id.id_rec_list_category_item);
        btnChangeWallpaper = v.findViewById(R.id.id_btn_change_wallpaper_main);
        wallpaperMain = v.findViewById(R.id.id_image_wallpaper);
        wallpaperMain.setScaleType(ImageView.ScaleType.FIT_XY);
        btnChangeWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });
        cardNotes = v.findViewById(R.id.id_card_color_create_notes);
    }
    void setImage() throws FileNotFoundException {
    }
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            InputStream inputStream = null;
            try {
                inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(result);
                Bitmap bitmapFactory = BitmapFactory.decodeStream(inputStream);
                wallpaperMain.setImageBitmap(bitmapFactory);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    });


    public void saveModelCategoryList(List<ModelCategory> modelCategoryList, Context ctx){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("LIST_CATEGORY_MODEL",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("MODEL_CATEGORY",new Gson().toJson(modelCategoryList)).commit();
        //////Toast.makeText(ctx.getApplicationContext(), "size category = " + modelCategoryList.size(),//////Toast.LENGTH_LONG)


    }
    SharedPreferences sharedPreferencesCategory;
    public List<ModelCategory> getListModelCategory(Context context){
        if(sharedPreferencesCategory == null){

        }
        Gson gson = new Gson();
        Type TypeCategory = new TypeToken<List<ModelCategory>>(){}.getType();
        String jsonCategoryModel = sharedPreferencesCategory.getString("MODEL_CATEGORY","");
        List<ModelCategory> modelCategoryList = gson.fromJson(jsonCategoryModel,TypeCategory);
        return modelCategoryList;

    }


    AdapterCategoryNote adapterCategoryNotes;
    List<ModelCategory> modelCategoryList;

    public void initializeAdapterCategory(Context ctx){
        sharedPreferencesCategory = ctx.getSharedPreferences("LIST_CATEGORY_MODEL",Context.MODE_PRIVATE);
        modelCategoryList = getListModelCategory(ctx);
        if(modelCategoryList == null){
            modelCategoryList = new ArrayList<>();
            modelCategoryList.add(new ModelCategory("Catatan Utama",R.color.card_white));
            modelCategoryList.add(new ModelCategory("Tambahkan Kategori",R.color.card_white));
            saveModelCategoryList(modelCategoryList, ctx);
        }
        AdapterCategoryNote.CategoryClickListener categoryClickListener = new AdapterCategoryNote.CategoryClickListener() {
            @Override
            public void clickCategoryListener(int positionOfCategory, View v) {
                if(positionOfCategory == modelCategoryList.size() - 1){

                    DialogCreateCategory.DialogTaskListener listener = new DialogCreateCategory.DialogTaskListener() {
                        @Override
                        public void apply(String newTittle,int color) {
                            if(!newTittle.isEmpty() || color != 0){
                                modelCategoryList.add(modelCategoryList.size() - 1,new ModelCategory(newTittle,color));
                                adapterCategoryNotes.notifyDataSetChanged();
                                saveModelCategoryList(modelCategoryList, ctx);

                            } else{
                                //////System.out.println("ERROR !");
                                failedDialogCategoryShow();
                            }
                        }
                    };
                    DialogCreateCategory dialogCreateCategory = new DialogCreateCategory(listener);
                    dialogCreateCategory.show(getParentFragmentManager(), "Dialog");

                    //////Toast.makeText(ctx,"Kategori Baru", //////Toast.LENGTH_LONG).show();
                    adapterCategoryNotes.notifyDataSetChanged();
                } else{
                    //////Toast.makeText(ctx," Baru" + modelCategoryList.get(positionOfCategory).getNameCategory(),//////Toast.LENGTH_LONG).show();
                    initializeAdapterColor(modelCategoryList.get(positionOfCategory).getNameCategory());
                }
            }
        };
        adapterCategoryNotes = new AdapterCategoryNote(modelCategoryList, categoryClickListener,ctx);
        recyclerViewListCategory.setAdapter(adapterCategoryNotes);
    }



    void initializeAdapterColor(String category){
        UtilAdapter.UtilColor utilColor = new UtilAdapter.UtilColor() {
            @Override
            public void getColor(int color) {
                btnChangeWallpaper.setBackgroundTintList(ColorStateList.valueOf(color));
                //////System.out.println(color + "colors" + " category = " + category);
                Toast.makeText(getActivity().getApplicationContext(),"Berhasil Memilih Warna ",Toast.LENGTH_SHORT).show();
                insertTextToList(color,category);
            }
        };
        UtilAdapter utilAdapter = new UtilAdapter(recyclerViewListColor,utilColor);
        utilAdapter.buildRecycleViewColor(utilAdapter.initializeAdapterColorList(cardNotes,getActivity().getApplicationContext()));
    }
    void insertTextToList(int color, String category){
        buttonSaveNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ModelNotes> listNotes = retriveListNotesFromSharedPref();
                if(listNotes == null){
                    listNotes = new ArrayList<>();
                }
                //////System.out.println(color + "colors" + " category = " + category);
                String tittleNotes = ""+editTextTittleNotes.getText().toString() + "";
                String contentNotes = "  "+editTextMyNotes.getHtml();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                if(category.isEmpty()){
                    listNotes.add(new ModelNotes(tittleNotes,contentNotes,formattedDate,color,"Catatan Utama",listNotes.size()));

                } else{
                    listNotes.add(new ModelNotes(tittleNotes,contentNotes,formattedDate,color,category,listNotes.size()));

                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                editor.putString("NOTES_LIST",
                        gson.toJson(listNotes));
                editor.commit();
                succesSaveNoteDialog();
                Toast.makeText(getActivity().getApplicationContext(),"Catatan Tersimpan",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_notes, container, false);
    }
}