package com.application.springboot.controller.home;


import com.application.springboot.model.User;
import com.application.springboot.model.notification.Notification;
import com.application.springboot.service.NotificationService;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeAPI {
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SessionRegistry sessionRegistry;


    @GetMapping("/count/users/online")
    public @ResponseBody int getOnlineUsers(){
        return sessionRegistry.getAllPrincipals().size();
    }

    @GetMapping("/user/status/get")
    public User getUserStatus(boolean status ,Principal principal){
        return userService.getUserStatus(true,principal.getName());
    }

    @GetMapping("/user/status/{id}/update")
    public User updateUserStatus(@RequestParam("active") boolean active ,Principal principal){
        return userService.getUserStatus(active,principal.getName());
    }
    @GetMapping("/count/notification")
    public int countNotification(Principal principal) {
        return notificationService.countNewMessages(false,principal.getName());
    }


    @PostMapping("/users/notification/read")
    public Notification readNotificationWhenClicked(Principal principal){
        List<Notification> existedNotification = notificationService.getNotification(principal.getName());
        for (Notification ns :existedNotification){
            System.out.print(principal.getName());
            User userId = userService.findByUsername(principal.getName());
            return ns;
        }
        return (Notification) existedNotification;
    }

    @GetMapping("/users/notifications/get")
    public List<Notification> getAllNotifications(Principal principal,Notification notification){

        return notificationService.getNotification(principal.getName());
    }






}
