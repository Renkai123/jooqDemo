package com.example.creams.demo;

import com.example.creams.demo.entity.QUser;
import com.example.creams.demo.entity.User;
import com.example.creams.demo.entity.UserDetail;
import com.example.creams.demo.entity.UserRepository;
import com.example.creams.demo.enums.UserEnum;
import com.example.creams.demo.model.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.CollectionExpression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.TemplateExpression;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.vladmihalcea.hibernate.type.json.internal.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

    @Resource
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void createBatch() {
        for (int i = 0; i < 100; i++) {
            create();
        }
    }

    @Test
    public void create() {
        Map<LocalDate, BigDecimal> map = new HashMap<>();
        map.put(LocalDate.of(2019, 01, 01), BigDecimal.valueOf(1));
        map.put(LocalDate.of(2019, 02, 01), BigDecimal.valueOf(2));
        map.put(LocalDate.of(2019, 03, 01), BigDecimal.valueOf(3));
        map.put(LocalDate.of(2019, 04, 01), BigDecimal.valueOf(4));
        map.put(LocalDate.of(2019, 05, 01), BigDecimal.valueOf(5));
        map.put(LocalDate.of(2019, 06, 01), BigDecimal.valueOf(6));

        User user = new User();
        UserDetail userDetail = new UserDetail();
        Set<UserEnum> permissions = new HashSet<>();
        permissions.add(UserEnum.A);
        permissions.add(UserEnum.B);
        user.setPermission(permissions);
        Set<UserDetail> userDetails = new HashSet<>();
        userDetails.add(userDetail);
        userDetail.setAge(BigDecimal.valueOf(11));
        userDetail.setSex("男");
        user.setUserDetail(userDetail);
        user.setUserDetails(Arrays.asList(userDetail));
        user.setUserDetails1(userDetails);
        user.setDate(LocalDate.now());
        user.setJsonValue(map);

        userRepository.save(user);
    }

    @Test
    public void query() {

        List<User> users = userRepository.findAll();
        BigDecimal value = users.stream().map(
                u -> {
                    List<LocalDate> aaa = u.getJsonValue().entrySet().stream().map(ua -> ua.getKey()).collect(Collectors.toList());
                    BigDecimal a = u.getJsonValue().get(LocalDate.of(2019, 06, 01));
                    return a;
                }
        ).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        log.info(users.get(4).getUserDetails().get(0).getSex());
        log.info(value + "");
    }


    QUser user = QUser.user;
    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    public void querydsl() {

        NumberTemplate<BigDecimal> exp = Expressions.numberTemplate(BigDecimal.class, "json_extract({0},REPLACE(REPLACE(JSON_SEARCH(user_details, 'one', '男'), 'sex', 'age'), '\"', ''))", user.userDetailString);

        List<UserDTO> users = queryFactory.select(
                Projections.bean(UserDTO.class,
                        user.id,
                        user.date,
                        user.jsonValue,
                        user.permission,
                        user.userDetails,
                        user.userDetails1,
                        user.userDetail,
                        user.jsonValueRange
                ))
                .from(user)
                .where(exp.gt(10))
                .fetch();

        Assert.assertEquals(Long.valueOf(10), users.get(0).getId());

    }

    @Test
    public void querydslNull() {

        NumberTemplate<BigDecimal> exp = Expressions.numberTemplate(BigDecimal.class, "json_extract({0},{1})", user.userDetail, "$.age");
        BooleanTemplate b = Expressions.booleanTemplate("{0} = json_array()", user.userDetails1);


        List<UserDTO> users = queryFactory.select(
                Projections.bean(UserDTO.class,
                        user.id,
                        user.date,
                        user.jsonValue,
                        user.permission,
                        user.userDetails,
                        user.userDetails1,
                        user.userDetail,
                        user.jsonValueRange
                ))
                .from(user)
                .where(b)
                .fetch();

        log.info(users.toString());

    }

    @Test
    @Transactional
    public void update() {


        Set<UserDetail> userDetails = new HashSet<>();
        userDetails.add(new UserDetail(BigDecimal.valueOf(1), "男"));
        userDetails.add(new UserDetail(BigDecimal.valueOf(2), "女"));
        userDetails.add(new UserDetail(BigDecimal.valueOf(3), "男"));
//        CollectionExpression<Set<UserDetail>,UserDetail> templateExpression = ExpressionUtils.template(Set.class, "json_array({0})", userDetails);
        queryFactory.update(user)
//                .set(user.userDetails1, templateExpression)
                .where(user.id.eq(6L))
                .execute();


    }



}
