package com.application.springboot.controller.photos;

import com.amazonaws.services.apigateway.model.Model;
import com.application.springboot.model.Photos;
import com.application.springboot.model.User;
import com.application.springboot.service.UserService;
import com.application.springboot.service.photo.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping( "/api")
public class PhotoController {

    @Autowired
    public PhotoService photoService;
    @Autowired
    public UserService userService;

    @GetMapping("/photos/all")
    public List<Photos> listAllUsersPhotos(Principal principal){
        User user = userService.findExistingEmail(principal.getName());
         return photoService.showAllPhotos(user);
    }

    @PostMapping("/photos/{id}/delete")
    public int deletePhoto(@PathVariable("id")int id ,Model model ){
        User user = userService.findUserById(id);
        return  photoService.deletePhotos(user.getId());
    }

}
