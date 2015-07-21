package com.brave.resupply.services;

import com.postmark.java.NameValuePair;
import com.postmark.java.PostmarkClient;
import com.postmark.java.PostmarkException;
import com.postmark.java.PostmarkMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcohen on 7/21/15.
 */
@Component
public class EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private static String serverToken;
    static {
        serverToken = System.getenv("POSTMARK_API_TOKEN");
    }

    public void sendMail() {
        List<NameValuePair> headers = new ArrayList<NameValuePair>();

        headers.add(new NameValuePair("HEADER", "test"));

        PostmarkMessage message = new PostmarkMessage("TO_ADDRESS",
                "FROM_ADDRESS",
                "REPLY_TO_ADDRESS",
                null,
                "SUBJECT",
                "CONTENT",
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
