package com.example.Javada.controller;

import com.example.Javada.entity.*;
import com.example.Javada.services.BookService;
import com.example.Javada.services.BorrowingService;
import com.example.Javada.services.CategoryService;
import com.example.Javada.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(){return "admin/index";}

    /*@GetMapping("/book")
    public String showAllBook(Model model,

                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(required = false) String tensach,
                              @PageableDefault(size = 4, sort = "masach", direction = Sort.Direction.ASC) Pageable pageable) {

        int pageSize = 4; // Số lượng mục trên mỗi trang
        pageable = PageRequest.of(page, pageSize, Sort.by("masach").ascending());
        Page<Book> bookPage;
        List<Category> categories = categoryService.getAllCategories();
        if (tensach != null && !tensach.isEmpty()) {
            bookPage = bookService.searchByTitle(tensach, pageable);
        } else {
            bookPage = bookService.getAllBooks(pageable);
        }
        List<Book> books = bookPage.getContent();
        model.addAttribute("books", books);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("tensach", tensach);

        return "admin/book/list";
    }*/
    @GetMapping("/book")
    public String showAllBook(Model model){
        List<Book> books = bookService.getAllBook();
        model.addAttribute("book", books);
        return "admin/book/list";
    }

    @GetMapping("/addBook")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/book/add";
    }
    @PostMapping("/addBook")
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult result, Model model) {
        if(result != null && result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream().map(ObjectError::getDefaultMessage).toList();
            model.addAttribute("errors", errors);
            return "admin/book/add";
        }
        bookService.addBook(book);
        return "redirect:/admin/book";
    }

    @GetMapping({"/delBook/{masach}"})
    public String deleteBook(@PathVariable("masach") Long masach) {
        this.bookService.deleteBook(masach);
        return "redirect:/admin/book";
    }

    @GetMapping({"/editBook/{masach}"})
    public String editBookForm(@PathVariable("masach") Long masach, Model model) {
        Book book = this.bookService.getBookById(masach);
        model.addAttribute("book", book);
        model.addAttribute("categories", this.categoryService.getAllCategories());
        return "admin/book/edit";
    }
    @PostMapping({"/editBook/{masach}"})
    public String editBook(@PathVariable("masach") Long masach, @ModelAttribute("book") Book book) {
        book.setMasach(masach);
        this.bookService.updateBook(book);
        return "redirect:/admin/book";
    }

  /*-----------------------------------------------------------------------Category*/
  @GetMapping("/categories")
  public String showAllCategory(Model model) {
      List<Category> categories = categoryService.getAllCategories();
      model.addAttribute("categories", categories);
      return "admin/category/list";
  }
    @GetMapping("/addCategory")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category/add";
    }
    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("category") @Valid Category category, BindingResult result, Model model) {
        if(result != null && result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream().map(ObjectError::getDefaultMessage).toList();
            model.addAttribute("errors", errors);
            return "admin/category/add";
        }
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }
    /*-----------------Edit*/
    @GetMapping({"/editCategory/{matheloai}"})
    public String editCategoryForm(@PathVariable("matheloai") Long matheloai, Model model) {
        Category category = this.categoryService.getCategoryById(matheloai);
        model.addAttribute("category", category);
        return "admin/category/edit";
    }
    @PostMapping({"/editCategory/{matheloai}"})
    public String editCategory(@PathVariable("matheloai") Long matheloai, @ModelAttribute("category") Category category) {
        category.setMatheloai(matheloai);
        this.categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }
    /*-----------------Delete*/
    @GetMapping({"/delCategory/{matheloai}"})
    public String deleteCategory(@PathVariable("matheloai") Long matheloai) {
        this.categoryService.deleteCategory(matheloai);
        return "redirect:/admin/categories";
    }


    @GetMapping("/searchlistcate")
    public String searchcates(@RequestParam(value = "tentheloai", required = false) String tentheloai,
                              Model model) {
        List<Category> categories;
        if (tentheloai != null) {
            categories = categoryService.searchByTentheloai(tentheloai);
        } else {
            categories = Collections.emptyList();
        }
        model.addAttribute("categories", categories);
        return "admin/category/list";
    }

    /*-------------------------------------------User----*/
    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<user> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/list";
    }
    @GetMapping({"/delUser/{id}"})
    public String deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new user());
        return "admin/user/add";
    }
    @PostMapping("/addUser")
    public String addBook(@ModelAttribute("user") @Valid user user, BindingResult result, Model model) {
        if(result != null && result.hasErrors()){
            List<String> errors = result.getAllErrors()
                    .stream().map(ObjectError::getDefaultMessage).toList();
            model.addAttribute("errors", errors);
            return "admin/user/add";
        }
        user.setPassword(new
                BCryptPasswordEncoder().encode(user.getPassword()));
        userService.addBook(user);
        return "redirect:/admin/users";
    }

    @GetMapping({"/editUser/{id}"})
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        user user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        user.setPassword(new
                BCryptPasswordEncoder().encode(user.getPassword()));
        return "admin/user/edit";
    }
    @PostMapping({"/editUser/{id}"})
    public String editCategory(@PathVariable("id") Long id, @ModelAttribute("id") user user) {
        user.setId(id);
        user.setPassword(new
                BCryptPasswordEncoder().encode(user.getPassword()));
        this.userService.saveUser(user);
        return "redirect:/admin/users";
    }

    /*------------------------------------------------------*/
    @GetMapping("/borrowing")
    public String showAllBorrow(Model model) {
        List<Borrowing> borrowings = borrowingService.getAllBorrowing();
        List<String> bookNames = new ArrayList<>();
        model.addAttribute("borrowings", borrowings);
        for (Borrowing borrowing : borrowings) {
            List<Book> books = borrowing.getBooks();
            for (Book book : books) {
                bookNames.add(book.getTensach());
            }
        }
        return "admin/borrowing/list";
    }
    @PostMapping("/toggleStatus")
    public String toggleStatus(@RequestParam("borrowingId") Long borrowingId) {
        Borrowing borrowing = borrowingService.findBorrowById(borrowingId);
        boolean currentStatus = borrowing.isTrangthai();
        borrowing.setTrangthai(!currentStatus);
        borrowingService.saveBorrowing(borrowing);
        return "redirect:/admin/borrowing";
    }
    @PostMapping("/toggleStatuss")
    public String toggleStatuss(@RequestParam("borrowingId") Long borrowingId) {
        Borrowing borrowing = borrowingService.findBorrowById(borrowingId);
        boolean currentStatus = borrowing.isMuontra();
        borrowing.setMuontra(!currentStatus);
        borrowingService.saveBorrowing(borrowing);
        return "redirect:/admin/borrowing";
    }



    /*-----------------------------------------------------------------*/

}
