package com.application.springboot.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@Data
public class Photos {
    @Id
    private int id;
    private String photoUrl;
    private String photoType;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User principalName;
}
