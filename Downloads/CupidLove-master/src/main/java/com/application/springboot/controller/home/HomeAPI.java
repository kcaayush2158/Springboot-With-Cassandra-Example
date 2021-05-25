package com.application.springboot.controller.home;


import com.application.springboot.model.User;
import com.application.springboot.model.notification.Notification;
import com.application.springboot.service.NotificationService;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/count/users/online")
    public @ResponseBody ResponseEntity<Integer> getOnlineUsers() {
        return new ResponseEntity<>(sessionRegistry.getAllPrincipals().size(),HttpStatus.OK );
    }

    @GetMapping("/user/status/get")
    public ResponseEntity<User> getUserStatus(boolean status ,Principal principal){
        User user = userService.findExistingEmail(principal.getName());
        return new ResponseEntity<>(userService.getUserStatus(true,user.getEmail()),HttpStatus.OK);
    }

    @GetMapping("/user/status/{id}/update")
    public ResponseEntity<User >updateUserStatus(@RequestParam("active") boolean active ,Principal principal){
        return new ResponseEntity<>(userService.getUserStatus(active,principal.getName()),HttpStatus.CREATED);
    }


    @GetMapping(value="/count/notification")
    public  @ResponseBody ResponseEntity<Integer> countNotification(@RequestParam("email") String email , Principal principal) {
        User user ;
        if(email !=null){
            user = userService.findExistingEmail(email);
        }else {
            user= userService.findExistingEmail(principal.getName());
        }
        int notification = notificationService.countNewMessages(false,user.getEmail());
        System.out.println(notification);
        if(notification > 0){
            return new ResponseEntity<>(notification,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(0,HttpStatus.OK);
        }
     
    }


//    @PostMapping("/users/notification/read")
//    public ResponseEntity<Notification> readNotificationWhenClicked(@RequestParam(value = "email",required = false)String email, Principal principal){
//        List<Notification> existedNotification = notificationService.getNotification(principal.getName());
//        for (Notification ns : existedNotification){
//            User user ;
//            if(email ==null){
//                user = userService.findByUsername(principal.getName());
//            }else{
//                user = userService.findByUsername(email);
//            }
//             notificationService.readNotification(user.getEmail());
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    @GetMapping("/users/notifications/get")
    public  @ResponseBody ResponseEntity<List<Notification>> getAllNotifications(Principal principal,Notification notification){
        return ResponseEntity.ok(notificationService.getNotification(principal.getName()));
    }


    @RequestMapping("/user")
    public ResponseEntity<Principal> user(Principal user) {
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
//    @GetMapping("/u/online")
//
//    public ResponseEntity<User> findMessagesForUser(@AuthenticationPrincipal User user) {
//        List<Object> allPrincipal = sessionRegistry.getAllPrincipals();
//        System.out.println(sessionRegistry.getAllPrincipals().size());
//        for(Object l : allPrincipal){
//            String dataObject = String.valueOf(l);
//            User users = userService.findExistingEmail(user.getEmail());
//            System.out.println("user not found");
//            return new ResponseEntity<>(users,HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("/u/online")
    public List<Object> findMessagesForUser(@AuthenticationPrincipal User user) {
        List<Object> allPrincipal = sessionRegistry.getAllPrincipals();
            System.out.println(allPrincipal.get(0));
        for(Object l : allPrincipal){
            System.out.println(l.toString());

            String dataObject = String.valueOf(l);
            return allPrincipal;
        }

        System.out.println("online user"+allPrincipal.get(3));
        return allPrincipal;
    }


    @GetMapping("/u/{email}/online/get")
    public  ResponseEntity<User> getAllOnlineUsers(@PathVariable(value = "email" ,required = false) String email){
        User onlineUsers =  userService.findExistingEmail(email);
        return new ResponseEntity<>(onlineUsers,HttpStatus.OK);
    }

    @GetMapping(value = "/user/login" )
    public ResponseEntity<User> loginUsers(@RequestParam("email") String email,@RequestParam("password") String password) throws Exception {

        User userPrincipal = userService.findExistingEmail(email);
        System.out.print(email +" "+ userPrincipal.getPassword());
        if(userPrincipal.getEmail().equals(email)){
            if(bCryptPasswordEncoder.matches(password,userPrincipal.getPassword())){
                System.out.println("password  matched");
                return new ResponseEntity<User>(userPrincipal, HttpStatus.OK);
            }else{
                System.out.println("password dont match");
                throw new  Exception("Bad Credential");
            }
        }else{
            return new   ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

    }



    }
