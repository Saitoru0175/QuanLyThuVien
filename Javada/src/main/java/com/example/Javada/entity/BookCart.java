package com.example.Javada.entity;

import java.util.ArrayList;
import java.util.List;

public class BookCart {
    private List<BookCartItem> items;

    public BookCart() {
        items = new ArrayList<>();
    }

    public void addItem(Book book, int quantity) {
        // Kiểm tra xem sách đã tồn tại trong giỏ hàng hay chưa
        for (BookCartItem item : items) {
            if (item.getBook().getMasach().equals(book.getMasach())) {
                // Nếu sách đã tồn tại trong giỏ hàng, tăng số lượng lên
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        // Nếu sách chưa tồn tại trong giỏ hàng, tạo một mục sách mới và thêm vào giỏ hàng
        BookCartItem newItem = new BookCartItem();
        newItem.setBook(book);
        newItem.setQuantity(quantity);
        items.add(newItem);
    }

    public List<BookCartItem> getItems() {
        return items;
    }

    public void setItems(List<BookCartItem> items) {
        this.items = items;
    }

    public void removeItem(Long masach) {
        if (items != null) {
            // Tìm kiếm mục sách có mã sách khớp với masach và xóa nó khỏi danh sách items
            items.removeIf(item -> item.getBook().getMasach().equals(masach));
        }
    }
    public void clearItems() {
        if (items != null) {
            items.clear();
        }
    }
}
