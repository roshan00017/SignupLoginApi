package org.example.signuplogin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "refresh_token_info",schema = "public")
public class RefreshTokenInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token ;
    private int userId ;
}
