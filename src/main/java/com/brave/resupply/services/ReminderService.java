package com.brave.resupply.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.brave.resupply.model.Order;
import com.brave.resupply.model.User;
import com.brave.resupply.repository.OrderRepository;
import com.brave.resupply.repository.UserRepository;

@Service
public class ReminderService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReminderService.class);

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;

	@Scheduled(cron = "0 0 13 * * *")
	public void sendResupplyReminder() {
		LOGGER.info("Sending resupply reminders.");
		try {
			List<User> users = userRepository.findAll();
			List<User> missingResupplyUsers = new ArrayList<User>();
			
			for (User user : users) {
				//Check if this user has an entry for today
				List<Order> orders = orderRepository.findByUserIdOrderByDateDesc(user.getId());
				if (null != orders && orders.size() > 0) {
					//TODO: Check if there is a resupply order that has been saved for today
				}
			}
			
			for (User user : missingResupplyUsers) {
				//TODO: Send reminder email to those filthy delinquents
				emailService.sendReminderEmail(user.getEmail());
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
