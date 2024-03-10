package org.example.signuplogin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "system_role",schema = "public")
public class SystemRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;


}
