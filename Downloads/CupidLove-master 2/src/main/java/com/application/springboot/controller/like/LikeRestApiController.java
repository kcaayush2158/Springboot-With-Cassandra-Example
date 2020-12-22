package com.application.springboot.controller.like;

import com.application.springboot.model.User;
import com.application.springboot.model.like.Like;
import com.application.springboot.service.LikeService.LikeService;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/")
public class LikeRestApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;


    @PostMapping("/likes/save")
    public Like saveLikes(@RequestBody Like like, Principal principal){
        User likedByUser = userService.findExistingEmail(principal.getName());
        like.setLikedBy(likedByUser);
        like.setLikedTo(like.getLikedTo());
        like.setStatus(true);
        return  likeService.saveLiked(like);
    }

    @GetMapping("/likes/all")
    public List<Like> getAllLikedUsers(Principal principal){
        User loggedInUsers = userService.findExistingEmail(principal.getName());
        return  likeService.getLikedUsers(loggedInUsers);
    }


    @GetMapping("/likes/users/count")
    public Like countTotalVisitedUsers(Principal principal){
        User user = userService.findExistingEmail(principal.getName());
        return likeService.countTotalLikes(user);
    }





}
