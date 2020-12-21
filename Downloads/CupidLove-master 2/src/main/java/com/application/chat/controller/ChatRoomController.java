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
@CrossOrigin(origins = "http://localhost:4200")
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
//    @GetMapping("/chatroom/search/{name}")
//    public @ResponseBody String searchChatRoom(@PathVariable("name") String name) {
//        List<ChatRoom> chatRooms = chatRoomService.searchChatRooms(name);
//        String result = "";
//        String s;
//        for (ChatRoom room : chatRooms) {
//            result +=
//
//                    "<div class='col-md-3'><div class=\"card mb-2 mr-3  ml-2 mt-5  \" id=\"custom-card-design\">" +
//                            "<div class=\"card-body\"><h5 class=\"card-title text-center text-uppercase mb-2\">" + room.getChatRoomName() + "</h5>" +
//                            "<p class=\"card-text mt-4 mb-5\" id=\"chatDescription\">" + room.getChatRoomDescription() + "</p><br >";
//                          if (room.getType().equals("PUBLIC")) {
//                s = "<button class='btn btn-block ' id='btn-join-room-public'><i class='fa fa-plus-circle'></i> Join Room</button> </div></div></div>";
//                result += s;
//
//            }
//            if (room.getType().equals("PRIVATE")) {
//                s = "<button class='btn  btn-block ' id='btn-join-room-private'><i class='fa fa-plus-circle'></i> Join Room</button> </div></div></div>";
//                result += s;
//            }
//        }
//            return result;
//
//    }

    @GetMapping("/chatroom/search/{name}")
    public List<ChatRoom>  searchChatRooms(@PathVariable("name") String name) {
        List<ChatRoom> chatRooms = chatRoomService.searchChatRooms(name);
        return chatRooms;

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
