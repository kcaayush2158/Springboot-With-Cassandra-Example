package com.application.demo.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("tutorial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tutorial {
    @PrimaryKey
    @Column("id")
    private UUID id;
    private String title;
    private String description;
    private boolean published;
}
