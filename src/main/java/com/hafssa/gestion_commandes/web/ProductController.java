package com.hafssa.gestion_commandes.web;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hafssa.gestion_commandes.model.Product;
import com.hafssa.gestion_commandes.repository.ProductRepository;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Afficher la page des produits + formulaire vide
    @GetMapping
    public String showProductsPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("isEdit", false);
        return "admin/products";
    }

    // Ajouter un produit avec image
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            imageFile.transferTo(new File(uploadDir + fileName));
            product.setImage("/uploads/" + fileName);
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // Afficher le formulaire de modification
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("isEdit", true);
        return "admin/products";
    }

    // Mettre à jour un produit
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                @ModelAttribute Product updatedProduct,
                                @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());

        if (!imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }

            imageFile.transferTo(new File(uploadDir + fileName));
            product.setImage("/uploads/" + fileName);
        }

        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // Supprimer un produit
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }


}
