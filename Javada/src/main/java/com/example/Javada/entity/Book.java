package com.example.Javada.entity;

import com.example.Javada.validator.annotation.ValidUserId;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Data
@Table(name = "book")
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long masach;

    @Column(name = "tensach")
    private String tensach;

    @Column(name = "tacgia")
    private String tacgia;

    @Column(name = "namxuatban")
    private int namxb;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "hinhanh")
    private String hinhanh;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ValidUserId
    private user user;

    @ManyToOne
    @JoinColumn(name = "borrowing_id")
    private Borrowing borrowing;

    public void setBorrowing(Borrowing borrowing){
        this.borrowing = borrowing;
    }
}