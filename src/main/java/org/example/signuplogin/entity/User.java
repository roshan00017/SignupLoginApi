package org.example.signuplogin.entity;



import jakarta.persistence.*;
import lombok.Data;
import org.example.signuplogin.entity.Enum.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
//error fixed
@Table(name = "user",schema = "public")


public class User   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "created_at")
    private Date createdAt;



}
