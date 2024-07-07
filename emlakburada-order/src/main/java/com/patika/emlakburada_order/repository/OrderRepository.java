package com.patika.emlakburada_order.repository;

import com.patika.emlakburada_order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.userId=:userId")
    List<Order> findAllByUserId(Long userId);
}
