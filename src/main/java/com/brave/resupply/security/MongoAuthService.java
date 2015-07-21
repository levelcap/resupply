package com.brave.resupply.security;

import com.brave.resupply.model.User;
import com.brave.resupply.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcohen on 9/2/14.
 */
@Service("authService")
public class MongoAuthService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoAuthService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = getUserDetail(email);
        ResupplyRole userDetail = new ResupplyRole(
                user.getEmail(), user.getPassword(), true, true, true, true,
                getAuthorities(1));
        userDetail.setId(user.getId());
        userDetail.setRole(1);
        httpSession.setAttribute("user", user);
        return userDetail;
    }

    public List<GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        if (role.intValue() == 1) {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authList;
    }

    public User getUserDetail(String email) {
        List<User> users = userRepository.findByEmailIgnoreCase(email);

        if (users.size() > 0) {
            return users.get(0);
        } else {
            LOGGER.error("Did not find user with email " + email);
            throw new UsernameNotFoundException("Did not find user with email " + email);
        }
    }
}
