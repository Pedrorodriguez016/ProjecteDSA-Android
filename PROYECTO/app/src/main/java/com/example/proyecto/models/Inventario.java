package com.example.proyecto.models;


import java.util.ArrayList;
import java.util.List;


public class Inventario {

    public Integer id;
    public Integer user;
    public Integer item;
    public Integer quantity;

    public Inventario()
    {
        setQuantity(1);
    }

    public Inventario(Integer user, Integer item)
    {
        this();
        this.user = user;
        this.item = item;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user)
    {
        this.user = user;
    }


    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item)
    {
        this.item = item;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }
}
