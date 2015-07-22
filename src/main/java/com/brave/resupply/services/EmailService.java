package com.brave.resupply.services;

import com.brave.resupply.model.ItemRequest;
import com.brave.resupply.model.Order;
import com.brave.resupply.model.User;
import com.brave.resupply.repository.UserRepository;
import com.postmark.java.NameValuePair;
import com.postmark.java.PostmarkClient;
import com.postmark.java.PostmarkException;
import com.postmark.java.PostmarkMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcohen on 7/21/15.
 */
@Service
public class EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private static String serverToken;
    static {
        serverToken = System.getenv("POSTMARK_API_TOKEN");
    }

    @Autowired
    UserRepository userRepository;

    public void sendReminderEmail(String userEmail) {
        List<NameValuePair> headers = new ArrayList<NameValuePair>();

        PostmarkMessage message = new PostmarkMessage("dcohen@infinio.com",
                userEmail,
                "dcohen@infinio.com",
                null,
                "Resupply Reminder",
                "You have not yet submitted a resupply request today.  Try to get it in before 2PM!",
                false,
                null,
                headers);

        PostmarkClient client = new PostmarkClient(serverToken);

        try {
            client.sendMessage(message);
        } catch (PostmarkException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void sendResupplyOrderEmail(Order order, User user) {
        List<NameValuePair> headers = new ArrayList<NameValuePair>();
        StringBuffer messageBody = new StringBuffer();
        messageBody.append("Resupply Order for ").append(user.getLocation()).append(" submitted by ").append(user.getEmail()).append("\n\n");

        for (ItemRequest itemRequest : order.getRequestedItems()) {
            if (itemRequest.getNumber() > 0 && itemRequest.getSizeType() != null) {
                messageBody.append(itemRequest.getNumber()).append(" ").append(itemRequest.getSizeType()).append("(s) of ").append(itemRequest.getItem().getName()).append("\n");
            }
        }
        PostmarkMessage message = new PostmarkMessage("dcohen@infinio.com",
                user.getEmail(),
                "dcohen@infinio.com",
                null,
                order.getDate() + " Resupply Order Submitted by " + user.getEmail() + " at " + user.getLocation(),
                messageBody.toString(),
                false,
                null,
                headers);

        PostmarkClient client = new PostmarkClient(serverToken);

        try {
            client.sendMessage(message);
        } catch (PostmarkException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void sendResupplyOrderConfirmationEmail(Order order, User user) {
        List<NameValuePair> headers = new ArrayList<NameValuePair>();
        StringBuffer messageBody = new StringBuffer();
        messageBody.append("You have submitted the following order for resupply.").append("\n\n");

        for (ItemRequest itemRequest : order.getRequestedItems()) {
            if (itemRequest.getNumber() > 0 && itemRequest.getSizeType() != null) {
                messageBody.append(itemRequest.getNumber()).append(" ").append(itemRequest.getSizeType()).append("(s) of ").append(itemRequest.getItem().getName()).append("\n");
            }
        }
        PostmarkMessage message = new PostmarkMessage("dcohen@infinio.com",
                user.getEmail(),
                "dcohen@infinio.com",
                null,
                order.getDate() + " You have submitted an order for resupply",
                messageBody.toString(),
                false,
                null,
                headers);

        PostmarkClient client = new PostmarkClient(serverToken);

        try {
            client.sendMessage(message);
        } catch (PostmarkException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void sendResupplyOrderFilledEmail(Order order) {
        List<NameValuePair> headers = new ArrayList<NameValuePair>();
        StringBuffer messageBody = new StringBuffer();
        messageBody.append("Your resupply order has been filled: ").append("\n\n");
        User user = userRepository.findOne(order.getUserId());

        for (ItemRequest itemRequest : order.getRequestedItems()) {
            if (itemRequest.getNumber() > 0 && itemRequest.getSizeType() != null) {
                messageBody.append(itemRequest.getNumber()).append(" ").append(itemRequest.getSizeType()).append("(s) of ").append(itemRequest.getItem().getName()).append("\n");
            }
        }
        PostmarkMessage message = new PostmarkMessage("dcohen@infinio.com",
                user.getEmail(),
                "dcohen@infinio.com",
                null,
                order.getDate() + " resupply order has been filled",
                messageBody.toString(),
                false,
                null,
                headers);

        PostmarkClient client = new PostmarkClient(serverToken);

        try {
            client.sendMessage(message);
        } catch (PostmarkException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
