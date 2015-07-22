package com.brave.resupply.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brave.resupply.model.Order;
import com.brave.resupply.model.User;
import com.brave.resupply.repository.OrderRepository;
import com.brave.resupply.repository.UserRepository;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/api/order")
public class OrderController extends BaseController {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<List<Order>> getOrders() {
        User user = getCurrentUser();
        if (user.getRole() == User.UserRole.MANAGER) {
            return new ResponseEntity<List<Order>>(orderRepository.findAll(sortByDateDesc()), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Order>>(orderRepository.findByUserIdOrderByDateDesc(user.getId()), HttpStatus.OK);
        }
	}

    private Sort sortByDateDesc() {
        return new Sort(Sort.Direction.DESC, "date");
    }

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<Order> saveOrder(@RequestBody Order order) {
		if (isLoggedIn()) {
			User currentUser = getCurrentUser();
            Date date = new Date();
            order.setUserId(currentUser.getId());
            order.setDate(date);
            orderRepository.save(order);
			return new ResponseEntity<Order>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<Order>(HttpStatus.UNAUTHORIZED);
		}
	}
}
