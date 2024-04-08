package com.abhishek.store.controllers;

//step 1 - create Model
//step 2 - create Repo
//step 3 - create Controller (i.e. this file)

import com.abhishek.store.models.Product;
import com.abhishek.store.models.ProductDto;
import com.abhishek.store.services.ProductsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller                     //handles HTTP requests
@RequestMapping("/products")    // Maps specific URLs (request paths) to handler methods within the controller class.
public class ProductsController {


    @Autowired      // injects a dependency (object) managed by Spring into your class.
    private ProductsRepository repo;

    @GetMapping()
    public String showProductList(Model model){
        List<Product> products = repo.findAll();
        model.addAttribute("products",products);
        return "products/index";
    }

    //Step 5 - to create products
    @GetMapping("/create")
    public String showCreatePage(Model m)
    {
        ProductDto productDto = new ProductDto();
        m.addAttribute("productDto", productDto);
        return "products/CreateProduct";
    }

    //step 6
    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result)
    {
        if(productDto.getImagefile().isEmpty()){
            result.addError(new FieldError("productDto", "imagefile", "Please upload an image"));
        }

        if (result.hasErrors()){
            return "products/CreateProduct";
        }

        //save image file
        MultipartFile image = productDto.getImagefile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try(InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }

        }catch (Exception ex)
        {
            System.out.println("Exception "+ex.getMessage());
        }

        //Step 7 - create object of product using productDto inorder to save it to the Db
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreatedAt(createdAt);
        product.setImageFileName(storageFileName);

        repo.save(product);  //to save obj into repo or db

        return "redirect:/products";
    }

    //Step 8 - to edit the product
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id){

        try {
            Product product = repo.findById(id).get();
            model.addAttribute("product", product);

            //now we use obj of productDto... earlier we used obj of product...
            ProductDto productDto  = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            model.addAttribute("productDto",productDto);



        }
        catch (Exception ex)
        {
            System.out.println("Exceptions " + ex.getMessage());
            return "redirect:/products";
        }
        return "products/EditProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(
            Model model, @RequestParam int id,
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result
    )
    {
        try {
            Product product = repo.findById(id).get();
            model.addAttribute("product",product);

            if(result.hasErrors()){
                return "products/EditProduct";
            }

            if(!productDto.getImagefile().isEmpty()){
                //delete old img
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir+product.getImageFileName());

                try{
                    Files.delete(oldImagePath);
                }
                catch (Exception e){
                    System.out.println("Exception "+e.getMessage());
                }

                //save new img  file
                MultipartFile image = productDto.getImagefile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime()+"_"+image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(uploadDir+storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImageFileName(storageFileName);
            }

            product.setName(productDto.getName());
            product.setBrand(productDto.getBrand());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());

            repo.save(product);
        }
        catch(Exception e){
            System.out.println("Exceptions "+e.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(
            @RequestParam int id
    ){
        try {
            Product product = repo.findById(id).get();

            //delete product img
            Path imagePath = Paths.get("public/images/"+product.getImageFileName());
            try {
                Files.delete(imagePath);
            }
            catch (Exception e){
                System.out.println("Exception is "+e.getMessage());
            }

            //del the prod
            repo.delete(product);
        }
        catch (Exception e){
            System.out.println("Exception is "+e.getMessage());
        }


        return "redirect:/products";
    }




}
