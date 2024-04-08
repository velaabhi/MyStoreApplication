package com.abhishek.store.services;

import com.abhishek.store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

//step 1 - create Model
//step 2 - create Repo (i.e. this file)
//step 3 - create Controller - allows us to perform CRUD ops on products

public interface ProductsRepository extends JpaRepository<Product, Integer> {
}
