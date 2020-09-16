package com.application.chat.controller;

import com.application.chat.model.ChatRoom;
import com.application.chat.model.Conversation;
import com.application.chat.service.ChatRoomService;
import com.application.springboot.model.User;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/chatroom/create")
    public String createChatRoom(@ModelAttribute("chatRoom") ChatRoom chatRoom, @RequestParam("chatRoomName") String chatRoomName, @RequestParam("password") String password, @RequestParam("roomDescription") String roomDescription, @RequestParam("roomType") String roomType, Model model, Principal principal) throws UnknownHostException {

        User user = userService.findExistingEmail(principal.getName());
        ChatRoom existedChatRoom = chatRoomService.findByChatRoomName(chatRoomName);

        if (existedChatRoom != null) {
            if (existedChatRoom.getChatRoomName().equals(chatRoomName)) {
                System.out.print("UPDATING THE ROOM....");
                chatRoomService.updateChatRoom(chatRoomName, roomDescription, roomType, new Date());

            }
        }

        Conversation conversation = new Conversation();
        InetAddress ip = InetAddress.getLocalHost();

        chatRoom.setChatRoomName(chatRoomName);
        chatRoom.setType(roomType);
        chatRoom.setChatRoomId(UUID.randomUUID().toString());
        chatRoom.setCreatedBy(user);
        chatRoom.setPassword(bCryptPasswordEncoder.encode(password));
        chatRoom.setChatRoomDescription(roomDescription);
        chatRoom.setCreatedTime(new Date());

        chatRoomService.createChatRoom(chatRoom);

        return "redirect:/chatroom";

    }
    @GetMapping("/chatroom/search/{name}")
    public @ResponseBody String searchChatRoom(@PathVariable("name") String name) {
        List < ChatRoom > chatRooms = chatRoomService.searchChatRooms(name);
        String result = "";
        for (ChatRoom room: chatRooms) {
            result += "<div class=\"card bg-light mb-3 mr-5  ml-5 mt-2 \" id=\"custom-card-design\">" +
                    "<div class=\"card-body\"><h5 class=\"card-title\">" + room.getChatRoomName() + "</h5>" +
                    "<p class=\"card-text\">" + room.getChatRoomDescription() + "</p><br>" +
                    "<button class='btn btn-success'>Join Room</button>" +
                    " </div>" +
                    "</div>";
        }
        return result;
    }
    @GetMapping("/chatroom/{ChatroomId}")
    public @ResponseBody ChatRoom rooms(@PathVariable("ChatroomId") String ChatroomId, Model model) {
        return chatRoomService.findChatRoomByChatRoomId(ChatroomId);
    }

    @PostMapping("/chatroom/{id}/delete")
    public void chatRoom(@PathVariable("id") int id) {
        chatRoomService.deleteChatRoom(id);
    }



}
