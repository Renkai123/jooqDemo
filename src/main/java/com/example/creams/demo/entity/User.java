package com.example.creams.demo.entity;


import com.example.creams.demo.enums.UserEnum;
import com.google.common.collect.Range;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by rk on 2019/12/24.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String userDetailString;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private UserDetail userDetail;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<LocalDate, BigDecimal> jsonValue;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<Range<LocalDate>, BigDecimal> jsonValueRange;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<UserDetail> userDetails;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Set<UserDetail> userDetails1;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Set<UserEnum> permission;
}
