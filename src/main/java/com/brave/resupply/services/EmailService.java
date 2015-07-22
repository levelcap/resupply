package com.brave.resupply.services;

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
        headers.add(new NameValuePair("HEADER", "test"));

        PostmarkMessage message = new PostmarkMessage(userEmail,
                "resupply-reminder@cloverfoodlab.com",
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
        headers.add(new NameValuePair("HEADER", "test"));

        PostmarkMessage message = new PostmarkMessage("cohen.davids@gmail.com",
                "resupply-reminder@cloverfoodlab.com",
                "resupply-reminder@cloverfoodlab.com",
                null,
                order.getDate() + " Resupply Order Submitted by " + user.getEmail() + " at " + user.getLocation(),
                order.toString(),
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
