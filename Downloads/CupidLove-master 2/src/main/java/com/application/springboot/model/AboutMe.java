package com.application.springboot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutMe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String liveIn;
    @NotEmpty
    private String workAs;
    @NotEmpty
    private String education;
    @NotEmpty
    private String haveKids;
    @NotEmpty
    private String known;
    @Lob
    private String lookingFor;
    @NotEmpty
    private String smoke;
    @NotEmpty
    private String drink;
    @NotEmpty
    private String height;
    @NotEmpty
    private String bodyType;
    @NotEmpty
    private String eyes;
    @NotEmpty
    private String hair;
    @NotEmpty
    private String relationship;
    @NotEmpty
    private String languages;
    @NotEmpty
    private String gender;
    @Min(18)
    @Max(80)
    @NotNull
    private int age;
    @Lob
    @NotEmpty
    private String bio;
    @Lob
    @NotEmpty
    private String interests;
    @NotEmpty
    private String country;


}
