package com.example.creams.demo.entity.repository;


import com.example.creams.demo.filter.BookFilter;
import com.example.creams.demo.jooq.Tables;
import com.example.creams.demo.jooq.tables.Book;
import com.example.creams.demo.model.BookModel;
import org.jooq.DSLContext;
import org.jooq.SelectHavingStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by rk on 2019/10/12.
 */

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @Resource
    private DSLContext dsl;

    private final Book book = Tables.BOOK;

    public Page<BookModel> queryPageableBooks(BookFilter filter, Pageable pageable) {
        SelectHavingStep query = dsl
                .select(
                        book.ID,
                        book.NAME
                )
                .from(book)
                .where(filter.whereCondition());
        int count = dsl.fetchCount(query);

        List<BookModel> bookModels = query
                .limit((int) pageable.getOffset(), pageable.getPageSize())
                .fetchInto(BookModel.class);

        return new PageImpl<>(bookModels, pageable, count);
    }
}
