package org.example.signuplogin.repositories;

import org.example.signuplogin.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    UserRole findByUserId(int UserId);



}
