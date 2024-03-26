package com.example.Javada.repository;

import com.example.Javada.entity.Book;
import com.example.Javada.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByTentheloaiContaining(String keyword);
}
