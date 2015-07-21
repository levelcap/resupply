package com.brave.chardb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brave.chardb.enums.Genre;
import com.brave.chardb.enums.TimePeriod;
import com.brave.chardb.model.Location;

public interface LocationRepository extends MongoRepository<Location, String> {
    List<Location> findByUserId(String userId);
    List<Location> findByNameLikeIgnoreCase(String name);
    List<Location> findByGenre(Genre genre);
    List<Location> findByTimePeriod(TimePeriod timePeriod);
    List<Location> findByGroup(String groupId);
}