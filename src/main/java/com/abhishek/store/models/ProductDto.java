package com.abhishek.store.models;

//step 1 - create Model
//step 2 - create Repo
//step 3 - create Controller (i.e. this file)
//step 4(this file) - to create and update the products list we'll a model class
// aka DTO. Our DTO wont contain the primary key and instead of file name
//we would require the entire file


/*
DTOs act as intermediaries, carrying the necessary data from the controller layer
(handling user requests) to the service layer (handling business logic) and vice versa. This keeps
 controllers focused on request handling and avoids exposing complex service layer objects directly
 */

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    @NotEmpty(message = "The name is required")
    private String name;

    @NotEmpty(message = "Brand name is required")
    private String brand;

    @NotEmpty(message = "category is required")
    private String category;

    @Min(0)
    private double price;

    @Size(min=10, message = "Description should be atleast 10 characters long")
    @Size(max=2000, message = "Description cannot exceed 2000 characters")
    private String description;

    private MultipartFile imagefile;

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

    public MultipartFile getImagefile() {
        return imagefile;
    }

    public void setImagefile(MultipartFile imagefile) {
        this.imagefile = imagefile;
    }
}
