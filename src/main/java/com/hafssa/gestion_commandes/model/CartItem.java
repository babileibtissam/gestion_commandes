package com.hafssa.gestion_commandes.model;

import com.hafssa.gestion_commandes.model.*;

public class CartItem {
    private Product produit;
    private int quantity;

    public CartItem(Product produit, int quantity) {
        this.produit = produit;
        this.quantity = quantity;
    }

    public Product getProduit() {
        return produit;
    }

    public void setProduit(Product produit) {
        this.produit = produit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
