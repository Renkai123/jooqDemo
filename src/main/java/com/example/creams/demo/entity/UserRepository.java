package com.example.creams.demo.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by rk on 2019/12/26.
 */


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findById(Long id, Long customerId);
//
//    List<User> findByIdInAndCustomerId(List<Long> ids, Long customerId);
}
