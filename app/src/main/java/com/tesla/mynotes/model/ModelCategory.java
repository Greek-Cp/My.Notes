package com.tesla.mynotes.model;

import java.util.List;

public class ModelCategory {
    String nameCategory;
    List<ModelNotes> modelNotesList;
    int colorCategory;
    public ModelCategory(String nameCategory, int colorCategory){
        this.nameCategory = nameCategory;
        this.colorCategory = colorCategory;
    }
    public ModelCategory(String nameCategory, List<ModelNotes> modelNotesList) {
        this.nameCategory = nameCategory;
        this.modelNotesList = modelNotesList;
    }

    public int getColorCategory() {
        return colorCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }
    public List<ModelNotes> getModelNotesList() {
        return modelNotesList;
    }

}
