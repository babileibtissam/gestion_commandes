package com.hafssa.gestion_commandes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hafssa.gestion_commandes.model.Product;
import com.hafssa.gestion_commandes.repository.ProductRepository;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Afficher la page des produits + formulaire vide ou prérempli
    @GetMapping
    public String showProductsPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("isEdit", false);
        return "admin/products";
    }

    // Ajouter un nouveau produit
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // Afficher le formulaire de modification avec les données du produit
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("isEdit", true);
        return "admin/products";
    }

    // Mettre à jour un produit existant
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product updatedProduct) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        productRepository.save(product);

        return "redirect:/admin/products";
    }

    // Supprimer un produit
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }
}
