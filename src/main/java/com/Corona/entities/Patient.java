package com.Corona.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PATIENT")
public class Patient {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int PatientId;
	private String name;
	private String opertion;
	private String email;
	private String phone;
	private String image;
	@Column(length=1000)
	private String description;
	
	@ManyToOne
	private Hospital hospital;
	
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPatientId() {
		return PatientId;
	}

	public void setPatientId(int patientId) {
		PatientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpertion() {
		return opertion;
	}

	public void setOpertion(String opertion) {
		this.opertion = opertion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	

	/*
	 * @Override public String toString() { return "Patient [PatientId=" + PatientId
	 * + ", name=" + name + ", opertion=" + opertion + ", email=" + email +
	 * ", phone=" + phone + ", image=" + image + ", description=" + description +
	 * ", hospital=" + hospital + "]"; }
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
