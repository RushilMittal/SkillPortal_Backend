package com.teksystems.skillportal.controller;


import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.EmployeeSkillPlaceholderDomain;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.EmployeeSkillService;


@RestController
@RequestMapping(value="/skill",method= {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin("*")
public class EmployeeSkillController {

	private static Logger logger = Logger.getLogger(EmployeeSkillController.class);

	@Autowired
	 private EmployeeSkillService employeeSkillService;

	/*
	 * For Fetching the SubSkill Domain of a particular Employee
	 * UI:- For displaying in "My Skill"
	 * Param:- Skill_Name and Employee_EmailId
	 * Integration Testing Done :- 	11-04-2018
	 */

	@GetMapping("/getSubSkillsBySkill")
	public List<SubSkillDomain> getBySubSkillId(@RequestParam String empId,@RequestParam String skillName) throws ExecutionException {
        logger.info("/api/getSubSkillsBySkillId accessed");
		logger.debug("Paramater received : empId "+ empId+" skillId "+skillName );
		logger.info("Getting all unassigned subskills for emplyoyee "+empId);

		return employeeSkillService.getAllUnassignedSubSkills(empId,skillName);
	}

    /*
     * For adding the Rating of a skill of a particular Employee
     * UI:- to save a rating from Rating Component
     * Param:- empId(Employee ID of the Employee), subSkillId, rating(Rating selected on the UI)
     * Integration Testing Done :- 	11-04-2018
     */

	@PostMapping("/add")
	public void add(@RequestParam String empId,@RequestParam String subSkillId, @RequestParam int rating) throws Exception {
		System.out.println("in add");
		logger.info("/api/add accessed");
		logger.debug("Paramater received : Empid "+empId +" subSkillid "+subSkillId+" rating "+rating);
		if (rating <= 0 || rating > 5) {
			throw new Exception("Rating Invalid"+ rating);
		}
		else {
			employeeSkillService.addNew(empId, subSkillId, rating);
		}
		logger.info("New subskill " + subSkillId +" with rating "+ rating +" added for " + empId);
	}

    /*
     * For getting the EmployeeSkillPlaceholder for Dashboard of a particular Employee
     * Param:- empId (Employee ID of the Employee)
     * Integration Testing Done :- 	11-04-2018
     */
	@GetMapping("/getEmployeeSkillPlaceholder")
	public EmployeeSkillPlaceholderDomain getEmployeeSkillPlaceholder(@RequestParam String empId){
		logger.info("/api/getEmployeeSkillPlaceholder");
		EmployeeSkillPlaceholderDomain toReturn = null;
		if(!empId.isEmpty()){
			logger.debug("Paramater received : empId " + empId);
			logger.info("Getting detail for the Skill Placeholder " + empId);
			toReturn = employeeSkillService.getEmployeeSkillPlaceHolderDomain(empId);
		}else{
			logger.debug("Employee Id Not Received, Nothing Processed");
		}
		return toReturn;
	}

    /*
     * For getting all the Employee Skills
     * Param:- empId (Employee ID of the Employee)
     * Integration Testing Done :- 	11-04-2018
     */
	@GetMapping("/getEmployeeSkills")
	public List<EmployeeSkillDomain> getEmployeeSkills(@RequestParam String empId) {
		logger.info("/api/getEmployeeSkills accessed");
		logger.debug("Paramater received : empid "+empId );
		logger.info("Employee skills fetched using id "+empId);
		return employeeSkillService.getAll(empId);
	}


		
	 

}
