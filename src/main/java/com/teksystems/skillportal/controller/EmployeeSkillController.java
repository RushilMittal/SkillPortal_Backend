package com.teksystems.skillportal.controller;


import java.io.IOException;
import java.util.List;
import com.mongodb.MongoException;
import com.teksystems.skillportal.helper.ConfigurationStrings;
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
	public List<SubSkillDomain> getBySubSkillId(HttpServletRequest request, HttpServletResponse response,@RequestParam String skillName) throws IOException {
        logger.info("/api/getSubSkillsBySkillId accessed");
        logger.debug("Paramater received : SkillName "+skillName );
        List<SubSkillDomain> toReturn = null;
        String employeeId ;
        try{
            logger.info(ConfigurationStrings.FETCHING);
            if(request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                toReturn = employeeSkillService.getAllUnassignedSubSkills(employeeId,skillName);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        }catch(Exception e){
            logger.info(ConfigurationStrings.ERROR + e.toString());
            logger.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
	public void add(HttpServletRequest request, HttpServletResponse response, @RequestParam String subSkillId, @RequestParam int rating) throws IOException {
		logger.info("/api/add accessed");
		String employeeId ;


        try {
            logger.info(ConfigurationStrings.FETCHING);
            if (request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                logger.info("Trying to Add the Employee Rating of " + employeeId);
                if (rating <= 0 || rating > 5) {
                    response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, ConfigurationStrings.INVALIDTOKEN);
                } else {
                    employeeSkillService.addNew(employeeId, subSkillId, rating);
                }
                logger.debug("Saved Rating of Employee " + employeeId + ", subSkillid " + subSkillId + ",with rating " + rating);
            } else {

                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        }catch (MongoException e){
            logger.info(ConfigurationStrings.ERROR + e.toString());
            logger.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }





	}

    /*
     * For getting the EmployeeSkillPlaceholder for Dashboard of a particular Employee
     * Param:- empId (Employee ID of the Employee)
     * Integration Testing Done :- 	11-04-2018
     * EmployeeId from Authorization token Done :- 13-04-2018
     */
	@GetMapping("/getEmployeeSkillPlaceholder")
	public EmployeeSkillPlaceholderDomain getEmployeeSkillPlaceholder(HttpServletRequest request,HttpServletResponse response) throws IOException {
		logger.info("/api/getEmployeeSkillPlaceholder");

        EmployeeSkillPlaceholderDomain toReturn = null;
        String employeeId ;
      	try {
            logger.info(ConfigurationStrings.FETCHING);
            if(!(request.getHeader(ConfigurationStrings.AUTHORIZATION)== null)) {

                employeeId = tokenValidator.ExtractEmployeeId(request);
                logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                logger.debug("Getting detail for the Skill Placeholder, using employeeId " + employeeId);
                toReturn = employeeSkillService.getEmployeeSkillPlaceHolderDomain(employeeId);

            } else {
                logger.info(ConfigurationStrings.NOTFOUND);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);

            }
        }catch(Exception e){
		    logger.info("Some error occured" + e.toString());
            logger.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
	public List<EmployeeSkillDomain> getEmployeeSkills(HttpServletRequest request,HttpServletResponse response) throws IOException {
        logger.info("/api/getEmployeeSkills accessed");
        String employeeId ;
        List<EmployeeSkillDomain> toReturn = null;

        try {
            logger.info(ConfigurationStrings.FETCHING);
            if(request.getHeader(ConfigurationStrings.AUTHORIZATION)!=null) {
                employeeId = tokenValidator.ExtractEmployeeId(request);
                if(employeeId!=null) {

                    logger.debug(ConfigurationStrings.EMPLOYEEID + employeeId);
                    logger.info("Fetching Employee Skills");
                    toReturn = employeeSkillService.getAll(employeeId);
                }else{
                    logger.info(ConfigurationStrings.NOTFOUND);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);

                }

            }else{
                logger.info("No Authorization Present");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConfigurationStrings.INVALIDTOKEN);
            }
        }catch(Exception e ) {
            logger.info(ConfigurationStrings.ERROR + e.toString());
            logger.error(e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

		return toReturn;

	}


		
	 

}
