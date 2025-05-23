package com.hafssa.gestion_commandes.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Product.java
@Entity
public class Product {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String name;
 private String description;
 private double price;
 private int stock;

 private String image; // chemin de l’image

 // Getters et Setters
 public Long getId() { return id; }

 public String getName() { return name; }

 public void setName(String name) { this.name = name; }

 public String getDescription() { return description; }

 public void setDescription(String description) { this.description = description; }

 public double getPrice() { return price; }

 public void setPrice(double price) { this.price = price; }

 public int getStock() { return stock; }

 public void setStock(int stock) { this.stock = stock; }

 public String getImage() { return image; }

 public void setImage(String image) { this.image = image; }
}
