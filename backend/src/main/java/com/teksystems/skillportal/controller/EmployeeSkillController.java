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
	 * Param:- Skill_Name and Employee_EmailId
	 */

	@GetMapping("/getSubSkillsBySkill")
	public List<SubSkillDomain> getBySubSkillId(@RequestParam String empId,@RequestParam String skillName) throws ExecutionException {
        logger.info("/api/getSubSkillsBySkillId accessed");
		logger.debug("Paramater received : empId "+ empId+" skillId "+skillName );
		logger.info("Getting all unassigned subskills for emplyoyee "+empId);

		return employeeSkillService.getAllUnassignedSubSkills(empId,skillName);
	}



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

	@GetMapping("/getEmployeeSkills")
	public List<EmployeeSkillDomain> readAll(@RequestParam String empId) {
		logger.info("/api/getEmployeeSkills accessed");
		logger.debug("Paramater received : empid "+empId );
		logger.info("Employee skills fetched using id "+empId);
		return employeeSkillService.getAll(empId);
	}

//	@PostMapping("/update")
//	public EmployeeSkillDomain update(@RequestParam String empId,@RequestParam String subSkillId, @RequestParam int rating)throws Exception {
//		logger.info("/api/update accessed");
//		logger.debug("Paramater received : Empid "+empId +" subSkillid "+subSkillId+" rating "+rating);
//		logger.info("Rating for subskill "+subSkillId+" updated for employee "+ empId +" as "+rating);
//		if (rating <= 0 || rating > 5) {
//			throw new Exception("Rating Invalid"+ rating);
//		}
//		else
//		{
//			return employeeSkillService.update(empId, subSkillId, rating);
//		}
//	}
//
//	 @GetMapping("/getById")
//	 public EmployeeSkillDomain getById(@RequestParam String id) {
//         logger.info("/api/getById accessed");
//         logger.debug("Paramater received :  "+id );
//         logger.info("Employee skill fetched using object id "+id);
//           return employeeSkillService.getEmployeeSkillByObjectId(id);
//	 }
//


//	 @DeleteMapping("/delete")
//		public void getByEmpIdAndSubSkillId(@RequestParam("empId")String empId, @RequestParam("subSkillId")String subSkillId) {
//         logger.info("/api/delete accessed");
//         logger.debug("Paramater received : empid "+empId+" subSkillId "+subSkillId );
//         logger.info("Subskill "+subSkillId+ " deleted for emplyoee "+empId);
//			employeeSkillService.deleteSubSkill(empId,subSkillId);
//
//		}
//
//	 @GetMapping("/getSkillCount")
//  	 public int getbySkillIdCount(@RequestParam String skillId) {
//         logger.info("/api/getSkillCount accessed");
//         logger.debug("Paramater received : skillId "+skillId );
//         logger.info("Skill count fetched for skillid "+skillId);
//            return employeeSkillService.getSkillCount(skillId);
//  	 }
//
//	 @GetMapping("/getSubSkillCount")
//	 public int getbySubSkillIdCount(@RequestParam String subSkillId) {
//         logger.info("/api/getSubSkillCount accessed");
//         logger.debug("Paramater received : subSkillId "+subSkillId );
//         logger.info("Skill count fetched for skillid "+subSkillId);
//          return employeeSkillService.getSubSkillCount(subSkillId);
//	 }
//
//		@GetMapping("/getSubSkillsBySkillId")
//	 public List<SubSkillDomain> getBySkillIdExcept(@RequestParam String empId, @RequestParam String skillId) {
//        logger.info("/api/getSubSkillsBySkillId accessed");
//        logger.debug("Paramater received : empId "+ empId+" skillId "+skillId );
//        logger.info("Getting all unassigned subskills for emplyoyee "+empId);
//        return employeeSkillService.getAllUnassignedSubSkills(empId, skillId);
//	 }
//

		
	 

}
