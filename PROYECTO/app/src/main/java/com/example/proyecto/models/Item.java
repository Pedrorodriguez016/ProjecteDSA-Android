package com.example.proyecto.models;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    private Integer id;

    @SerializedName("type")
    private String type;

    @SerializedName("value")
    private Integer value;

    @SerializedName("description")
    private String description;


    public Item(Integer id, String type, Integer value, String description) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}