package com.application.springboot.controller;

import com.application.springboot.model.User;
import com.application.springboot.model.notification.Notification;
import com.application.springboot.service.NotificationService;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    @GetMapping("/notification/all")
    public ResponseEntity<List<Notification>> notifications( Model model,@RequestParam("email") String email, Principal principal){
        User user;
        if(email !=null){
            user=  userService.findExistingEmail(email);
        }else{
           user=  userService.findExistingEmail(principal.getName());
        }

        List<Notification> notification = notificationService.getNotification(user.getEmail());
        return new ResponseEntity<>(notification,HttpStatus.OK);

    }

    @PostMapping("/notification/delete/{id}")
    public ResponseEntity<Integer> deleteNotification(@PathVariable("id") int id){
        return new ResponseEntity<>(notificationService.deleteNotification(id), HttpStatus.OK);
    }

     @PostMapping("/notification/read/{id}")
    public ResponseEntity<Void> readNotification(@RequestBody @RequestParam String email ,@PathVariable("id")int id){
       User user1 = userService.findUserById(id);
        notificationService.readNotification(id);
       return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
