package com.example.proyecto.models;

public class Datos {
    public String username;
    public String password;
    public String id;
    public String money;
    public String email;


    public Datos(String username, String password) {
        this.username = username;
        this.password = password;
        this.id=id;
        this.money= money;
        this.email= email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
