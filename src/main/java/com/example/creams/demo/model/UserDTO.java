package com.example.creams.demo.model;

import com.example.creams.demo.entity.UserDetail;
import com.example.creams.demo.enums.UserEnum;
import com.google.common.collect.Range;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by rk on 2019/12/27.
 */

@Getter
@Setter
public class UserDTO {

    private Long id;

    private LocalDate date;

    private Map<LocalDate, BigDecimal> jsonValue;

    private List<UserDetail> userDetails;

    private Set<UserDetail> userDetails1;

    private Set<UserEnum> permission;

    private Map<Range<LocalDate>, BigDecimal> jsonValueRange;
}
