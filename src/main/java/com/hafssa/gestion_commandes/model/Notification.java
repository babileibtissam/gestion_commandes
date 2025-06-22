package com.hafssa.gestion_commandes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime date;
    
    @Column(name = "is_read")
    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Order commande;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Notification() {
        // Constructeur par défaut requis par JPA
    }

    public Notification(String message, LocalDateTime date, boolean isRead,Order commande,User user) {
        this.message = message;
        this.date = date;
        this.isRead = isRead;
        this.commande = commande;
        this.user = user;
    }

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
    public Order getCommande() {
        return commande;
    }

    public void setCommande(Order commande) {
        this.commande = commande;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Méthode utilitaire pour marquer comme lu
    public void markAsRead() {
        this.isRead = true;
    }
    
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", isRead=" + isRead +
                ", commande=" + (commande != null ? commande.getId() : null) +
                ", user=" + (user != null ? user.getId() : null) +
                '}';
    }
}

