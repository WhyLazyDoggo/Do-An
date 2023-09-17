package com.example.myapp1.comfirmKy;

public class FileModel {
    String id;
    String name;
    String person;
    Integer MAX_SIZE= 6;

    public FileModel(String id, String name, String person) {
        this.id = id;
        this.name = name;
        this.person = person;
        if (this.name.length() > MAX_SIZE) {
            this.name = name.substring(0, MAX_SIZE) + "...";
        }
    }

    public FileModel() {

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
