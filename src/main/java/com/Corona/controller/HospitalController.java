package com.Corona.controller;



import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;



import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Corona.dao.HospitalRepository;
import com.Corona.dao.PatientRepository;
import com.Corona.entities.Hospital;
import com.Corona.entities.Patient;
import com.smart.helper.Message;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;













@Controller
@RequestMapping("/hospital")
public class HospitalController {
	
	private static final Integer PatientId = null;
	@Autowired
	private HospitalRepository HospitalRepository; 
	@Autowired
	private PatientRepository PatientRepository;
	
		//method for handing comman data for response
		@ModelAttribute
		  private void addCommonData(Model model,Principal principal) {
			
		
			String userName= principal.getName();
			
			
			System.out.println("USERNAME "+userName);
			
			//get the user using userName(Email)
			
			
			Hospital hospital = HospitalRepository.getHospitalByHospitalName(userName);
			
			System.out.println("HOSPITAL"+hospital);
			
			model.addAttribute("hospital",hospital);
			
			
			
			
			
		}
		
	//dashboard home
    @RequestMapping("/index")
	public String dashboard(Model model,Principal principal) 
    {
		return"normal/hospital_dashboard";
		}
    
    //open and from handler
    @GetMapping("/add-patient")
    public String openAddContactFrom(Model model) {
    	
    	model.addAttribute("title","Add patient");
    	model.addAttribute("patient",new Patient());
    	return "normal/add_patient_form";
    	
    	
    	
    	
    }
    
    
    
    //processing and contact form
    
    @PostMapping("/process-patient")
    public String processPatient(@ModelAttribute Patient patient,
    		@RequestParam("profileImage") MultipartFile file, 
    		Principal principal,HttpSession session) {
    	
    	try {
    		
		String name=principal.getName();
    	Hospital hospital=this .HospitalRepository.getHospitalByHospitalName(name);
    	
    	
    	
    	//processing and uploading file..
    	
    	
    	if(file.isEmpty())
    	{
    		
    		//if the file is empty then try our message
    		System.out.println("file is Empty");
    		patient.setImage("patient1.png");
    		
    		}
    	else {
    		
    		
    		//file the file to folder and update the name to Patient
    		patient.setImage(file.getOriginalFilename());
    		
    		
    		File saveFile=new ClassPathResource("static/img").getFile();
    		
    	Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
    		
    		Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
    	
    	System.out.println("Image is uploaded");
    	
    	
    	}
    	
    	
    	
    	
    	
    	patient.setHospital(hospital);
    	hospital.getPatients().add(patient);
    	
    	
    	
    	
    	
    	this.HospitalRepository.save(hospital);
    	
    	System.out.println("DATA "+patient);
    	System.out.println("added to the data bases");
    
    	//message success.......... 
    	
    	session.setAttribute("message", new Message("your patient is added !! And more..", "success"));
    	
    	}
       catch (Exception e) {
	
    	System.out.println("ERROR"+ e.getMessage());
    	e.printStackTrace();
    	
    	//message error
    	
    	session.setAttribute("message", new Message("Somthing went wrong!! try agin..", "danger"));
    	
	}
	
    return "normal/add_patient_form";
    	
    	
    }
    
  //show patients handler
    
    // per page= 7[n]
   //current page=0 [page]    
    
    
    @GetMapping("/show-patients/{page}")
    public String showPatient(@PathVariable("page") Integer page,Model m,Principal principal) {
    	m.addAttribute("title", "Show user Patients");
    	
    	
    	//patients list leni hai
    	String hospitalName=principal.getName();
    	
    	
    	Hospital hospital= this.HospitalRepository.getHospitalByHospitalName(hospitalName);
    	
  
    	org.springframework.data.domain.Pageable pageable= PageRequest.of(page, 7);
    	
    	
    	
    	Page<Patient> patients = this.PatientRepository.findPatientsByHospital(hospital.getId(),pageable);
    	
    	m.addAttribute("patients", patients);
    	m.addAttribute("currentPage",page);
    	
    	m.addAttribute("totalPages",patients.getTotalPages());
    	
    	 /*public String showPatient(Model m,Principal principal) {
		
		 * String userName = principal.getName();
		 * 
		 * Hospital
		 * hospital=this.HospitalRepository.getHospitalByHospitalName(userName);
		 * 
		 * List<Patient>patients = hospital.getPatients();
		 * 
		 * this.HospitalRepository.getHospitalByHospitalName(userName);
		 */
    	
    	return"normal/show_Patients";
    	
    }
    	
    // delete patient handler
    
    @GetMapping("/delete/{PatientId}")
    public String deletePatient(@PathVariable("PatientId") Integer PatientId,Model model,HttpSession session) {
    	
    	
    	
    	java.util.Optional<Patient> patientOptional= this.PatientRepository.findById(PatientId);
    
		
	 Patient Patient = patientOptional.get();
    	
    	
    	// check assignment.....
    	
    	
    
    	
    	
    	
         Patient.setHospital(null);
    	
    	this.PatientRepository.delete(Patient);
    	 
    
    	session.setAttribute("message", new Message("Patient deleted successfully","success"));
    	
    	
    	
    	return "redirect:/hospital/show-patients/0";
    	
    	
    	
    }
   
    	// open update form handler
    
    @PostMapping("/update-patient/{PatientId}")
    
    public String updateForm(@PathVariable("PatientId") Integer PatientId,Model m) 
    
    {
    	
    m.addAttribute("title", "Update Patient");
    	
	
	  Patient patient= this.PatientRepository.findById(PatientId).get();
	  
	  m.addAttribute("patient",patient);
	 
    return "normal/update_form";
    	
    	
    	
    	
    	
    }
    
    //showing particular patient details
    
    
    
    @RequestMapping("/{PatientId}/patient")
    public String showPatientDetail(@PathVariable("PatientId") Integer PatientId, Model model) {
    	
    	System.out.println("PATIENTID"+ PatientId);
    	
    	
    	
    	java.util.Optional<Patient> patientOptional=this.PatientRepository.findById(PatientId);
    	
       Patient patient=patientOptional.get();
    	
       model.addAttribute("patient", patient);
       
       
       
       
		return "normal/patient_detail";
    	
    	
    	
    	
    	
    	
    }
    
    
    
    
    	
    }
    
	

