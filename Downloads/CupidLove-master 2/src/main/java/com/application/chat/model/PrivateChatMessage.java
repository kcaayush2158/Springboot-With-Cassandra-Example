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
    private String ip;
    private String messageId;
    private String message;
    private boolean status;
    @Temporal(TemporalType.TIME)
    private Date date;
    private String url;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
}
