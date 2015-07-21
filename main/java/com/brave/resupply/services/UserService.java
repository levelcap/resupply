package com.brave.chardb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brave.chardb.model.User;
import com.brave.chardb.repository.UserRepository;

/**
 * Created by dcohen on 3/4/15.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getByUsername(String username) {
        List<User> users = userRepository.findByUsernameIgnoreCase(username);
        if (users != null && users.size() > 0) {
        	return users.get(0);
        } else {
        	return null;
        }
    }
}
