package com.application.chat.model;

import com.application.springboot.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String type;
    private String ip;
    private String chatRoomId;
    private String message;
    private boolean status;
    private Date date;
    @ManyToOne
    private User sender_id;
    @ManyToOne
    private User receiver_id;
}
