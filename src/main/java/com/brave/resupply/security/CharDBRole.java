package com.brave.resupply.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CharDBRole extends User {
	private static final long serialVersionUID = -7379590624723451244L;
	private String id;
	private int role;
	
	public CharDBRole(String email, String password, boolean enabled,
                      boolean accountNonExpired, boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities) {
		super(email, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public int getRole() {
    	return role;
    }
    
    public void setRole(int role) {
    	this.role = role;
    }

}
