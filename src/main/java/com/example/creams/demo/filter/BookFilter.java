package com.example.creams.demo.filter;

import com.example.creams.demo.jooq.Tables;
import com.example.creams.demo.jooq.tables.Book;
import lombok.*;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.jooq.tools.StringUtils;

/**
 * Created by rk on 2019/10/12.
 */


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookFilter {

    private String name;

    public Condition whereCondition(){
        Book book = Tables.BOOK;
        Condition condition = DSL.trueCondition();

        if(!StringUtils.isBlank(name)){
            condition = condition.and(book.NAME.contains(name));
        }

        return condition;
    }

}
