package com.teksystems.skillportal.controller;


import java.util.List;

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
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.EmployeeSkillService;


@RestController
@RequestMapping(value="/skill",method= {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin("*")
public class EmployeeSkillController {

	private static Logger logger = Logger.getLogger(EmployeeSkillController.class);

	@Autowired
	 private EmployeeSkillService employeeSkillService;
	
	 
	@PostMapping("/add")
	 public void add(@RequestParam String empId,@RequestParam String subSkillId, @RequestParam int rating) {
        logger.info("/api/add accessed");
	    logger.debug("Paramater received : Empid "+empId +" subSkillid "+subSkillId+" rating "+rating);
	     employeeSkillService.addNew(empId,subSkillId,rating);
		logger.info("New subskill " + subSkillId +" with rating "+ rating +" added for " + empId);

	 }
	
	 @PostMapping("/update")
	 public EmployeeSkillDomain update(@RequestParam String empId,@RequestParam String subSkillId, @RequestParam int rating) {
         logger.info("/api/update accessed");
         logger.debug("Paramater received : Empid "+empId +" subSkillid "+subSkillId+" rating "+rating);
         logger.info("Rating for subskill "+subSkillId+" updated for employee "+ empId +" as "+rating);
	     return employeeSkillService.update(empId,subSkillId,rating);

	 }
	 
	 @GetMapping("/getById")
	 public EmployeeSkillDomain getById(@RequestParam String id) {
         logger.info("/api/getById accessed");
         logger.debug("Paramater received :  "+id );
         logger.info("Employee skill fetched using object id "+id);
           return employeeSkillService.getEmployeeSkillByObjectId(id);
	 }
	 
	 @GetMapping("/getEmployeeSkills")
	 public List<EmployeeSkillDomain> readAll(@RequestParam String empId) {
         logger.info("/api/getEmployeeSkills accessed");
         logger.debug("Paramater received : empid "+empId );
         logger.info("Employee skills fetched using id "+empId);
           return employeeSkillService.getAll(empId);    
		 }
	 
	 @DeleteMapping("/delete")
		public void getByEmpIdAndSubSkillId(@RequestParam("empId")String empId, @RequestParam("subSkillId")String subSkillId) {
         logger.info("/api/delete accessed");
         logger.debug("Paramater received : empid "+empId+" subSkillId "+subSkillId );
         logger.info("Subskill "+subSkillId+ " deleted for emplyoee "+empId);
			employeeSkillService.deleteSubSkill(empId,subSkillId);

		}
	 
	 @GetMapping("/getSkillCount")
  	 public int getbySkillIdCount(@RequestParam String skillId) {
         logger.info("/api/getSkillCount accessed");
         logger.debug("Paramater received : skillId "+skillId );
         logger.info("Skill count fetched for skillid "+skillId);
            return employeeSkillService.getSkillCount(skillId);
  	 }
	 
	 @GetMapping("/getSubSkillCount")
	 public int getbySubSkillIdCount(@RequestParam String subSkillId) {
         logger.info("/api/getSubSkillCount accessed");
         logger.debug("Paramater received : subSkillId "+subSkillId );
         logger.info("Skill count fetched for skillid "+subSkillId);
          return employeeSkillService.getSubSkillCount(subSkillId);
	 }
	 
		@GetMapping("/getSubSkillsBySkillId")
	 public List<SubSkillDomain> getBySkillIdExcept(@RequestParam String empId, @RequestParam String skillId) {
        logger.info("/api/getSubSkillsBySkillId accessed");
        logger.debug("Paramater received : empId "+ empId+" skillId "+skillId );
        logger.info("Getting all unassigned subskills for emplyoyee "+empId);
        return employeeSkillService.getAllUnassignedSubSkills(empId, skillId);
	 }
		
		@GetMapping("/getTotalSkillsRated")
		public int getTotalSkillRatedByUser(@RequestParam String empId){
			logger.info("/api/getTotalSkillRatedByUser");
			int count;
			if(!empId.isEmpty()) {
				logger.debug("Paramater received : empId " + empId);
				logger.info("Getting Total Number of skills rated for emplyoyee " + empId);
				count = employeeSkillService.getTotalSkillRatedbyUser(empId);
			}else{
				logger.debug("Employee Id Not Received");
				count =0;
			}
			return count;
		}

		@GetMapping("/getHighestRatedSkill")
		public String getHighestRatedSkillByUser(@RequestParam String empId ){
			logger.info("/api/getHighestRatedByUser");
			String highestRated;
			if(!empId.isEmpty()){
				logger.debug("Paramater received : empId " + empId);
				logger.info("Getting Total Number of skills rated for emplyoyee " + empId);
				highestRated = employeeSkillService.getHighestRatedSkillByUser(empId);
			}else{
				logger.debug("Employee Id Not Received");
				highestRated = null;
			}

			return highestRated;
		}

		@GetMapping("/getHighestRating")
	    public int getHighestRatingOfEmployee(@RequestParam String empId){
		    logger.info("/api/getHighestRating");
		    int highestRating;
		    if(!empId.isEmpty()){
	            logger.debug("Paramater received : empId " + empId);
	            logger.info("Getting Highest Rating of the Employee " + empId);
	            highestRating = employeeSkillService.getHighestRatingOfEmployee(empId);
	        }else{
	            logger.debug("Employee Id Not Received");
	            highestRating = 0;
	        }
	        return highestRating;
	    }

		@GetMapping("/getLastUpdatedOfEmployee")
		public int[] getLastUpdatedOfEmployee(@RequestParam String empId){
			logger.info("/api/getHighestRatedByUser");
			int[] lastUpdated = new int[3];
			if(!empId.isEmpty()){
				logger.debug("Paramater received : empId " + empId);
				logger.info("Getting Total Number of skills rated for emplyoyee " + empId);
				lastUpdated = employeeSkillService.getLastUpdatedOfEnployee(empId);
			}else{
				logger.debug("Employee Id Not Received, Nothing Processed");

			}

			return lastUpdated;
		}
		
		
	 

}
