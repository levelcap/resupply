package com.brave.resupply.repository;

import com.brave.resupply.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findByAvailableTrue();
    List<Item> findByAvailableFalse();
}