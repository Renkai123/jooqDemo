package com.example.creams.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by rk on 2019/12/26.
 */

@Getter
@AllArgsConstructor
public enum UserEnum {

    A("A"),
    B("A");

    private String desc;
}
