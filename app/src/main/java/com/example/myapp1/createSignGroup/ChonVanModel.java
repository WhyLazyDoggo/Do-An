package com.example.myapp1.createSignGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChonVanModel {

    String id;
    String name;
    String date;
    Integer MAX_SIZE = 6;

    public ChonVanModel(String id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public ChonVanModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
