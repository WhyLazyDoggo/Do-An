package com.example.myapp1.checkSignature;

public class checkNotSignatureModel {
    String id;
    String name;
    String person;
    String count;

    public checkNotSignatureModel(String id, String name, String person, String count) {
        this.id = id;
        this.name = name;
        this.person = person;
        this.count = count;
    }

    public checkNotSignatureModel() {
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
