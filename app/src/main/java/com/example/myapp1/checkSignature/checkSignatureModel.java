package com.example.myapp1.checkSignature;

import java.util.List;

public class checkSignatureModel {
    String id;
    String name;
    String person;
    String datafile;
    String fullname;
    String signature;
    String group_key;
    String date;
    int profileImagine;
    Integer MAX_SIZE= 16;

    String tmp;
    List<String> subMember;

    public checkSignatureModel(String id, String name,String datafile, String date,String signature,String group_key, int profileImagine) {
        this.id = id;
        this.name = name;
        this.datafile = datafile;
        this.fullname = name;
        if (this.name.length()>MAX_SIZE)
            this.name = name.substring(0, MAX_SIZE) + "...";
        this.date = date;
        this.group_key= group_key;
        this.signature= signature;
        this.profileImagine = profileImagine;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public void addMember(String Member) {
        subMember.add(Member);
    }

    public List<String> getSubMember() {
        return subMember;
    }

    public void setSubMember(List<String> subMember) {
        this.subMember = subMember;
    }

    public void displayInfo() {
        System.out.println(getId());
        System.out.println(getName());
        System.out.println(getSignature());
        System.out.println("Giá trị check nè: "+getTmp());
        for (String subject : subMember) {
            System.out.println(subject);
        }
    }
    public checkSignatureModel() {

    }

    public String getGroup_key() {
        return group_key;
    }

    public void setGroup_key(String group_key) {
        this.group_key = group_key;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDatafile() {
        return datafile;
    }

    public void setDatafile(String datafile) {
        this.datafile = datafile;
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
}
