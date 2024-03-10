package org.example.signuplogin.repositories;


import org.example.signuplogin.entity.SystemRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<SystemRole, Long> {
  SystemRole findByName(String Name);
  SystemRole findById(int id);



}
