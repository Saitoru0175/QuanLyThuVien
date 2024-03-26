package com.example.Javada.repository;

import com.example.Javada.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBorrowingRepository extends JpaRepository<Borrowing, Long> {
}
