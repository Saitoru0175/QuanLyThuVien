package com.example.Javada.controller;

import com.example.Javada.entity.*;
import com.example.Javada.repository.IBookRepository;
import com.example.Javada.repository.ICategoryRepository;
import com.example.Javada.services.BookService;
import com.example.Javada.services.BorrowingService;
import com.example.Javada.services.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class LibController {
    public final BookService bookService;
    public final CategoryService categoryService;

    public final BorrowingService borrowingService;
    private ICategoryRepository categoryRepository;
    private IBookRepository bookRepository;

    public LibController(BookService bookService, CategoryService categoryService, BorrowingService borrowingService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.borrowingService = borrowingService;
    }
    @GetMapping
    public String index(){return "lib/index";}


    @GetMapping("/category/{categoryId}")
    public String showBooksByCategory(@PathVariable Long categoryId, Model model) {
        Category category = categoryService.findById(categoryId);
        List<Book> books = bookService.findByCategory(category);
        model.addAttribute("category", category);
        model.addAttribute("books", books);
        return "lib/book";
    }
    @GetMapping("/book")
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

        return "lib/book";
    }

    @GetMapping("/contact")
    public String contact(){return "lib/contact";}
    @GetMapping("/about")
    public String about(){return "lib/about";}


/*-----------------------------------------------------------------------------------*/
@GetMapping("/addToCart/{masach}")
public String addToCart(@PathVariable("masach") Long masach, HttpSession session) {
    Book book = bookService.findById(masach);
    BookCart bookCart = (BookCart) session.getAttribute("bookCart");
    if (bookCart == null) {
        bookCart = new BookCart();
        session.setAttribute("bookCart", bookCart);
    }
    bookCart.addItem(book, 1);
    return "redirect:/book";
}

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        BookCart bookCart = (BookCart) session.getAttribute("bookCart");
        if (bookCart == null) {
            bookCart = new BookCart();
            session.setAttribute("bookCart", bookCart);
        }
        model.addAttribute("cartItems", bookCart.getItems());
        return "lib/cart";
    }
    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {
        BookCart bookCart = (BookCart) session.getAttribute("bookCart");
        if (bookCart != null) {
            bookCart.clearItems();
        }
        return "redirect:/cart";
    }
    @GetMapping("/removeFromCart/{masach}")
    public String removeFromCart(@PathVariable("masach") Long masach, HttpSession session) {
        BookCart bookCart = (BookCart) session.getAttribute("bookCart");
        if (bookCart != null) {
            bookCart.removeItem(masach);
        }
        return "redirect:/cart";
    }
    /*----------------------------------------------------------*/
    @GetMapping("/cart/export")
    public void exportCart(HttpServletResponse response, HttpSession session) throws IOException {
        // Lấy danh sách mặt hàng từ giỏ hàng
        BookCart bookCart = (BookCart) session.getAttribute("bookCart");
        List<BookCartItem> cartItems = bookCart.getItems();

        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Cart Items");

        // Tạo tiêu đề cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã sách");
        headerRow.createCell(1).setCellValue("Tác giả");
        headerRow.createCell(2).setCellValue("Thể loại");
        headerRow.createCell(3).setCellValue("Năm xuất bản");
        headerRow.createCell(4).setCellValue("Số lượng");
        // Ghi dữ liệu các mặt hàng vào các dòng
        int rowNum = 1;
        for (BookCartItem cartItem : cartItems) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cartItem.getBook().getTensach());
            row.createCell(1).setCellValue(cartItem.getBook().getTacgia());
            row.createCell(2).setCellValue(cartItem.getBook().getCategory().getTentheloai());
            row.createCell(3).setCellValue(cartItem.getBook().getNamxb());
            row.createCell(4).setCellValue(cartItem.getQuantity());
        }

        // Thiết lập header và loại file response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=cart_items.xlsx");

        // Ghi workbook vào response
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    /*----------------------------------------------*/

    @GetMapping("/borrow")
    public String borrowBooks(HttpSession session, RedirectAttributes redirectAttributes) {
        BookCart bookCart = (BookCart) session.getAttribute("bookCart");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (bookCart == null || bookCart.getItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("emptyCartMessage", "Giỏ hàng trống");
            return "redirect:/cart";
        }
        LocalDate borrowDate = LocalDate.now();
        String pickupLocation = "Khu E3, Hutech";
        String mssv = authentication.getName();

        Borrowing borrowing = new Borrowing();
        borrowing.setBorrowDate(borrowDate);
        borrowing.setBorrowLocate(pickupLocation);
        borrowing.setTrangthai(false);
        borrowing.setMssv(mssv);
        borrowing.setMuontra(false);

        List<BookCartItem> cartItems = bookCart.getItems();
        for (BookCartItem cartItem : cartItems) {
            Book book = cartItem.getBook();
            borrowing.addBook(book);
        }
        borrowingService.saveBorrowing(borrowing);

        session.removeAttribute("bookCart");

        return "lib/info";
    }
}
