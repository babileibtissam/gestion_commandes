package com.hafssa.gestion_commandes.web;

import com.hafssa.gestion_commandes.model.Panier;
import com.hafssa.gestion_commandes.model.Product;
import com.hafssa.gestion_commandes.model.Order;
import com.hafssa.gestion_commandes.repository.OrderRepository;
import com.hafssa.gestion_commandes.repository.PanierRepository;
import com.hafssa.gestion_commandes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository produitRepository;

    @Autowired
    private PanierRepository panierRepository;
    
    @Autowired
    private OrderRepository orderRepository;


    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, Model model) {
        Product produit = produitRepository.findById(productId).orElse(null);
        if (produit == null) {
            model.addAttribute("error", "Produit introuvable.");
            return "redirect:/user/products";
        }

        if (produit.getStock() <= 0) {
            model.addAttribute("error", "Produit en rupture de stock.");
            return "redirect:/user/products";
        }

        // Vérifie si le produit est déjà dans le panier
        Optional<Panier> existingItem = panierRepository.findByProduit(produit);
        if (existingItem.isPresent()) {
            Panier item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            panierRepository.save(item);
        } else {
            Panier item = new Panier();
            item.setProduit(produit);
            item.setQuantity(1);
            panierRepository.save(item);
        }

        // Diminuer la quantité en stock du produit
        produit.setStock(produit.getStock() - 1);
        produitRepository.save(produit);

        model.addAttribute("message", "Produit ajouté avec succès au panier.");
        return "redirect:/user/products";
    }
 // Augmenter la quantité d'un produit dans le panier
    @PostMapping("/increase")
    public String increaseQuantity(@RequestParam Long panierId) {
        Optional<Panier> panierItemOpt = panierRepository.findById(panierId);
        if (panierItemOpt.isPresent()) {
            Panier panierItem = panierItemOpt.get();
            Product produit = panierItem.getProduit();
            if (produit.getStock() > 0) {  // vérifie stock dispo
                panierItem.setQuantity(panierItem.getQuantity() + 1);
                panierRepository.save(panierItem);

                // Réduit stock produit
                produit.setStock(produit.getStock() - 1);
                produitRepository.save(produit);
            }
        }
        return "redirect:/cart/view";
    }

    // Diminuer la quantité d'un produit dans le panier
    @PostMapping("/decrease")
    public String decreaseQuantity(@RequestParam Long panierId) {
        Optional<Panier> panierItemOpt = panierRepository.findById(panierId);
        if (panierItemOpt.isPresent()) {
            Panier panierItem = panierItemOpt.get();
            Product produit = panierItem.getProduit();

            if (panierItem.getQuantity() > 1) {
                panierItem.setQuantity(panierItem.getQuantity() - 1);
                panierRepository.save(panierItem);

                // Restaure stock produit
                produit.setStock(produit.getStock() + 1);
                produitRepository.save(produit);
            } else {
                // Si quantité 1 et on veut diminuer, supprime l'article
                panierRepository.delete(panierItem);
                produit.setStock(produit.getStock() + 1);
                produitRepository.save(produit);
            }
        }
        return "redirect:/cart/view";
    }

    // Supprimer un produit du panier
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long panierId) {
        Optional<Panier> panierItemOpt = panierRepository.findById(panierId);
        if (panierItemOpt.isPresent()) {
            Panier panierItem = panierItemOpt.get();
            Product produit = panierItem.getProduit();

            // Restaure stock produit en fonction de la quantité supprimée
            produit.setStock(produit.getStock() + panierItem.getQuantity());
            produitRepository.save(produit);

            panierRepository.delete(panierItem);
        }
        return "redirect:/cart/view";
    }
    @PostMapping("/confirm")       // ✅ Corrigé
    public String confirmerCommande(
            @RequestParam String adresseLivraison,
            @RequestParam String modeLivraison,
            @RequestParam String moyenPaiement,
            Model model) {

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
            order.setAdresseLivraison(adresseLivraison);  // Vérifie que ce paramètre est bien reçu
            order.setModeLivraison(modeLivraison);
            order.setMoyenPaiement(moyenPaiement);
            order.setStatut("en_attente");  // <-- AJOUTÉ pour éviter l'erreur

            orderRepository.save(order);
        }

        panierRepository.deleteAll();

        return "redirect:/user/orders";
    }





    @GetMapping("/view")
    public String viewCart(Model model) {
        List<Panier> items = panierRepository.findAll();
        model.addAttribute("cartItems", items);

        // Calcul du total général
        double total = items.stream()
            .mapToDouble(item -> item.getProduit().getPrice() * item.getQuantity())
            .sum();
        model.addAttribute("total", total);

        return "carteView";
    }

}
