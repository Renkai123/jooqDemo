package com.example.creams.demo.service;

import com.example.creams.demo.entity.repository.BookRepository;
import com.example.creams.demo.filter.BookFilter;
import com.example.creams.demo.model.BookModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by rk on 2019/10/12.
 */

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Page<BookModel> queryPageableBoos(BookFilter bookFilter, Pageable pageable) {
        return bookRepository.queryPageableBooks(bookFilter, pageable);
    }
}
