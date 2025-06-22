package com.hafssa.gestion_commandes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "panier")
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Product produit;

    private Integer quantity;

    // Getters & Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Product getProduit() {
        return produit;
    }
    public void setProduit(Product produit) {
        this.produit = produit;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
