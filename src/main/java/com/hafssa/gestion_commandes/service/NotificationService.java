package com.hafssa.gestion_commandes.service;

import com.hafssa.gestion_commandes.model.Notification;
import com.hafssa.gestion_commandes.model.User;
import com.hafssa.gestion_commandes.model.Order;
import com.hafssa.gestion_commandes.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findByIsReadFalse();
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }

    public void markAllAsRead() {
        List<Notification> unreadNotifications = getUnreadNotifications();
        for (Notification n : unreadNotifications) {
            n.setRead(true);
        }
        notificationRepository.saveAll(unreadNotifications);
    }

    public void createNotification(String message, User user, Order commande) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(LocalDateTime.now());
        notification.setRead(false);
        notification.setUser(user);
        notification.setCommande(commande);
        notificationRepository.save(notification);
    }
}
