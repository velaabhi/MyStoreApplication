package com.abhishek.store.models;

import jakarta.persistence.*;

import java.util.Date;

//step 1 - create Model (i.e. this file)
//step 2 - create Repo - it will allow us to add/del entries from db- in services package

@Entity                     //Marks a class as an entity that can be persisted to a database table.
@Table(name="products")     //to create table "products"
public class Product {

    @Id                 //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //to increment automatically
    private int id;

    private String name;
    private String brand;
    private String category;
    private double price;

    @Column(columnDefinition = "TEXT")      //create a Col of type Text
    private String description ;
    private Date createdAt;     //Use date from Util cz its in epoch, while Date from
                                //sql is in YYYY-MM-DD and it excludes time info
    private String imageFileName;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
