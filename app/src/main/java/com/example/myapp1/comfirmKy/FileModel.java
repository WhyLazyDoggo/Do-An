package com.example.myapp1.comfirmKy;

public class FileModel {
    String id;
    String id_cuavanban;
    String id_nhomKy;
    String name;
    String fullname;
    String date;
    String person;
    String datafile;
    Integer MAX_SIZE= 16;
    int profileImagine;

    public FileModel(String id, String id_cuavanban,String id_nhomKy, String name,String datafile, String date, int profileImagine) {
        this.id = id;
        this.id_cuavanban = id_cuavanban;
        this.id_nhomKy = id_nhomKy;
        this.name = name;
        this.datafile = datafile;
        this.fullname = name;
        if (this.name.length()>MAX_SIZE)
            this.name = name.substring(0, MAX_SIZE) + "...";
        this.date = date;
        this.profileImagine = profileImagine;
    }

    public FileModel() {

    }

    public String getId_nhomKy() {
        return id_nhomKy;
    }

    public void setId_nhomKy(String id_nhomKy) {
        this.id_nhomKy = id_nhomKy;
    }

    public String getId_cuavanban() {
        return id_cuavanban;
    }

    public void setId_cuavanban(String id_cuavanban) {
        this.id_cuavanban = id_cuavanban;
    }

    public String getDatafile() {
        return datafile;
    }

    public void setDatafile(String datafile) {
        this.datafile = datafile;
    }

    public int getProfileImagine() {
        return profileImagine;
    }

    public void setProfileImagine(int profileImagine) {
        this.profileImagine = profileImagine;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
