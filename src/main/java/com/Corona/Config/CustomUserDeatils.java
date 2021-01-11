package com.Corona.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Corona.entities.Hospital;

public class CustomUserDeatils implements UserDetails {
	
	 private Hospital hospital;
	
	 public CustomUserDeatils(Hospital hospital) {
		super();
		this.hospital = hospital;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		
		
		SimpleGrantedAuthority  simpleGrantedAuthority=new SimpleGrantedAuthority(hospital.getRole());
		
		return  Arrays.asList(simpleGrantedAuthority);
	}


	@Override
	public String getPassword() {
		
	return hospital.getPassword();
	}

	@Override
	public String getUsername() {
		
		return hospital.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
