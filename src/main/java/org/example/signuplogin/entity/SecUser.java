package org.example.signuplogin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.signuplogin.repositories.RoleRepository;
import org.example.signuplogin.repositories.UserRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class SecUser implements UserDetails {

    private final User user;

    private SystemRole userRole;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


            return List.of(new SimpleGrantedAuthority(this.userRole.getName()));


    }
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getDisplayName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
