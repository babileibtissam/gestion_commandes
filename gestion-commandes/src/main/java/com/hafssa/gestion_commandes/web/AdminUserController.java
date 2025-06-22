package com.hafssa.gestion_commandes.web;

import com.hafssa.gestion_commandes.model.User;
import com.hafssa.gestion_commandes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

	private final UserRepository userRepository;

    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/toggleStatus/{id}")
    public String toggleUserStatus(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé : " + id));

        user.setActive(!user.isActive());

        userRepository.save(user);
        return "redirect:/admin/users";
    }
}