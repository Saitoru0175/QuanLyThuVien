package com.example.Javada.repository;

import com.example.Javada.entity.Book;
import com.example.Javada.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookRepository extends JpaRepository<Book, Long> {
    List<Book> findByCategory(Category category);

    List<Book> findByTensachContaining(String keyword);
    Page<Book> findByTensach(String tensach, Pageable pageable);

}
