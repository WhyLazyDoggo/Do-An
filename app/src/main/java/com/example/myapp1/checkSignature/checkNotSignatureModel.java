package com.example.myapp1.checkSignature;

public class checkNotSignatureModel {
    String id;
    String name;
    String fullname;
    String person;
    String count;
    Integer MAX_SIZE= 16;
    int profileImagine;
    public checkNotSignatureModel(String id, String name, String person, String count,int profileImagine) {
        this.id = id;
        this.name = name;
        this.fullname = name;
        if (this.name.length()>MAX_SIZE)
            this.name = name.substring(0, MAX_SIZE) + "...";
        this.person = person;
        this.count = count;
        this.profileImagine = profileImagine;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public checkNotSignatureModel() {
    }

    public int getProfileImagine() {
        return profileImagine;
    }

    public void setProfileImagine(int profileImagine) {
        this.profileImagine = profileImagine;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
