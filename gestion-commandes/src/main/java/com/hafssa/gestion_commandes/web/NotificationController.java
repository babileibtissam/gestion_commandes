package com.hafssa.gestion_commandes.web;


import com.hafssa.gestion_commandes.model.Notification;
import com.hafssa.gestion_commandes.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Afficher la page des notifications
    @GetMapping
    public String showNotifications(Model model) {
        List<Notification> allNotifications = notificationService.getAllNotifications();
        model.addAttribute("notifications", allNotifications);
        return "admin/notifications"; // View: templates/admin/notifications.html
    }

    // Marquer une notification comme lue
    @PostMapping("/read/{id}")
    public String markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return "redirect:/admin/notifications";
    }

    // Marquer toutes les notifications comme lues
    @PostMapping("/readAll")
    public String markAllAsRead() {
        notificationService.markAllAsRead();
        return "redirect:/admin/notifications";
    }

    // API pour obtenir le nombre de notifications non lues (optionnel si tu veux le badge dynamique)
    @GetMapping("/unread/count")
    @ResponseBody
    public int getUnreadCount() {
        return notificationService.getUnreadNotifications().size();
    }
}

