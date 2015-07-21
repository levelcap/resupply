package com.brave.chardb.repository;

import com.brave.chardb.enums.Genre;
import com.brave.chardb.enums.TimePeriod;
import com.brave.chardb.model.Setting;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SettingRepository extends MongoRepository<Setting, String> {
    List<Setting> findByUserId(String userId);
    List<Setting> findByNameLikeIgnoreCase(String name);
    List<Setting> findByGenre(Genre genre);
    List<Setting> findByTimePeriod(TimePeriod timePeriod);
}