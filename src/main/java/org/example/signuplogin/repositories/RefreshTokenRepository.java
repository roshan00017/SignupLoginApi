package org.example.signuplogin.repositories;

import org.example.signuplogin.entity.RefreshTokenInfo;
import org.example.signuplogin.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenInfo,Long> {
    RefreshTokenInfo findByUserId(int UserId);


    RefreshTokenInfo findByToken(String Token);

}
