package com.example.creams.demo.controller;

import com.example.creams.demo.filter.BookFilter;
import com.example.creams.demo.model.BookModel;
import com.example.creams.demo.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by rk on 2019/10/14.
 */

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Page<BookModel> queryPageableBoos(BookFilter bookFilter, @PageableDefault Pageable pageable) {
        return bookService.queryPageableBoos(bookFilter, pageable);
    }
}
