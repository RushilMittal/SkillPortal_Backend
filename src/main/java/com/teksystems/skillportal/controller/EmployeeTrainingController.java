package com.teksystems.skillportal.controller;

import com.teksystems.skillportal.domain.EmployeeTrainingDomain;
import com.teksystems.skillportal.domain.EmployeeTrainingPlaceholderDomain;
import com.teksystems.skillportal.domain.TrainingEventDomain;
import com.teksystems.skillportal.domain.TrainingListEventDomain;
import com.teksystems.skillportal.service.EmployeeTrainingService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value="/training",method= RequestMethod.GET)
@CrossOrigin()
public class EmployeeTrainingController {

    private static Logger logger = Logger.getLogger(EmployeeTrainingController.class);

    @Autowired
    EmployeeTrainingService employeeTrainingService;

    private TokenValidationService tokenValidator;

    @GetMapping("/gettraining")
    public List<EmployeeTrainingDomain> getByEmpId(HttpServletRequest request) {
        logger.info("/gettraining API called");
        String empId = null;
        List<EmployeeTrainingDomain> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + empId);
                logger.info("Trying to fetch employee's added Training");
                toReturn = employeeTrainingService.getEmployeeTrainingByEmployeeId(empId);
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
        System.out.println(toReturn);
        return toReturn;
    }

    @GetMapping("/gettrainingevent")
    public List<TrainingEventDomain> getEventByEmpId(HttpServletRequest request) {
        logger.info("/gettrainingevent API called");
        String empId = null;
        List<TrainingEventDomain> toReturn = null;

        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + empId);
                logger.info("Trying to fetch employee's added Events for Calendar view");
                toReturn = employeeTrainingService.getTrainingEventByEmployeeId(empId);
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
        System.out.println(toReturn);
        return toReturn;
    }

    @DeleteMapping("/delete")
    public void getByEmpIdAndSubSkillId(HttpServletRequest request, @RequestParam("trainingId")String trainingId) {
        logger.info("/delete API called");
        String empId = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + empId);
                logger.info("Trying to delete employee's added Events");
                employeeTrainingService.cancelEnrollment(empId,trainingId);
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
    }

    @GetMapping("/gettrainingeventlist")
    public List<TrainingListEventDomain> getEventListByEmpId(HttpServletRequest request){
        logger.info("/gettrainingeventlist API called");
        String empId = null;
        List<TrainingListEventDomain> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + empId);
                logger.info("Trying to fetch employee's added Events for list view");
                toReturn = employeeTrainingService.getTrainingListEventByEmployeeId(empId);
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
        System.out.println(toReturn );
        return toReturn;
    }

    @GetMapping("/getenrolledtrainings")
    public List<EmployeeTrainingPlaceholderDomain> getEnrolledTraining(HttpServletRequest request) {
        logger.info("/getenrolledtrainings API called");
        String empId = null;
        List<EmployeeTrainingPlaceholderDomain> toReturn = null;
        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + empId);
                logger.info("Trying to fetch employee's added Trainings for training placeholder");
                toReturn = employeeTrainingService.getEnrolledTraining(empId);
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
        return toReturn;
    }

    @GetMapping("/getupcomingtrainings")
    public List<EmployeeTrainingPlaceholderDomain> getUpcomingTraining(HttpServletRequest request) {
        logger.info("/getupcomingtrainings API called");
        String empId = null;
        List<EmployeeTrainingPlaceholderDomain> toReturn = null;

        try {
            logger.info("Trying to Fetch the Employee Id from the HTTP HEADERS");
            if (!(((HttpServletRequest) request).getHeader("Authorization").toString().equals(null))) {
                empId = tokenValidator.ExtractEmployeeId(request);
                logger.debug("Paramater received : employeeId " + empId);
                logger.info("Trying to fetch Upcoming Trainings for training placeholder");
                toReturn = employeeTrainingService.getUpcomingTraining();
            } else {
                logger.info("Employee Id not Found in the Authorization");
            }
        } catch (Exception e) {
            logger.info("Some Error Occurred: " + e.toString());
        }
        return toReturn;
    }
}