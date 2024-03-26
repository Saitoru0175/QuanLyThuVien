package com.example.Javada.services;

import com.example.Javada.entity.Book;
import com.example.Javada.entity.Category;
import com.example.Javada.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private final IBookRepository bookRepository;

    public BookService(IBookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public Page<Book> getAllBooks(Pageable pageable){
        return bookRepository.findAll(pageable);
    }
    public List<Book> getAllBook(){return bookRepository.findAll();}


    public Book getBookById(Long id){
        return bookRepository.findById(id).orElse(null);
    }

    public void addBook(Book book){
        bookRepository.save(book);
    }
    public void updateBook(Book book){
        bookRepository.save(book);
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }
    public Page<Book> searchByTitle(String tensach,Pageable pageable) {
        return bookRepository.findByTensach(tensach,pageable);
    }

    public List<Book> findByCategory(Category category){
        return bookRepository.findByCategory(category);
    }

    public void saveBook(Book book){bookRepository.save(book);}
}
