package com.example.Javada.services;

import com.example.Javada.entity.Book;
import com.example.Javada.entity.Category;
import com.example.Javada.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    public List<Category> getAllCategories(){return categoryRepository.findAll();}
    public Category getCategoryById(Long id){return categoryRepository.findById(id).orElse(null);}
    public Category saveCategory(Category category){return  categoryRepository.save(category);}
    public void addCategory(Category category){
        categoryRepository.save(category);
    }
    public void updateBook(Category category){
        categoryRepository.save(category);
    }
    public void deleteCategory(Long id){categoryRepository.deleteById(id);}

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public List<Category> searchByTentheloai(String keyword) {
        return categoryRepository.findByTentheloaiContaining(keyword);
    }
}
