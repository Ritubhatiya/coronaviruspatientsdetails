package com.Corona.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Corona.entities.Hospital;

public interface HospitalRepository  extends JpaRepository<Hospital,Integer>{

	@Query("select h from Hospital h where h.email = :email ")
	
	public Hospital getHospitalByHospitalName(@Param("email") String email);
	
	
	
	
	
}
