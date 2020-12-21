package com.application.chat.controller.privatemessage;

import com.application.chat.model.PrivateChatMessage;
import com.application.chat.service.PrivateMessageService;
import com.application.springboot.model.User;
import com.application.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PrivateMessageController {
    @Autowired
    private PrivateMessageService privateMessageService;
    @Autowired
    private UserService userService;

    @GetMapping("/direct/inbox")
    public List<PrivateChatMessage> getAllUsersMessage(Principal principal){
        User user = userService.findExistingEmail(principal.getName());
        List<PrivateChatMessage> messages = privateMessageService.getAllMessagesBySender(user);
        return messages;
    }


//    @PostMapping("/direct/u/{userId}")
//    public User redirectUser(@ModelAttribute("privateMessage") PrivateChatMessage privateChatMessage,Principal principal ,@PathVariable("userId") final int userId) throws UnknownHostException {
//        User user = userService.findUserById(userId);
//        return user;
//
//    }
@GetMapping("/direct/u/{userId}/count")
public int countUnreadMessages(Principal principal, @PathVariable("userId") int userId)  {
    User user = userService.findExistingEmail(principal.getName());
    System.out.print(userService.findUserById(userId));
    System.out.print(userService.findExistingEmail(principal.getName()));
    return privateMessageService.countUnreadMessages(user,userService.findUserById(userId),false);
}

    @GetMapping("/direct/u/{userId}/message")
    public List<PrivateChatMessage> getMessages(Principal principal,@PathVariable("userId") String userId) throws UnsupportedEncodingException {
        User user = userService.findExistingEmail(principal.getName());
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(userId);
        String decodedString = new String(bytes);
        System.out.print(decodedString);
        System.out.print(Integer.parseInt(decodedString));
        return privateMessageService.getSenderAndReceiverMessages(Integer.parseInt(decodedString),user.getId());
    }


    @PostMapping("/direct/u/{messageId}/send")
    public PrivateChatMessage save(@ModelAttribute("privateMessage") PrivateChatMessage privateChatMessage,@RequestParam("message")String message,Principal principal ,@PathVariable("messageId") final String messageId) throws UnknownHostException {
        User user = userService.findExistingEmail(principal.getName());

        InetAddress myIP=InetAddress.getLocalHost();
        privateChatMessage.setStatus(false);
        privateChatMessage.setIp(myIP.getHostAddress());
        privateChatMessage.setDate(new Date());
        privateChatMessage.setMessage(message);
        privateChatMessage.setUrl("");
        privateChatMessage.setReceiver(privateChatMessage.getReceiver());
        privateChatMessage.setSender(user);
        privateChatMessage.getReceiver();
        return privateMessageService.save(privateChatMessage);

    }

    @GetMapping("/direct/u/unreadMessage")
    public int getUnreadMessages(Principal principal){
        User user =userService.findExistingEmail(principal.getName());
        return privateMessageService.getUnreadMessages(user.getEmail(),false);
    }


}
