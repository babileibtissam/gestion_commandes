package com.hafssa.gestion_commandes.web;

import com.hafssa.gestion_commandes.model.Product;
import com.hafssa.gestion_commandes.model.User;
import com.hafssa.gestion_commandes.repository.ProductRepository;
import com.hafssa.gestion_commandes.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
@Controller
public class UserController {

    @Autowired
    private ProductRepository produitRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

 

    @GetMapping("/homeAdmin")
    public String homeAdmin() {
        return "homeAdmin";
    }

    @GetMapping("/user/products")
    public String userProducts(Model model) {
        List<Product> produits = produitRepository.findAll();
        model.addAttribute("products", produits);
        return "userProducts";  // Crée cette page HTML dans /templates
    }
    
    @PostMapping("/user/updateProfile")
    public String updateProfile(@RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam(required = false) String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email); // ⚠️ Cela modifie l'identifiant de connexion

        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userService.save(user); // 💾 Méthode à créer pour mise à jour directe

        // 🔐 Met à jour l'identité de l'utilisateur dans Spring Security si l'email change
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), auth.getAuthorities())
        );

        return "redirect:/homeUser";
    }


    @GetMapping("/homeUser")
    public String homeUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);  // méthode à créer dans userService
        model.addAttribute("currentUser", user);
        return "homeUser";
    }



    @GetMapping("/user/help")
    public String helpPage() {
        return "Help"; // Thymeleaf va chercher templates/user/help.html
    }
    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }


}
