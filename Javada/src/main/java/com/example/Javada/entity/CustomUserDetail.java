package com.example.Javada.entity;

import com.example.Javada.repository.IUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetail implements UserDetails {
    private final user user;
    private final IUserRepository userRepository;
    public CustomUserDetail(user user, IUserRepository userRepository){
        this.user = user;
        this.userRepository = userRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        String[] roles= userRepository.getRolesOfUser(user.getId());
        for(String role:roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
