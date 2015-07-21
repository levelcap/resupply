package com.brave.resupply.controller;

import com.brave.resupply.model.Order;
import com.brave.resupply.model.User;
import com.brave.resupply.repository.OrderRepository;
import com.brave.resupply.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/api/order")
public class OrderController extends BaseController {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<List<Order>> getOrders() {
        User user = getCurrentUser();
        if (user.getRole() == User.UserRole.MANAGER) {
            return new ResponseEntity<List<Order>>(orderRepository.findByDate(getTodayDateString()), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Order>>(orderRepository.findByUserIdAndDate(user.getId(), getTodayDateString()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<List<Order>> getAllOrders() {
        User user = getCurrentUser();
        if (user.getRole() == User.UserRole.MANAGER) {
            return new ResponseEntity<List<Order>>(orderRepository.findAll(sortByDateDesc()), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Order>>(orderRepository.findByUserIdOrderByDateDesc(user.getId()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<Order> getOrders(@PathVariable("id") String id) {
        User user = getCurrentUser();
        return new ResponseEntity<Order>(orderRepository.findOne(id), HttpStatus.OK);
    }

    private Sort sortByDateDesc() {
        return new Sort(Sort.Direction.DESC, "date");
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<Order> saveOrder(@RequestBody Order order) {
        if (isLoggedIn()) {
            User currentUser = getCurrentUser();
            order.setUserId(currentUser.getId());
            order.setDate(getTodayDateString());
            orderRepository.save(order);
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<Order>(HttpStatus.UNAUTHORIZED);
        }
    }

    private String getTodayDateString() {
        return format.format(new Date());
    }
}
