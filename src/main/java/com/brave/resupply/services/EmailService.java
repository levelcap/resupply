package com.brave.resupply.services;

import com.brave.resupply.model.ItemRequest;
import com.brave.resupply.model.Order;
import com.brave.resupply.model.User;
import com.postmark.java.NameValuePair;
import com.postmark.java.PostmarkClient;
import com.postmark.java.PostmarkException;
import com.postmark.java.PostmarkMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public void sendReminderEmail(String userEmail) {
        List<NameValuePair> headers = new ArrayList<NameValuePair>();

        PostmarkMessage message = new PostmarkMessage("resupply-reminder@cloverfoodlab.com",
                userEmail,
                "resupply-reminder@cloverfoodlab.com",
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
            messageBody.append(itemRequest.getNumber()).append(" ").append(itemRequest.getSizeType()).append("(s) of ").append(itemRequest.getItem().getName()).append("\n");
        }
        PostmarkMessage message = new PostmarkMessage("dcohen@infinio.com",
                "cohen.davids@gmail.com",
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

}
