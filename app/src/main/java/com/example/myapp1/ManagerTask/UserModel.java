package com.example.myapp1.ManagerTask;

import java.io.Serializable;

public class UserModel implements Serializable {


    String id;

    int profileImage;
    String username;
    String fullname;
    String role;
    String phong_ban;
    String status;
    String created_at;
    String update_at;

    public UserModel(String id, int profileImage, String username, String fullname, String role, String phong_ban, String status, String created_at, String update_at) {
        this.id = id;
        this.profileImage = profileImage;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.phong_ban = phong_ban;
        this.status = status;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    public UserModel() {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhong_ban() {
        return phong_ban;
    }

    public void setPhong_ban(String phong_ban) {
        this.phong_ban = phong_ban;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
