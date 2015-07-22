package com.brave.resupply.services;

import com.brave.resupply.model.Order;
import com.brave.resupply.model.User;
import com.brave.resupply.repository.OrderRepository;
import com.brave.resupply.repository.UserRepository;
import com.brave.resupply.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

	@Scheduled(cron = "0 0 13 * * *", zone="America/New_York")
	public void sendResupplyReminder() {
		LOGGER.info("Sending resupply reminders.");
		try {
			List<User> users = userRepository.findAll();
			List<User> missingResupplyUsers = new ArrayList<User>();
			
			for (User user : users) {
				//Check if this user has an entry for today
				List<Order> orders = orderRepository.findByUserIdAndDate(user.getId(), DateUtil.getTodayDateString());
				if (null == orders || orders.size() > 0) {
					missingResupplyUsers.add(user);
				} else if (!orders.get(0).isSent()) {
                    missingResupplyUsers.add(user);
                }
			}
			
			for (User user : missingResupplyUsers) {
				emailService.sendReminderEmail(user.getEmail());
			}
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
}
