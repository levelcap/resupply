package com.brave.resupply.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

/**
 * Created by dcohen on 9/2/14.
 */

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Resource(name = "authService")
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().anonymous()
                .and()
                	.logout()
                	.logoutUrl("/logout")
                	.logoutSuccessUrl("/")                	
                .and()
                	.formLogin()
                	.loginPage("/login")
                	.defaultSuccessUrl("/user")
                	.permitAll();
    }
}
