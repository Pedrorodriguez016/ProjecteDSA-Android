package com.example.proyecto.models;

import com.google.gson.annotations.SerializedName;

public class ThreadMessageWrapper {
    @SerializedName("thread")
    private Thread thread;

    @SerializedName("message")
    private ForumMessage message;

    public ThreadMessageWrapper(Thread thread, ForumMessage message) {
        this.thread = thread;
        this.message = message;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public ForumMessage getMessage() {
        return message;
    }

    public void setMessage(ForumMessage message) {
        this.message = message;
    }
}