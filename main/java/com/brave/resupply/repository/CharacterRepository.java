package com.brave.chardb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brave.chardb.enums.Genre;
import com.brave.chardb.enums.TimePeriod;
import com.brave.chardb.model.Character;

public interface CharacterRepository extends MongoRepository<Character, String> {
    List<Character> findByUserId(String userId);
    List<Character> findByNameLikeIgnoreCase(String name);
    List<Character> findByGenre(Genre genre);
    List<Character> findByTimePeriod(TimePeriod timePeriod);
    List<Character> findByGroup(String groupId);
}