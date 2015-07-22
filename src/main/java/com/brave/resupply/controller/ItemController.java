package com.brave.resupply.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brave.resupply.model.Item;
import com.brave.resupply.repository.ItemRepository;
import com.brave.resupply.repository.UserRepository;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/api/item")
public class ItemController extends BaseController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ItemRepository itemRepository;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<List<Item>> getItems(
			@RequestParam(value = "available", required = false) Boolean available) {
		if (null == available) {
			return new ResponseEntity<List<Item>>(itemRepository.findAll(),
					HttpStatus.OK);
		} else if (available) {
			return new ResponseEntity<List<Item>>(
					itemRepository.findByAvailableTrue(), HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Item>>(
					itemRepository.findByAvailableFalse(), HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<Item> getItem(@PathVariable("id") String id) {
		return new ResponseEntity<Item>(itemRepository.findOne(id),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<List<Item>> saveOrder(@RequestBody List<Item> items) {
		if (isLoggedIn()) {
			for (Item item : items) {
				if (item.getId() == null) {
					item.setId(new ObjectId().toString());
				}
			}
			itemRepository.save(items);

			return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Item>>(HttpStatus.UNAUTHORIZED);
		}
	}
}
