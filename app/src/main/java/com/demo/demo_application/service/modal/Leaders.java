package com.demo.demo_application.service.modal;

import com.google.gson.annotations.SerializedName;

public class Leaders {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("designation")
    String designation;
    @SerializedName("image")
    String image;
    @SerializedName("description")
    String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
