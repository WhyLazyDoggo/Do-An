package com.example.myapp1.createSignGroup;

public class TaoNhomModel {
    int profileImage;
    String id;
    String username;
    String decreption;
    String publickey;

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public TaoNhomModel(int profileImage, String id, String username, String decreption,String publickey) {
        this.profileImage = profileImage;
        this.id = id;
        this.username = username;
        this.decreption = decreption;
        this.publickey = publickey;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public TaoNhomModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDecreption() {
        return decreption;
    }

    public void setDecreption(String decreption) {
        this.decreption = decreption;
    }
}
