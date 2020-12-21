package com.application.chat.controller;

import com.application.chat.model.Conversation;
import com.application.chat.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConversationApiController {

@Autowired
private ConversationService conversationService;

//    @GetMapping("/chatroom/public/{chatRoomId}/get")
//    public List<Conversation> getAllWithChatroomId(@PathVariable("chatRoomId") String chatRoomId){
//        return conversationService.getAllChatsByUsers(chatRoomId);
//    }

//    @PostMapping("/chatroom/public/{chatRoomId}/save")
//    public String  savePublicChatroomMessages(@RequestParam("message") final String message){
//
//        return "";
//    }
}
