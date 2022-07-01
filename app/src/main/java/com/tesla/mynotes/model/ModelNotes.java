package com.tesla.mynotes.model;

public class ModelNotes {
    private String tittleNotes;
    private String contentNotes;
    private String date;
    private String nameCategory;
    private int color;
    private int defaultPosition;


    public ModelNotes(){

    }
    public ModelNotes(String tittleNotes, String contentNotes, String date, int color, String nameCategory,int defaultPosition) {
        this.tittleNotes = tittleNotes;
        this.contentNotes = contentNotes;
        this.date = date;
        this.color = color;
        this.nameCategory = nameCategory;
        this.defaultPosition = defaultPosition;
    }

    public int getDefaultPosition() {
        return defaultPosition;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTittleNotes() {
        return tittleNotes;
    }

    public void setTittleNotes(String tittleNotes) {
        this.tittleNotes = tittleNotes;
    }

    public String getContentNotes() {
        return contentNotes;
    }

    public void setContentNotes(String contentNotes) {
        this.contentNotes = contentNotes;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        date = date;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public void setDefaultPosition(int defaultPosition) {
        this.defaultPosition = defaultPosition;
    }
}
