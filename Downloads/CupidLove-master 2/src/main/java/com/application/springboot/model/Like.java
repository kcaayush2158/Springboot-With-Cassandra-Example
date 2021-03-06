package com.application.springboot.model;

import com.application.springboot.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User likedBy;
    @ManyToOne
    private User likedTo;

    private boolean status;

}
