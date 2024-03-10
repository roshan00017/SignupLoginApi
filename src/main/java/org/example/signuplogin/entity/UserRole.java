package org.example.signuplogin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_role",schema = "public")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int roleId;
    private int userId;

}
