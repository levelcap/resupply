package com.brave.chardb.repository;

import com.brave.chardb.model.Featured;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeaturedRepository extends MongoRepository<Featured, String> {
}