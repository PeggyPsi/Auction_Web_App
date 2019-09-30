package com.ted.auction_app.Security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class Myauthtoken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	public Myauthtoken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public Myauthtoken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}
}