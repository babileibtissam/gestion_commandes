package com.hafssa.gestion_commandes.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/homeUser")
    public String homeUser() {
        return "homeUser";  // Affichera la page homeUser.html
    }

    @GetMapping("/homeAdmin")
    public String homeAdmin() {
        return "homeAdmin"; // Affichera la page homeAdmin.html
    }
}
