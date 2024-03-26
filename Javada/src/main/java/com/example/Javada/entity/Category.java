package com.example.Javada.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Table(name = "category")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long matheloai;

    @Column(name = "tentheloai")
    public String tentheloai;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Book> books;
}

