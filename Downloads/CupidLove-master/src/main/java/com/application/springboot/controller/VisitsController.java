package com.application.springboot.controller;

import com.application.springboot.model.User;
import com.application.springboot.model.Visits;
import com.application.springboot.service.UserService;
import com.application.springboot.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VisitsController {
    @Autowired
    private VisitService visitService;
    @Autowired
    private UserService userService;

    @GetMapping("/visits")
    public ResponseEntity<List<Visits>> getVisits(@RequestParam(value = "email" ,required = false)String email, Principal principal){
        User loggedInUsers ;
        if(email !=null){
            loggedInUsers = userService.findExistingEmail(email);
        }else {
           loggedInUsers = userService.findExistingEmail(principal.getName());
        }
        return  ResponseEntity.ok().body(visitService.getUsers(loggedInUsers));
    }


    @GetMapping("/visits/users/count")
    public ResponseEntity<Integer> countTotalVisitedUsers(@RequestParam(value = "email" ,required = false)String email,Principal principal){
        User user ;
        if(email !=null){
            user = userService.findExistingEmail(email);
        }else {
            user = userService.findExistingEmail(principal.getName());
        }
        return ResponseEntity.ok().body(visitService.countTotalVisitedUsers(user));
    }





}
