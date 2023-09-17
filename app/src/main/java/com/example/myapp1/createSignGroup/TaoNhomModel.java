package com.example.myapp1.createSignGroup;

public class TaoNhomModel {
    int profileImage;
    String username;
    String decreption;

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public TaoNhomModel(int profileImage, String username, String decreption) {
        this.profileImage = profileImage;
        this.username = username;
        this.decreption = decreption;
    }

    public TaoNhomModel() {
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
