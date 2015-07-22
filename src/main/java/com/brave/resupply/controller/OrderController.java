package com.brave.resupply.controller;

import com.brave.resupply.model.ItemRequest;
import com.brave.resupply.model.Order;
import com.brave.resupply.model.User;
import com.brave.resupply.repository.OrderRepository;
import com.brave.resupply.repository.UserRepository;
import com.brave.resupply.services.EmailService;
import com.brave.resupply.services.OrderService;
import com.brave.resupply.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<List<Order>> getOrders() {
        User user = getCurrentUser();
        List<Order> orders = new ArrayList<Order>();
        if (user.getRole() == User.UserRole.MANAGER) {
            orders = orderRepository.findByDate(DateUtil.getTodayDateString());
        } else {
            orders = orderRepository.findByUserIdAndDate(user.getId(), DateUtil.getTodayDateString());
            if (null == orders || orders.size() <= 0) {
                orders.add(orderService.getBlankOrder(user));
            }
        }

        for (Order order : orders) {
            User orderUser = userRepository.findOne(order.getUserId());
            order.setUser(orderUser);
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
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
            order.setDate(DateUtil.getTodayDateString());
            order.setSent(true);
            orderRepository.save(order);
            emailService.sendResupplyOrderEmail(order, currentUser);
            emailService.sendResupplyOrderConfirmationEmail(order, currentUser);
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<Order>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "saveManaged", method = RequestMethod.POST)
    public HttpEntity<List<Order>> saveOrder(@RequestBody List<Order> orders) {
        if (isLoggedIn()) {
            orderRepository.save(orders);
            return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Order>>(HttpStatus.UNAUTHORIZED);
        }
    }
}
