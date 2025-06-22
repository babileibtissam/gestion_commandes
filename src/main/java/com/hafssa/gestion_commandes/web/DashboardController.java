package com.hafssa.gestion_commandes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hafssa.gestion_commandes.repository.ProductRepository;

@Controller
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/dashboard";  // Affiche le fichier dashboard.html dans /templates/admin/
    }
}


