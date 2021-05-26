package com.application.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String topicName;
    private String topicQuestion;
    private String topicAnswer;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;



}
