package com.application.springboot.controller;

import com.application.springboot.service.LikeService.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LikeController {



    @GetMapping("/likes")
    public  String likePage(Model model) {
        return "likes";

    }
}
