package com.brave.resupply.repository;

import com.brave.resupply.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserIdOrderByDateDesc(String userID);
    List<Order> findByUserIdAndDate(String userId, String date);
    List<Order> findByDate(String date);
    List<Order> findByDateAndFilledFalseAndSentTrue(String date);
}