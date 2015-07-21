package com.brave.chardb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brave.chardb.model.Location;
import com.brave.chardb.model.User;
import com.brave.chardb.repository.LocationRepository;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/api/location")
public class LocationController extends BaseController {

    @Autowired
    private LocationRepository locationRepository;

    @RequestMapping("/{id}")
    @ResponseBody
    public HttpEntity<Location> getLocation(@PathVariable("id") String id) {
        Location location = locationRepository.findOne(id);
        return new ResponseEntity<Location>(location, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<Location> saveLocation(@PathVariable("id") String id, @RequestBody Location location) {
        if (isLoggedIn()) {
            long currentTime = System.currentTimeMillis();
            location.setUpdated(currentTime);

        	if (StringUtils.isEmpty(location.getUrl())) {
        		location.setUrl("/images/blank.png");
        	}
        	location.setId(id);
            User currentUser = getCurrentUser();
            Location existingLocation = locationRepository.findOne(id);
            if (existingLocation != null) {
                if (existingLocation.getUserId().equals(currentUser.getId())) {
                    location = locationRepository.save(location);
                    return new ResponseEntity<Location>(location, HttpStatus.OK);
                } else {
                    return new ResponseEntity<Location>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                location.setUserId(currentUser.getId());
                location.setCreated(currentTime);
                location = locationRepository.save(location);
                return new ResponseEntity<Location>(location, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Location>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public HttpEntity<Location> deleteLocation(@PathVariable("id") String id) {
        if (isLoggedIn()) {
        	User currentUser = getCurrentUser();
            Location existingLocation = locationRepository.findOne(id);
            if (existingLocation != null) {
                if (existingLocation.getUserId().equals(currentUser.getId())) {
                    locationRepository.delete(id);
                    return new ResponseEntity<Location>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<Location>(HttpStatus.UNAUTHORIZED);
                }
            } else {
            	return new ResponseEntity<Location>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Location>(HttpStatus.UNAUTHORIZED);
        }
    }    
}
