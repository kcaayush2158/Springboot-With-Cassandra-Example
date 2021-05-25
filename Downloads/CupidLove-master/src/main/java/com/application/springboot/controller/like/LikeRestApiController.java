package com.application.springboot.controller.like;

import com.application.springboot.model.Likes;
import com.application.springboot.model.User;
import com.application.springboot.model.notification.Notification;
import com.application.springboot.service.LikeService.LikesService;
import com.application.springboot.service.NotificationService;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class LikeRestApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private LikesService likesService;
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/likes/save")
    public Likes saveLikes(@RequestParam("id") int id, Principal principal) {

        User likedByUser = userService.findExistingEmail(principal.getName());
        User likedToUser = userService.findUserById(id);

      Likes existingUser = likesService.findExistingUser(likedToUser, likedByUser);

        if(existingUser ==null){

            //sends the user notification to the users
            Notification notification = new Notification();
            notification.setMessage(likedByUser.getUsername() + ' ' + "has liked your profile");
            notification.setDatetime_added(new Date());
            notification.setUserSender(principal.getName());
            notification.setStatus(false);
            notification.setUserReceiver(likedToUser.getEmail());
            notificationService.saveUserVisit(notification);
            notification.setUser(likedByUser);

            Likes like = new Likes();
            like.setLikedBy(likedByUser);
            like.setLikedTo(likedToUser);
            like.setStatus(true);

            return likesService.saveLikes(like);
        }else{
            return null;
        }

    }

    @GetMapping("/likes/all")
    public ResponseEntity<List<Likes>> getAllLikedUsers(@RequestParam(value = "email", required = false)String email, Principal principal) {
        User loggedInUsers;
        if(email !=null){
            loggedInUsers = userService.findExistingEmail(email);
        }else{
          loggedInUsers = userService.findExistingEmail(principal.getName());
        }

        return ResponseEntity.ok().body(likesService.getLikedUsers(loggedInUsers));
    }

    @GetMapping("/likes/u/all")
    public ResponseEntity<List<Likes>> getAllLikedByPrincipal(@RequestParam(value = "email" , required = false)String email, Principal principal) {
        User loggedInUsers ;
        if(email!=null){
           loggedInUsers = userService.findExistingEmail(email);
        }else{
            loggedInUsers = userService.findExistingEmail(principal.getName());
        }
        return ResponseEntity.ok().body(likesService.getYouLiked(loggedInUsers));
    }

    @GetMapping("/likes/users/count")
    public ResponseEntity<Integer> countTotalVisitedUsers(@RequestParam(value = "email" , required = false)String email,Principal principal) {
        User user;
        if(email!=null){
            user = userService.findExistingEmail(email);
        }else{
            user = userService.findExistingEmail(principal.getName());
        }

        return ResponseEntity.ok().body(likesService.countTotalLikes(user));
    }

    @GetMapping("/likes/you-liked/count")
    public ResponseEntity<Integer> countTotalUserLikesBy(@RequestParam(value = "email" , required = false)String email,Principal principal) {
        User user;
        if(email!=null){
            user = userService.findExistingEmail(email);
        }else{
            user = userService.findExistingEmail(principal.getName());
        }
        return  new ResponseEntity<Integer>( likesService.countTotalUserLikesBy(user),HttpStatus.OK);
    }

    @PostMapping("/likes/{id}/update")
    public ResponseEntity<Likes> updatesLikeStatus(@RequestParam(value = "email" , required = false)String email,@RequestBody Likes likes, @PathVariable("id") int id, Principal principal) {
        User user;
        if(email!=null){
            user = userService.findExistingEmail(email);
        }else{
            user = userService.findExistingEmail(principal.getName());
        }
        likes.setLikedBy(user);
        likes.setLikedTo(likes.getLikedTo());
        if (likes.isStatus()) {
            likes.setStatus(false);

            return ResponseEntity.ok().body(likesService.updateLikes(likes.getId(), false));
        } else {
            likes.setStatus(true);
        }
        return ResponseEntity.ok().body(likesService.updateLikes(likes.getId(), true));
    }


    @PostMapping("/likes/{id}/delete")
    public ResponseEntity<Integer> deleteLike(@RequestBody @PathVariable("id") int id) {
        return ResponseEntity.ok().body(likesService.deleteLikes(id));
    }


}
