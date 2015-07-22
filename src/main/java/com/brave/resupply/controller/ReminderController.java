package com.brave.resupply.controller;

import com.brave.resupply.model.Item;
import com.brave.resupply.services.ReminderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/api/reminder")
public class ReminderController extends BaseController {
	@Autowired
	private ReminderService reminderService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<String> sendReminder() {
        reminderService.sendUnfilledOrderReminder();
        return new ResponseEntity<String>("Yay", HttpStatus.OK);
	}
}
