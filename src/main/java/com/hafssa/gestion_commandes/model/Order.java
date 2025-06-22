package com.hafssa.gestion_commandes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "adresse_livraison")
    private String adresseLivraison;

    @Column(name = "date_commande")
    private LocalDateTime dateCommande;

    @Column(name = "mode_livraison")
    private String modeLivraison;

    @Column(name = "moyen_paiement")
    private String moyenPaiement;

    private int quantity;

    private String statut; // EN_ATTENTE, EN_COURS, LIVREE, ANNULEE

    @Column(name = "total_amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Product produit;

    // (optionnel) Lien vers utilisateur si tu veux l’utiliser plus tard
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAdresseLivraison() { return adresseLivraison; }
    public void setAdresseLivraison(String adresseLivraison) { this.adresseLivraison = adresseLivraison; }

    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }

    public String getModeLivraison() { return modeLivraison; }
    public void setModeLivraison(String modeLivraison) { this.modeLivraison = modeLivraison; }

    public String getMoyenPaiement() { return moyenPaiement; }
    public void setMoyenPaiement(String moyenPaiement) { this.moyenPaiement = moyenPaiement; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Product getProduit() { return produit; }
    public void setProduit(Product produit) { this.produit = produit; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
