package com.example.Javada.services;

import com.example.Javada.entity.Book;
import com.example.Javada.entity.Borrowing;
import com.example.Javada.entity.Category;
import com.example.Javada.repository.IBorrowingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingService {
    private final IBorrowingRepository borrowingRepository;

    public BorrowingService(IBorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public void saveBorrowing(Borrowing borrowing) {
        borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getAllBorrowing(){return borrowingRepository.findAll();}

    public Borrowing getBorrowingById(Long id){
        return borrowingRepository.findById(id).orElse(null);
    }

    public Borrowing findBorrowById(Long id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }
}
