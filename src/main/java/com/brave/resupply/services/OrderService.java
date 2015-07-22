package com.brave.resupply.services;

import com.brave.resupply.model.Item;
import com.brave.resupply.model.ItemRequest;
import com.brave.resupply.model.Order;
import com.brave.resupply.model.User;
import com.brave.resupply.repository.ItemRepository;
import com.brave.resupply.util.DateUtil;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dcohen on 7/22/15.
 */
@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    ItemRepository itemRepository;

    public Order getBlankOrder(User user) {
        Order order = new Order();
        order.setId(new ObjectId().toString());
        order.setDate(DateUtil.getTodayDateString());
        order.setUserId(user.getId());
        order.setFilled(false);

        Set<ItemRequest> itemRequests = new HashSet<ItemRequest>();

        //populate all items
        List<Item> items = itemRepository.findByAvailableTrue();
        for (Item item: items) {
            ItemRequest itemRequest = new ItemRequest();
            itemRequest.setItem(item);
            itemRequest.setNumber(0);
            itemRequest.setSizeType(null);
            itemRequests.add(itemRequest);
        }

        order.setRequestedItems(itemRequests);

        return order;
    }

}
