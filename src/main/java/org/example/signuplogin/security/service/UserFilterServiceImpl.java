package org.example.signuplogin.security.service;

import lombok.AllArgsConstructor;
import org.example.signuplogin.entity.SecUser;
import org.example.signuplogin.entity.SystemRole;
import org.example.signuplogin.entity.User;
import org.example.signuplogin.entity.UserRole;
import org.example.signuplogin.repositories.RoleRepository;
import org.example.signuplogin.repositories.UserRepository;
import org.example.signuplogin.repositories.UserRoleRepository;
import org.example.signuplogin.security.UserFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class UserFilterServiceImpl implements UserFilterService {
    private final UserRepository userRepository; // Assuming you have a UserRepository


    private final UserRoleRepository userRoleRepository; // Assuming you have a UserRoleRepository


    private final RoleRepository roleRepository; // Assuming you have a RoleRepository




    public UserFilterServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;

    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {



            @Override
            public UserDetails loadUserByUsername(String username) {
                User user = userRepository.findByEmail(username);
                if (user == null) {
                    throw new

                            UsernameNotFoundException("User not found with username: " + username);
                }

                UserRole userRole = userRoleRepository.findByUserId(user.getId());
                SystemRole roleName = roleRepository.findById(userRole.getRoleId());

                // Create a SecUser object with the retrieved user and role information
                return new SecUser(user, roleName);

            }

        };

    }
}
