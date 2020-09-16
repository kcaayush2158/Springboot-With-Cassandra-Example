package com.application.springboot.controller.home;

import com.application.springboot.model.User;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/api/users/profile")
    public @ResponseBody  String getUserProfile(@RequestParam("limit")int limit, @RequestParam("start")int start, HttpSession session, Principal principal){
        List<User> users= userService.findAllUsersProfile(limit,start);

        String results ="";
        for (User user : users){
           if(session.getAttribute(user.getEmail()) !=null) {
               results += " <div class=\"row\" style=\"float: left;\">   <a href= "+"/user/profile/"+user.getUsername()+" style=\"text-decoration: none; color: #313437;\"> <div class=\"card bg-light mb-3 mr-5  ml-5 mt-2 \" style=\"max-width: 16rem;max-width: 16rem;box-shadow: 0 4px 8px 0 rgba(0,0,0,0.08);border-radius: 10px;overflow: hidden;text-decoration: none;\"> " +
                       "<img class=\"card-img-top image-thumbnail\" height=\"180\" width=\"215\"  src=\""+user.getProfilePhoto()+"\"  alt=\"Card image cap\">   <div class=\"card-body\" style=\"background-color: white\">" +
                       " <h5 class=\"card-title \" style='font-size:18px;font-weight: 700;color: #222;' > "+user.getFirstName()+ ' '+user.getLastName()+ ' '+ user.getAboutMe().getAge()+" </h5>  <p style=\" overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 3; /* number of lines to show */-webkit-box-orient: vertical;color:#222;\">"+user.getAboutMe().getBio()+"</p>" +
                       " </div>" +
                       "   </div>" +
                       " </a>" +
                       " </div> ";
            }else{
               results += " <div class=\"row\" style=\"float: left;\">   <a href= "+"/user/profile/"+user.getUsername()+" style=\"text-decoration: none; color: #313437;\"> <div class=\"card bg-light mb-3 mr-5  ml-5 mt-2 \" style=\"max-width: 16rem;max-width: 16rem;box-shadow: 0 4px 8px 0 rgba(0,0,0,0.08);border-radius: 10px;overflow: hidden;text-decoration: none;\"> " +
                       "<img class=\"card-img-top image-thumbnail\" height=\"180\" width=\"215\"  src=\""+user.getProfilePhoto()+"\"  alt=\"Card image cap\">   <div class=\"card-body\" style=\"background-color: white\">" +
                       " <h5 class=\"card-title \" style='font-size:18px;font-weight: 700;color: #222;' > "+user.getFirstName()+ ' '+user.getLastName()+ ' '+ user.getAboutMe().getAge()+" <img src=\"https://tse3.mm.bing.net/th?id=OIP.ESDrDIXdc0VWXriGnrrcowHaHa&pid=Api&P=0&w=300&h=300\" class=\"ml-2 \" height=\"10\"> </h5> <p style=\" overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 3; /* number of lines to show */-webkit-box-orient: vertical;color:#222;\">"+user.getAboutMe().getBio()+"</p>" +
                       " </div>" +
                       "   </div>" +
                       " </a>" +
                       " </div> ";
           }

        }
        return results;

    }

}
