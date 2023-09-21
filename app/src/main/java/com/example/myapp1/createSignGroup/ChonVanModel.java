package com.example.myapp1.createSignGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChonVanModel {

    String id;
    String date;
    Integer MAX_SIZE = 6;

    int profileImagine;

    public int getProfileImagine() {
        return profileImagine;
    }

    public void setProfileImagine(int profileImagine) {
        this.profileImagine = profileImagine;
    }

    public ChonVanModel(String id, String date) {
        this.id = id;
        this.date = date;
    }

    public ChonVanModel(String id, String date, int profileImagine) {
        this.id = id;
        this.date = date;
        this.profileImagine = profileImagine;
    }

    public ChonVanModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
