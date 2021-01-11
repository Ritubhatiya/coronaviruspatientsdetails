package com.Corona.dao;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Corona.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>  {

//pagination...
	
	@Query("from Patient as P where P.hospital.id =:hospitalId")
	//current page
	//patient per page
	public Page<Patient> findPatientsByHospital(@Param("hospitalId")int hospitalId,Pageable pePageable);




}






