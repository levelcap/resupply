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

import com.brave.chardb.model.Setting;
import com.brave.chardb.model.User;
import com.brave.chardb.repository.SettingRepository;

/**
 * Created by dcohen on 2/11/15.
 */

@RestController
@RequestMapping("/api/setting")
public class SettingController extends BaseController {

    @Autowired
    private SettingRepository settingRepository;

    @RequestMapping("/{id}")
    @ResponseBody
    public HttpEntity<Setting> getSetting(@PathVariable("id") String id) {
        Setting setting = settingRepository.findOne(id);
        return new ResponseEntity<Setting>(setting, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<Setting> saveSetting(@PathVariable("id") String id, @RequestBody Setting setting) {
        if (isLoggedIn()) {
            long currentTime = System.currentTimeMillis();
            setting.setUpdated(currentTime);

        	if (StringUtils.isEmpty(setting.getUrl())) {
        		setting.setUrl("/images/blank.png");
        	}
        	setting.setId(id);
            User currentUser = getCurrentUser();
            Setting existingSetting = settingRepository.findOne(id);
            if (existingSetting != null) {
                if (existingSetting.getUserId().equals(currentUser.getId())) {
                    setting = settingRepository.save(setting);
                    return new ResponseEntity<Setting>(setting, HttpStatus.OK);
                } else {
                    return new ResponseEntity<Setting>(HttpStatus.UNAUTHORIZED);
                }
            } else {
                setting.setUserId(currentUser.getId());
                setting.setCreated(currentTime);
                setting = settingRepository.save(setting);
                return new ResponseEntity<Setting>(setting, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<Setting>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public HttpEntity<Setting> deleteSetting(@PathVariable("id") String id) {
        if (isLoggedIn()) {
        	User currentUser = getCurrentUser();
            Setting existingSetting = settingRepository.findOne(id);
            if (existingSetting != null) {
                if (existingSetting.getUserId().equals(currentUser.getId())) {
                    settingRepository.delete(id);
                    return new ResponseEntity<Setting>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<Setting>(HttpStatus.UNAUTHORIZED);
                }
            } else {
            	return new ResponseEntity<Setting>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<Setting>(HttpStatus.UNAUTHORIZED);
        }
    }    
}
