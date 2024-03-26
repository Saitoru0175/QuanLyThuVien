package com.example.Javada.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Table(name = "role")
@Entity
public class role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idrole;

    @Column(name = "tenrole")
    public String tenrole;

    @ManyToMany(mappedBy = "roles")
    private Set<user> users = new HashSet<>();

    public Long getIdrole() {
        return idrole;
    }

    public void setIdrole(Long idrole) {
        this.idrole = idrole;
    }

    public String getTenrole() {
        return tenrole;
    }

    public void setTenrole(String tenrole) {
        this.tenrole = tenrole;
    }

    public Set<user> getUsers() {
        return users;
    }

    public void setUsers(Set<user> users) {
        this.users = users;
    }
}
