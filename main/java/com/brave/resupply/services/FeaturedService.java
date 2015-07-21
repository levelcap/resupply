package com.brave.chardb.services;

import com.brave.chardb.model.Featured;
import com.brave.chardb.repository.CharacterRepository;
import com.brave.chardb.repository.FeaturedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.brave.chardb.model.Character;

/**
 * Created by dcohen on 3/4/15.
 */
@Service
public class FeaturedService {
    private static final String FEATURED_ID = "54f77f6764617400ae040000";
    @Autowired
    FeaturedRepository featuredRepository;

    @Autowired
    CharacterRepository characterRepository;

    public Character getFeaturedCharacter() {
        Featured featured = featuredRepository.findOne(FEATURED_ID);
        return characterRepository.findOne(featured.getFeatured());
    }
}
