package com.example.creams.demo.entity.repository;

import com.example.creams.demo.filter.BookFilter;
import com.example.creams.demo.model.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by rk on 2019/10/12.
 */

public interface BookRepositoryCustom {

    Page<BookModel> queryPageableBooks(BookFilter filter, Pageable pageable);

}
