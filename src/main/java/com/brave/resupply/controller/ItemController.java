package com.brave.resupply.controller;

import com.brave.resupply.model.Item;
import com.brave.resupply.model.Order;
import com.brave.resupply.model.User;
import com.brave.resupply.repository.ItemRepository;
import com.brave.resupply.repository.OrderRepository;
import com.brave.resupply.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    public HttpEntity<List<Item>> getItems(@RequestParam(value = "available", required = false) Boolean available) {
        if (null == available) {
            return new ResponseEntity<List<Item>>(itemRepository.findAll(), HttpStatus.OK);
        } else if (available) {
            return new ResponseEntity<List<Item>>(itemRepository.findByAvailableTrue(), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Item>>(itemRepository.findByAvailableFalse(), HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<Item> getItem(@PathVariable("id") String id) {
        return new ResponseEntity<Item>(itemRepository.findOne(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Item> saveOrder(@RequestBody Item item) {
		if (isLoggedIn()) {
            itemRepository.save(item);
			return new ResponseEntity<Item>(item, HttpStatus.OK);
		} else {
			return new ResponseEntity<Item>(HttpStatus.UNAUTHORIZED);
		}
	}
}
