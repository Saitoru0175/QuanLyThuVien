package com.example.Javada.entity;

import com.example.Javada.validator.annotation.ValidUsername;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Table(name = "user")
@Entity
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @ValidUsername
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "diachi")
    private String diachi;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "quyen", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Set<role> getRoles() {
        return roles;
    }

    public void setRoles(Set<role> roles) {
        this.roles = roles;
    }

}
