package com.example.creams.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by rk on 2019/12/26.
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {

    private BigDecimal age;

    private String sex;
}
