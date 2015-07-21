package com.brave.chardb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brave.chardb.model.User;
import com.brave.chardb.repository.UserRepository;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<User> getUser() {
		return new ResponseEntity<User>(getCurrentUser(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<User> saveUser(@RequestBody User user) {
		if (isLoggedIn()) {
			User currentUser = getCurrentUser();
			if (user.getPassword() == null) {
				user.setPassword(currentUser.getPassword());
			} else {
				ShaPasswordEncoder encoder = new ShaPasswordEncoder();
				String hashedPassword = encoder.encodePassword(
						user.getPassword(), null);
				user.setPassword(hashedPassword);
			}
			user.setId(currentUser.getId());

			user = userRepository.save(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			List<User> existing = userRepository.findByEmailIgnoreCase(user.getEmail());
			if (existing != null && existing.size() > 0) {
				return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
			}
			ShaPasswordEncoder encoder = new ShaPasswordEncoder();
			String hashedPassword = encoder.encodePassword(
					user.getPassword(), null);
			user.setPassword(hashedPassword);
			user.setUsername(user.getEmail());
			userRepository.save(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value= "/username", method = RequestMethod.POST)
	public HttpEntity<User> saveDisplayName(@RequestBody String username) {
		if (isLoggedIn()) {
			User currentUser = getCurrentUser();
			List<User> existing = userRepository.findByUsernameIgnoreCase(username);
			if (existing != null && existing.size() > 0) {
				if (!existing.get(0).getId().equals(currentUser.getId())) {
					return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);		
				}
			}
			if (StringUtils.isEmpty(username)) {
				username = currentUser.getId();
			}
			currentUser.setUsername(username);
			userRepository.save(currentUser);
			return new ResponseEntity<User>(currentUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
	}	
}
