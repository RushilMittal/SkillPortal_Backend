package com.teksystems.skillportal.controller;


import java.util.List;
import java.util.concurrent.ExecutionException;

import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.EmployeeSkillPlaceholderDomain;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.EmployeeSkillService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value="/skill",method= {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin()
public class EmployeeSkillController {

	private static Logger logger = Logger.getLogger(EmployeeSkillController.class);

	@Autowired
	 private EmployeeSkillService employeeSkillService;
    @Autowired
    private TokenValidationService tokenValidator;

	/*
	 * For Fetching the SubSkill Domain of a particular Employee
	 * UI:- For displaying in "My Skill"
	 * Param:- Skill_Name and Employee_EmailId
	 * Integration Testing Done :- 	11-04-2018
	 * EmployeeId from Authorization token Done :- 14-04-2018
	 */

	@GetMapping("/getSubSkillsBySkill")
	public List<SubSkillDomain> getBySubSkillId(HttpServletRequest request,@RequestParam String skillName) throws ExecutionException {
        logger.info("/api/getSubSkillsBySkillId accessed");
        logger.debug("Paramater received : SkillName "+skillName );
        List<SubSkillDomain> toReturn = null;
        String employeeId = null;
        try{
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if(!( ((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                toReturn = employeeSkillService.getAllUnassignedSubSkills(employeeId,skillName);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        }catch(Exception e){
            logger.info("Some error occured" + e.toString());
            logger.error(e.getMessage());
        }
        return toReturn;
	}

    /*
     * For adding the Rating of a skill of a particular Employee
     * UI:- to save a rating from Rating Component
     * Param:- empId(Employee ID of the Employee), subSkillId, rating(Rating selected on the UI)
     * Integration Testing Done :- 	11-04-2018
     * EmployeeId from Authorization token Done :- 14-04-2018
     */

	@PostMapping("/add")
	public void add(HttpServletRequest request, @RequestParam String subSkillId, @RequestParam int rating) throws Exception {
		logger.info("/api/add accessed");
		String employeeId =null;

		try{

            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if(!( ((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                logger.info("Trying to Add the Employee Rating of " + employeeId);
                if (rating <= 0 || rating > 5) {
                    throw new Exception("Rating Invalid"+ rating);
                }
                else {
                    employeeSkillService.addNew(employeeId, subSkillId, rating);
                }
                logger.debug("Saved Rating of Employee "+employeeId +", subSkillid "+subSkillId+",with rating "+rating);
            } else {

                logger.info("Employee Id not Found in the Authorization");
            }

        }catch(Exception e){
            logger.info("Some error occured" + e.toString());
            logger.error(e.getMessage());
        }




	}

    /*
     * For getting the EmployeeSkillPlaceholder for Dashboard of a particular Employee
     * Param:- empId (Employee ID of the Employee)
     * Integration Testing Done :- 	11-04-2018
     * EmployeeId from Authorization token Done :- 13-04-2018
     */
	@GetMapping("/getEmployeeSkillPlaceholder")
	public EmployeeSkillPlaceholderDomain getEmployeeSkillPlaceholder(HttpServletRequest request){
		logger.info("/api/getEmployeeSkillPlaceholder");

        EmployeeSkillPlaceholderDomain toReturn = null;
        String employeeId = null;
      	try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if(!( ((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {

                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + employeeId);
                logger.debug("Getting detail for the Skill Placeholder, using employeeId " + employeeId);
                toReturn = employeeSkillService.getEmployeeSkillPlaceHolderDomain(employeeId);

            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        }catch(Exception e){
		    logger.info("Some error occured" + e.toString());
            logger.error(e.getMessage());
        }


		return toReturn;
	}

    /*
     * For getting all the Employee Skills
     * Param:- empId (Employee ID of the Employee)
     * Integration Testing Done :- 	11-04-2018
     * EmployeeId from Authorization token Done :- 13-04-2018
     */
	@GetMapping("/getEmployeeSkills")
	public List<EmployeeSkillDomain> getEmployeeSkills(HttpServletRequest request,HttpServletResponse res) {
        logger.info("/api/getEmployeeSkills accessed");
        String employeeId =null;
        List<EmployeeSkillDomain> toReturn = null;

        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if(!((HttpServletRequest) request).getHeader("Authorization").equals(null)) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                if(employeeId!=null) {

                    logger.debug("Paramater received : EmployeeId " + employeeId);
                    logger.info("Fetching Employee Skills");
                    toReturn = employeeSkillService.getAll(employeeId);
                }else{
                    logger.info("Employee Id not present in the id token");
                }

            }else{
                logger.info("No Authorization Present");
            }
        }catch(Exception e ) {
            logger.info("Some Error Occured" + e.toString());
            logger.error(e.getMessage());
        }

		return toReturn;

	}


		
	 

}
