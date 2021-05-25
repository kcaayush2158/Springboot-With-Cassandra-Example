package com.application.chat.controller;
import com.application.chat.model.ChatRoom;
import com.application.chat.model.ShoutOut;
import com.application.chat.service.ChatRoomService;
import com.application.chat.service.ShoutOutService;
import com.application.springboot.model.User;
import com.application.springboot.service.NotificationService;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.*;
@Controller
@CrossOrigin(origins = "*")
public class ChatRoomAPIController {

    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    public ChatRoomService chatRoomService;
    @Autowired
    public NotificationService notificationService;
    @Autowired
    public UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ShoutOutService shoutOutService;



    @GetMapping("/chatroom")
    public String chatRoom(Model model){
        model.addAttribute("activeuser",  sessionRegistry.getAllPrincipals().size());
        return "/chatroom/chatroom";
    }
    @GetMapping("/api/my/chatroom")
    public @ResponseBody ResponseEntity< List<ChatRoom>> myChatRooms(Model model, @RequestParam(value = "email" , required = false) String email,@AuthenticationPrincipal  Principal principal){
        User findUser;
        if(email !=null){
            findUser =userService.findExistingEmail(email);
            return  new ResponseEntity<>(chatRoomService.findChatRoomCreatedBy(findUser), HttpStatus.OK);
        }else {
            findUser = userService.findExistingEmail(principal.getName());
        }
        return  new ResponseEntity<>(chatRoomService.findChatRoomCreatedBy(findUser), HttpStatus.OK);

    }


    @GetMapping("/api/chatrooms/all")
    public  ResponseEntity<List<ChatRoom>> chatRooms() {
        return new ResponseEntity<>(chatRoomService.findAllChatRoom(),HttpStatus.OK) ;
    }

    //counts all the room in the principal chatroom
    @GetMapping(value = "/api/chatroom/my/count")
    @ResponseBody
    public  ResponseEntity<Integer> countsAllThePrincipalChatroom(@AuthenticationPrincipal Principal principal, @RequestParam(value = "email", required = false)  String email){
        User user ;
        if(email !=null){
            user= userService.findExistingEmail(email);
        }else{
            user = userService.findExistingEmail(principal.getName());
        }
        return  new ResponseEntity<>(chatRoomService.countAllChatroom(user), HttpStatus.OK);
    }

    @GetMapping("/chatroom/public/{chatRoomId}")
     public String chatRoom(@PathVariable("chatRoomId")String chatRoomId,  Model model, HttpServletRequest request, HttpSession httpSession){
        ChatRoom chatRoom = chatRoomService.findChatRoomByChatRoomId(chatRoomId);
        model.addAttribute("chatRoom",chatRoom);
        return "/chatroom/rooms";
    }

    @PostMapping("/chatroom/private/{chatRoomId}")
    public  String privateChatroom(@PathVariable("chatRoomId")String chatRoomId, @RequestParam("password-chatroom") String password,Principal principal, Model model, HttpServletRequest request, HttpSession httpSession){

        ChatRoom chatRoom = chatRoomService.findChatRoomByChatRoomId(chatRoomId);


            if(bCryptPasswordEncoder.matches(password,chatRoom.getPassword())){

                httpSession.setAttribute("username",principal.getName());
                model.addAttribute("chatRoom",chatRoom);

                User users= userService.findExistingEmail(principal.getName());
                model.addAttribute("users",users);

                return "/chatroom/rooms";
            }
            model.addAttribute("error","Invalid Password ");
            return "/chatroom/chatroom";
    }

    @GetMapping("/api/chatrooms/shoutout/all")
    public @ResponseBody ResponseEntity<List<ShoutOut>> getAllShoutOuts(){
        return ResponseEntity.ok().body(shoutOutService.getAllShoutOuts());
    }

    @PostMapping("/api/chatrooms/shoutout/{id}/delete")
    public void deleteShoutOuts(int id){
          shoutOutService.deleteShoutOuts(id);
    }

    @PostMapping("/api/chatrooms/shoutout/save")
    public ResponseEntity<ShoutOut> saveShoutOuts(@RequestBody @RequestParam("message") String message,@RequestParam(value = "email",required = false)String email, ShoutOut shoutOut,Principal principal) throws UnknownHostException {
        InetAddress myIP=InetAddress.getLocalHost();
        User user;
        if(email !=null){
            user = userService.findExistingEmail(email);
        }else{
            user = userService.findExistingEmail(principal.getName());
        }
        shoutOut.setIp(myIP.getHostAddress());
        shoutOut.setMessage(message);
        shoutOut.setUser(user);
        return ResponseEntity.ok().body(shoutOutService.saveShoutOuts(shoutOut));
    }


}
