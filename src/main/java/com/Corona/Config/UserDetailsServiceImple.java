package com.Corona.Config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.Corona.dao.HospitalRepository;
import com.Corona.entities.Hospital;

public class UserDetailsServiceImple implements UserDetailsService {
	
    @Autowired 
	private HospitalRepository hospitalRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//fetching user from database
		
		
		Hospital hospital=hospitalRepository.getHospitalByHospitalName(username);
		
		if(hospital==null) 
		{
				
		throw new UsernameNotFoundException("Could not found user!!");
		
		}
		
		 CustomUserDeatils customuserDetails=new CustomUserDeatils(hospital);
			
			return customuserDetails;
			
			
		}
		
		
	}


