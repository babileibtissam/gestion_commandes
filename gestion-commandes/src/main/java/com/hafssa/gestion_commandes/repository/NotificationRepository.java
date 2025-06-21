package com.hafssa.gestion_commandes.repository;


import com.hafssa.gestion_commandes.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Obtenir toutes les notifications triées par date décroissante
    List<Notification> findAllByOrderByDateDesc();

    // Obtenir les notifications non lues
    List<Notification> findByIsReadFalse();
    
    List<Notification> findByUserId(Long userId);
}
