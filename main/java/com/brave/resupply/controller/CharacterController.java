package com.brave.chardb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brave.chardb.model.Character;
import com.brave.chardb.model.User;
import com.brave.chardb.repository.CharacterRepository;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/api/character")
public class CharacterController extends BaseController {

    @Autowired
    private CharacterRepository characterRepository;

    @RequestMapping("/{id}")
    @ResponseBody
    public HttpEntity<Character> getCharacter(@PathVariable("id") String id) {
        Character character = characterRepository.findOne(id);
        return new ResponseEntity<Character>(character, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<Character> saveCharacter(@PathVariable("id") String id, @RequestBody Character character) {
        if (isLoggedIn()) {
            long currentTime = System.currentTimeMillis();
            character.setUpdated(currentTime);

        	if (StringUtils.isEmpty(character.getUrl())) {
        		character.setUrl("/images/blank.png");
        	}
        	character.setId(id);
            User currentUser = getCurrentUser();
            Character existingCharacter = characterRepository.findOne(id);
            if (existingCharacter != null) {
                if (existingCharacter.getUserId().equals(currentUser.getId())) {
                	character.setUserId(currentUser.getId());
                    character = characterRepository.save(character);
                    return new ResponseEntity<Character>(character, HttpStatus.OK);
                } else {
                    return new ResponseEntity<Character>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                character.setUserId(currentUser.getId());
                character.setCreated(currentTime);
                character = characterRepository.save(character);
                return new ResponseEntity<Character>(character, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Character>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public HttpEntity<Character> deleteCharacter(@PathVariable("id") String id) {
        if (isLoggedIn()) {
        	User currentUser = getCurrentUser();
            Character existingCharacter = characterRepository.findOne(id);
            if (existingCharacter != null) {
                if (existingCharacter.getUserId().equals(currentUser.getId())) {
                    characterRepository.delete(id);
                    return new ResponseEntity<Character>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<Character>(HttpStatus.UNAUTHORIZED);
                }
            } else {
            	return new ResponseEntity<Character>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Character>(HttpStatus.UNAUTHORIZED);
        }
    }    
}
