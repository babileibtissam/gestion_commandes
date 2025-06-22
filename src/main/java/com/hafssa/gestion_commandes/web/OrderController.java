package com.hafssa.gestion_commandes.web;

import com.hafssa.gestion_commandes.model.Order;
import com.hafssa.gestion_commandes.model.Panier;
import com.hafssa.gestion_commandes.repository.OrderRepository;
import com.hafssa.gestion_commandes.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PanierRepository panierRepository;

    // ===================== [ ADMIN - Voir et modifier les commandes ] =====================
    
    @GetMapping("/admin/orders")
    public String listOrdersForAdmin(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin/orders"; // ⇦ fichier: templates/admin/orders.html
    }

    @PostMapping("/admin/orders/updateStatus/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));
        order.setStatut(status);
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }

    // ===================== [ USER - Valider panier => créer commandes ] =====================

    @PostMapping("/validate")
    public String validerCommande(Model model) {
        List<Panier> panierItems = panierRepository.findAll();

        if (panierItems.isEmpty()) {
            model.addAttribute("message", "Votre panier est vide !");
            return "redirect:/cart/view";
        }

        for (Panier item : panierItems) {
            Order order = new Order();
            order.setProduit(item.getProduit());
            order.setQuantity(item.getQuantity());
            order.setDateCommande(LocalDateTime.now());
            order.setStatut("EN_ATTENTE");
            orderRepository.save(order);
        }

        panierRepository.deleteAll();
        model.addAttribute("message", "Commande validée avec succès !");
        return "redirect:/user/orders";
    }

    // ===================== [ USER - Voir mes commandes ] =====================

    @GetMapping("/user/orders")
    public String afficherCommandesUtilisateur(Model model) {
        List<Order> commandes = orderRepository.findAll();
        model.addAttribute("orders", commandes);
        return "orders"; // ⇦ fichier: templates/user/orders.html
    }
}
