package com.example.myapp1.createSignGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChonVanModel {

    String id;
    String name;
    String fullname;
    String date;
    Integer MAX_SIZE = 18;

    int profileImagine;

    public int getProfileImagine() {
        return profileImagine;
    }

    public void setProfileImagine(int profileImagine) {
        this.profileImagine = profileImagine;
    }

    public ChonVanModel(String id, String name, String date, int profileImagine) {
        this.id = id;
        this.name = name;
        this.fullname = name;
        if (this.name.length()>MAX_SIZE)
            this.name = name.substring(0, MAX_SIZE) + "...";
        this.date = date;
        this.profileImagine = profileImagine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
