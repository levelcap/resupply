package com.brave.chardb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brave.chardb.enums.Genre;
import com.brave.chardb.enums.TimePeriod;
import com.brave.chardb.model.Character;
import com.brave.chardb.model.Location;
import com.brave.chardb.model.Setting;
import com.brave.chardb.model.User;
import com.brave.chardb.repository.CharacterRepository;
import com.brave.chardb.repository.LocationRepository;
import com.brave.chardb.repository.SettingRepository;
import com.brave.chardb.repository.UserRepository;
import com.brave.chardb.services.FeaturedService;
import com.brave.chardb.services.UserService;

/**
 * Created by dcohen on 2/13/15.
 */
@Controller
public class PageController extends BaseController {
	@Autowired
	private CharacterRepository characterRepository;
	
	@Autowired
	private SettingRepository settingRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	FeaturedService featuredService;
	
	@Autowired
	UserService userService;

	@RequestMapping("/")
	public String index(Model model) {
		Character character = featuredService.getFeaturedCharacter();
		model.addAttribute("character", character);
		model.addAttribute("loggedIn", isLoggedIn());
		return "index";
	}

	@RequestMapping("/new")
	public String newCharacter(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			Character character = new Character();
			character.setId(new ObjectId().toString());
			character.setUserId(getCurrentUser().getId());
			character.setUrl("/images/blank.png");
			model.addAttribute("character", character);
			model.addAttribute("title", "Character Center - New Character");
			return "edit";
		} else {
			return "login";
		}
	}
	
	@RequestMapping("/newLocation")
	public String newLocation(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			Location location = new Location();
			location.setId(new ObjectId().toString());
			location.setUserId(getCurrentUser().getId());
			location.setUrl("/images/blank.png");
			model.addAttribute("location", location);
			model.addAttribute("title", "Character Center - New Location");
			return "edit";
		} else {
			return "login";
		}
	}
	
	@RequestMapping("/newSetting")
	public String newSetting(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			Setting setting = new Setting();
			setting.setId(new ObjectId().toString());
			setting.setUserId(getCurrentUser().getId());
			setting.setUrl("/images/blank.png");
			model.addAttribute("setting", setting);
			model.addAttribute("title", "Character Center - New Setting");
			return "edit";
		} else {
			return "login";
		}
	}


	@RequestMapping("/char/{id}")
	public String getCharacter(@PathVariable("id") String id, Model model) {
		Character character = characterRepository.findOne(id);
		model.addAttribute("loggedIn", isLoggedIn());
		model.addAttribute("character", character);
		model.addAttribute("title", "Character Center - " + character.getName());
		return "char";
	}
	
	@RequestMapping("/char/{id}/edit")
	public String edit(@PathVariable("id") String id, Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			User currentUser = getCurrentUser();
			Character existingCharacter = characterRepository.findOne(id);
			if (existingCharacter == null) {
				return index(model);
			} else if (!existingCharacter.getUserId().equals(
					currentUser.getId())) {
				model.addAttribute("character", existingCharacter);
				model.addAttribute("title", "Character Center - "
						+ existingCharacter.getName());
				return "char";
			}
			model.addAttribute("character", existingCharacter);
			model.addAttribute("title", "Character Center - Editing "
					+ existingCharacter.getName());
			return "edit";
		}
		return "login";
	}
	
	@RequestMapping("/s/{id}")
	public String getSetting(@PathVariable("id") String id, Model model) {
		Setting setting = settingRepository.findOne(id);
		model.addAttribute("loggedIn", isLoggedIn());
		model.addAttribute("setting", setting);
		model.addAttribute("title", "Character Center - " + setting.getName());
		return "char";
	}
	
	@RequestMapping("/s/{id}/edit")
	public String editSetting(@PathVariable("id") String id, Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			User currentUser = getCurrentUser();
			Setting existingSetting = settingRepository.findOne(id);
			if (existingSetting == null) {
				return index(model);
			} else if (!existingSetting.getUserId().equals(
					currentUser.getId())) {
				model.addAttribute("setting", existingSetting);
				model.addAttribute("title", "Character Center - "
						+ existingSetting.getName());
				return "char";
			}
			model.addAttribute("setting", existingSetting);
			model.addAttribute("title", "Character Center - Editing "
					+ existingSetting.getName());
			return "edit";
		}
		return "login";
	}
	
	@RequestMapping("/l/{id}")
	public String getLocation(@PathVariable("id") String id, Model model) {
		Location location = locationRepository.findOne(id);
		model.addAttribute("loggedIn", isLoggedIn());
		model.addAttribute("location", location);
		model.addAttribute("title", "Character Center - " + location.getName());
		return "char";
	}
	
	@RequestMapping("/l/{id}/edit")
	public String editLocation(@PathVariable("id") String id, Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			User currentUser = getCurrentUser();
			Location existingLocation = locationRepository.findOne(id);
			if (existingLocation == null) {
				return index(model);
			} else if (!existingLocation.getUserId().equals(
					currentUser.getId())) {
				model.addAttribute("location", existingLocation);
				model.addAttribute("title", "Character Center - "
						+ existingLocation.getName());
				return "char";
			}
			model.addAttribute("location", existingLocation);
			model.addAttribute("title", "Character Center - Editing "
					+ existingLocation.getName());
			return "edit";
		}
		return "login";
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		model.addAttribute("title", "Character Center - Login");
		return "login";
	}

	@RequestMapping("/user")
	public String getLoggedInUser(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		if (isLoggedIn()) {
			User user = getCurrentUser();
			if (StringUtils.isEmpty(user.getUsername())) {
				user.setUsername(user.getId());
			}
			model.addAttribute("edit", true);
			model.addAttribute("user", user);
			model.addAttribute("title", "Character Center -  User");
			model.addAttribute("characterHeader", "Your Characters");
			model.addAttribute("locationsHeader", "Your Locations");
			model.addAttribute("settingsHeader", "Your Settings");
			addCharactersToModel(model, getCurrentUser());
			addLocationsToModel(model, getCurrentUser());
			addSettingsToModel(model, getCurrentUser());
			return "user";
		} else {
			return "login";
		}
	}

	@RequestMapping("/user/{id}")
	public String getUser(@PathVariable("id") String id, Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		User user = userRepository.findOne(id);
		if (user == null) {
			user = userService.getByUsername(id);
		}
		
		if (user == null) {
			return index(model);
		}
		
		model.addAttribute("edit", false);
		model.addAttribute("user", user);
		model.addAttribute("title", "Character Center -  User");
		model.addAttribute("characterHeader", user.getUsername() + "'s Characters");
		model.addAttribute("locationsHeader", user.getUsername() + "'s Locations");
		model.addAttribute("settingsHeader", user.getUsername() + "'s Settings");
		addCharactersToModel(model, user);
		addLocationsToModel(model, user);
		addSettingsToModel(model, user);
		return "user";
	}

	@RequestMapping("/register")
	public String registrationPage(Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		return "register";
	}

	@RequestMapping("/c/{id}")
	public String characterShortcut(@PathVariable("id") String id, Model model) {
		return getCharacter(id, model);
	}

	@RequestMapping("/u/{id}")
	public String userShortcut(@PathVariable("id") String id, Model model) {
		return getUser(id, model);
	}

	@RequestMapping("/browse/{cat}")
	public String browseByGenre(@PathVariable("cat") String cat, Model model) {
		model.addAttribute("loggedIn", isLoggedIn());
		Map<String, List<Character>> catMap = new HashMap<String, List<Character>>();
		if (cat.equals("genre")) {
			populateGenreMap(catMap);
			model.addAttribute("browseCriteria", "Browse by Genre");
		} else if (cat.equals("time")) {
			populateTimePeriodMap(catMap);
			model.addAttribute("browseCriteria", "Browse by Time Period");
		}
		model.addAttribute("title", "Character Center - Browse");
		model.addAttribute("catMap", catMap);
		return "browse";
	}

    @RequestMapping("/quest")
    public String questPage(Model model) {
        if (isLoggedIn()) {
            model.addAttribute("loggedIn", isLoggedIn());
            return "quest";
        } else {
            return index(model);
        }
    }

	private void addCharactersToModel(Model model, User user) {
		model.addAttribute("characters",
				characterRepository.findByUserId(user.getId()));
	}
	
	private void addLocationsToModel(Model model, User user) {
		model.addAttribute("locations",
				locationRepository.findByUserId(user.getId()));
	}
	
	private void addSettingsToModel(Model model, User user) {
		model.addAttribute("settings",
				settingRepository.findByUserId(user.getId()));
	}

	private void populateGenreMap(Map<String, List<Character>> catMap) {
		for (Genre genre : Genre.values()) {
			List<Character> characters = characterRepository.findByGenre(genre);
			if (!CollectionUtils.isEmpty(characters)) {
				catMap.put(genre.toString(), characters);
			}
		}
	}

	private void populateTimePeriodMap(Map<String, List<Character>> catMap) {
		for (TimePeriod timePeriod : TimePeriod.values()) {
			List<Character> characters = characterRepository
					.findByTimePeriod(timePeriod);
			if (!CollectionUtils.isEmpty(characters)) {
				catMap.put(timePeriod.toString(), characters);
			}
		}
	}
}
