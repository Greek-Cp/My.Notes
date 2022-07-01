package com.tesla.mynotes.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tesla.mynotes.R;
import com.tesla.mynotes.Util.UtilAdapter;
import com.tesla.mynotes.adapter.AdapterCardNote;
import com.tesla.mynotes.adapter.AdapterCategoryNote;
import com.tesla.mynotes.dialog.DialogEditContentCard;
import com.tesla.mynotes.model.ModelCategory;
import com.tesla.mynotes.model.ModelNotes;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListNotesFragment extends Fragment {

    public ListNotesFragment() {
        // Required empty public constructor
    }

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FrameLayout frameLayout;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = view.findViewById(R.id.id_frame_layout_base);
        initializeID(view);

        initializeAdapterColor();
        initializeAdapterCategory(getActivity().getApplicationContext());
        sharedPreferences = getActivity().getSharedPreferences("LIST_PREF", Context.MODE_PRIVATE);

    }


    RecyclerView recyclerViewCard, recyclerViewColor,recyclerViewCategory;
    AdapterCardNote adapterCardNote;
    AdapterCardNote.CardNoteListener deleteListener;
    ScrollView scrollView;

    void saveToPrefences(List<ModelNotes> listFilterNotes, List<ModelNotes> defaultModelNotes, String category) {
        for(int y = 0; y < listFilterNotes.size(); y++){
            for(int t = 0; t < defaultModelNotes.size(); t++){
                int postFilter = listFilterNotes.get(y).getDefaultPosition();
                int postDefault = defaultModelNotes.get(t).getDefaultPosition();
                if(postFilter == postDefault){
                    defaultModelNotes.set(postDefault,listFilterNotes.get(y));
                    //System.out.println("get category listfilter = " + listFilterNotes.get(y).getNameCategory() + "\n get category default = " + defaultModelNotes.get(t).getNameCategory());
                    //Toast.makeText(getActivity(), "item " + postFilter + "is same", //Toast.LENGTH_LONG).show();
                }
            }
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LIST_PREF", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        sharedPreferences.edit().putString("NOTES_LIST", gson.toJson(defaultModelNotes)).commit();
    }
    void saveToPrefencesDelete(List<ModelNotes> defaultModelNotes) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LIST_PREF", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        sharedPreferences.edit().putString("NOTES_LIST", gson.toJson(defaultModelNotes)).commit();
    }

    public void saveModelCategoryList(List<ModelCategory> modelCategoryList, Context ctx){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("LIST_CATEGORY_MODEL",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("MODEL_CATEGORY",new Gson().toJson(modelCategoryList)).commit();
        //Toast.makeText(ctx.getApplicationContext(), "size category = " + modelCategoryList.size(),//Toast.LENGTH_LONG)


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
            modelCategoryList.add(new ModelCategory("test 1",R.color.white));
            modelCategoryList.add(new ModelCategory("test 2",R.color.card_blue));
            saveModelCategoryList(modelCategoryList, ctx);
        }
        AdapterCategoryNote.CategoryClickListener categoryClickListener = new AdapterCategoryNote.CategoryClickListener() {
            @Override
            public void clickCategoryListener(int positionOfCategory,View v) {
                initializeAdapterCardListNotes(modelCategoryList.get(positionOfCategory).getNameCategory(),recyclerViewCategory.findViewHolderForAdapterPosition(positionOfCategory));
           }
        };
        adapterCategoryNotes = new AdapterCategoryNote(modelCategoryList, categoryClickListener,ctx);
        recyclerViewCategory.setAdapter(adapterCategoryNotes);
    }
    void initializeAdapterCardListNotes(String category, RecyclerView.ViewHolder v) {
         List<ModelNotes> listNotes = retreiveListNotesFromSharedPref();
        List<ModelNotes> filterNotes = new ArrayList<>();
        if(listNotes != null){
            for(int i = 0; i < listNotes.size(); i++){
                if(listNotes.get(i).getNameCategory().equals(category)){
                    filterNotes.add(listNotes.get(i));
                }
            }
        }
       deleteListener = new AdapterCardNote.CardNoteListener() {
            @Override
            public void deleteItemClickListener(int positionOfItem) {

                listNotes.remove(filterNotes.get(positionOfItem).getDefaultPosition());
                filterNotes.remove(positionOfItem);

                saveToPrefencesDelete(listNotes);
                adapterCardNote.notifyDataSetChanged();
            }
            @Override
            public void editItemClickListener(int positionOfCard) {
                DialogEditContentCard.DialogTaskListener dialogTaskListener = new DialogEditContentCard.DialogTaskListener() {
                    @Override
                    public void apply(String newTittle, String newContent, int color) {
                        if(color == 0){
                            ModelNotes newModel = new ModelNotes(newTittle, newContent, filterNotes.get(positionOfCard).getDate(), filterNotes.get(positionOfCard).getColor(),filterNotes.get(positionOfCard).getNameCategory(),filterNotes.get(positionOfCard).getDefaultPosition());
                            filterNotes.set(positionOfCard, newModel);
                            saveToPrefences(filterNotes,listNotes,category);
                           adapterCardNote.notifyDataSetChanged();

                        }else{
                            ModelNotes newModel = new ModelNotes(newTittle, newContent, filterNotes.get(positionOfCard).getDate(), color,filterNotes.get(positionOfCard).getNameCategory(),filterNotes.get(positionOfCard).getDefaultPosition());
                            filterNotes.set(positionOfCard, newModel);
                            saveToPrefences(filterNotes,listNotes,category);
                            adapterCardNote.notifyDataSetChanged();
                        }
                    }
                };

                adapterCardNote.notifyDataSetChanged();
                DialogEditContentCard dialogEdit = new DialogEditContentCard(filterNotes.get(positionOfCard).getTittleNotes(), filterNotes.get(positionOfCard).getContentNotes(), dialogTaskListener);
                dialogEdit.show(getParentFragmentManager(), "TVask Dialog");
            }

            @Override
            public void clickItemClickListener(int positionOfCard) {
                Toast.makeText(getActivity().getApplicationContext(),"clicked" , Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(getId(),new ViewNoteFragment(filterNotes.get(positionOfCard).getTittleNotes(),filterNotes.get(positionOfCard).getContentNotes(),filterNotes.get(positionOfCard).getColor()));
                fragmentTransaction.commit();

            }
        };
        adapterCardNote = new AdapterCardNote(filterNotes, getActivity().getApplicationContext(), deleteListener,category);
        adapterCardNote.notifyDataSetChanged();
        recyclerViewCard.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewCard.setAdapter(adapterCardNote);
    }

    List<ModelNotes> retreiveListNotesFromSharedPref() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ModelNotes>>() {
        }.getType();
        List<ModelNotes> list = gson.fromJson(sharedPreferences.getString("NOTES_LIST", ""), type);
        return list;
    }

    void initializeAdapterColor() {
        UtilAdapter utilAdapter = new UtilAdapter(recyclerViewColor);
        utilAdapter.buildRecycleViewColor(utilAdapter.initializeAdapterColorList(scrollView, getActivity().getApplicationContext()));
    }

    void initializeID(View v) {
        recyclerViewCard = v.findViewById(R.id.id_rec_list_notes);
        recyclerViewColor = v.findViewById(R.id.id_rec_list_color);
        recyclerViewCategory = v.findViewById(R.id.id_rec_list_category_item);
        scrollView = v.findViewById(R.id.id_scrollview);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }
}


