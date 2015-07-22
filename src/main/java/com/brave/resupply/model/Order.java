package com.brave.resupply.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Set;

public class Order {
	@Id
	private String id;
	private Date date;
	private String userId;
    private Set<ItemRequest> requestedItems;
    private boolean filled = false;

	public Order() {
	}

	public Order(Date date, String userId, Set<ItemRequest> requestedItems) {
		this.date = date;
		this.userId = userId;
        this.requestedItems = requestedItems;
        this.filled = false;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<ItemRequest> getRequestedItems() {
        return requestedItems;
    }

    public void setRequestedItems(Set<ItemRequest> requestedItems) {
        this.requestedItems = requestedItems;
    }

    public void addItemRequest(ItemRequest item) {
        this.requestedItems.add(item);
    }

    public void removeItemRequest(ItemRequest item) {
        this.requestedItems.remove(item);
    }
    
    public boolean isFilled() {
    	return filled;
    }
    
    public void setFilled(boolean filled) {
    	this.filled = filled;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", userId=" + userId +
                ", requestedItems=" + requestedItems +
                ", filled=" + filled +
                '}';
    }
}
