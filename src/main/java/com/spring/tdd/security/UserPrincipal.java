package com.spring.tdd.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.spring.tdd.model.User;

public class UserPrincipal implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private User user;
	
	public UserPrincipal(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(user.getRoles()!=null) {
			for(String role:user.getRoles().split(",")) {
				authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
			}
		}
		if(user.getAuthorities()!=null) {
			for(String authority:user.getAuthorities().split(",")) {
				authorities.add(new SimpleGrantedAuthority(authority));
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !user.isExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !user.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !user.isExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.isActive();
	}

}
