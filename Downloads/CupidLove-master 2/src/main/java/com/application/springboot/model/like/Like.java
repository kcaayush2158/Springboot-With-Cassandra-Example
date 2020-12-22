package com.application.springboot.model.like;

import com.application.springboot.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Data
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
