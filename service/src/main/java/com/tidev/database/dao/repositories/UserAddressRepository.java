package com.tidev.database.dao.repositories;

import com.tidev.database.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer>, QuerydslPredicateExecutor<UserAddress> {

    Optional<UserAddress> findByUserId(Integer userId);
}
