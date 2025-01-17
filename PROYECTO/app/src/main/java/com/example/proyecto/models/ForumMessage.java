package com.example.proyecto.models;

import com.google.gson.annotations.SerializedName;

public class ForumMessage {
    @SerializedName("id")
    private Integer id;

    @SerializedName("sender")
    private Integer sender;

    @SerializedName("thread")
    private Integer thread;

    @SerializedName("message")
    private String message;

    @SerializedName("date")
    private String date;

    @SerializedName("parent_message")
    private Integer parent_message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getThread() {
        return thread;
    }

    public void setThread(Integer thread) {
        this.thread = thread;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getParent_message() {
        return parent_message;
    }

    public void setParent_message(Integer parent_message) {
        this.parent_message = parent_message;
    }
}