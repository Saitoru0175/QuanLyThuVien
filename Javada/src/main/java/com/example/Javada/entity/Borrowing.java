package com.example.Javada.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Borrowing {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "borrow_locate")
    private String borrowLocate;

    @Column(name = "TrangThai")
    private boolean trangthai;

    private String mssv;

    @Column(name = "muontra")
    private boolean muontra;

    /*@Column(name = "ngaytra")
    private*/
    private List<String> bookNames;

    public List<String> getBookNames() {
        return bookNames;
    }
    public void setBookNames(List<String> bookNames) {
        this.bookNames = bookNames;
    }

    @ManyToMany
    @JoinTable(name = "borrowing_book",
    joinColumns = @JoinColumn(name = "borrowing_id"),
    inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        book.setBorrowing(this);
    }

    public String getBorrowLocate() {
        return borrowLocate;
    }
    public void setBorrowLocate(String borrowLocate) {
        this.borrowLocate = borrowLocate;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public boolean isMuontra() {
        return muontra;
    }

    public void setMuontra(boolean muontra) {
        this.muontra = muontra;
    }
}
