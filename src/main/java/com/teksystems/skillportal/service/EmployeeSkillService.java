package com.teksystems.skillportal.service;


import com.google.common.cache.LoadingCache;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.EmployeeSkillPlaceholderDomain;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.init.GuavaCacheInit;
import com.teksystems.skillportal.init.MongoConfigNew;
import com.teksystems.skillportal.model.EmployeeSkill;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.EmployeeSkillRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Service
public class EmployeeSkillService {

    static ApplicationContext ctx =
            new AnnotationConfigApplicationContext(MongoConfigNew.class);
    static MongoOperations mongoOperation =
            (MongoOperations) ctx.getBean("mongoTemplate");

    @Autowired
    EmployeeSkillRepository empSkillRepository;

    @Autowired
    SubSkillRepository subSkillRepository;

    @Autowired
    GuavaCacheInit guavaCacheInit;

    public List<SubSkillDomain> getAllUnassignedSubSkills(String empId, String skill) throws MongoException, ExecutionException{
        empId = empId.trim();
        skill = skill.trim();
        LoadingCache<String, List<SubSkill>> skillCache = guavaCacheInit.getSkillLoadingCache();
        List<SubSkill> allSkills = skillCache.get(skill);

        List<String> assignedSkillIds = mongoOperation.getCollection("employeeskill").distinct(ConfigurationStrings.SUBSKILLID, new BasicDBObject(ConfigurationStrings.EMPID, empId));


        List<SubSkillDomain> toReturn = new LinkedList<>();
        for (SubSkill i : allSkills) {
            boolean flag = false;
            for (String j : assignedSkillIds) {
                if (i.getId().equals(j)) {
                    //set flag to true if ID matches, that is employee has rated that skill
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                SubSkillDomain temp = new SubSkillDomain(i.getId(), i.getSubSkill(), i.getModelSubSkillDesc(), i.getModelSkill(),
                        i.getModelSkillGroup(), i.getModelPractice(), getSubSkillCount(i.getId()));
                toReturn.add(temp);

                // need to call the count of rated users here and assigned for the domain

            }
        }

        // convert it into the domain before returning

        return toReturn;
    }


    public void addNew(String empId, String subSkillId, int rating) throws MongoException {
        empId = empId.trim();
        subSkillId = subSkillId.trim();


        //created a new object to insert into database
        EmployeeSkill newSkill = new EmployeeSkill();

        //setting properties of objected as received from front end
        //: Use builder pattern to build models
        newSkill.setEmpId(empId);
        newSkill.setSubSkillId(subSkillId);
        newSkill.setRating(rating);
        newSkill.setLastModifiedDate(new Date());

        //saving the object into employee skill database
        empSkillRepository.save(newSkill);

    }

    /*
     * Service to get count of subskills rated by users by subskill id
     */
    public int getSubSkillCount(String subSkillId) {
        subSkillId = subSkillId.trim();
//finding all distinct employee skills records for one subskill
        List<String> empSkills = mongoOperation.getCollection("employeeskill").distinct(ConfigurationStrings.EMPID,
                new BasicDBObject(ConfigurationStrings.SUBSKILLID, subSkillId));
        //returning the size of list, i.e.,
        //the number of employees who have rated a particular subskill
        return empSkills.size();
    }


    public EmployeeSkillPlaceholderDomain getEmployeeSkillPlaceHolderDomain(String employeeId) throws MongoException {

        employeeId = employeeId.trim();
        List<EmployeeSkillDomain> empSkills = getAll(employeeId);
        //Fetching the number of Skill Rated
        int numberofSkillRated = empSkills.size();

        Collections.sort(empSkills, (o1, o2) -> Integer.compare(o2.getRating(), o1.getRating()));

        //Fetching  the Highest Rated Skill nad highest rating
        String highestRatedSkill = empSkills.get(0).getSubSkill().getSubSkill();
        int highestRating = empSkills.get(0).getRating();

        // for calculating the period of last updated.
        int[] lastUpdated = new int[3];

        //Created the new Function in the Repository and found the last modified rating.
        EmployeeSkill employeeSkill = empSkillRepository.findTopByEmpIdOrderByLastModifiedDateDesc(employeeId);

        Date today = new Date();
        Date recieved;


        recieved = employeeSkill.getLastModifiedDate();


        //Calculating the difference in time and then will divide by respective to get in years and days


        Calendar recievedDate = Calendar.getInstance();
        Calendar todayDate = Calendar.getInstance();

        recievedDate.setTime(recieved);
        todayDate.setTime(today);
        int day;
        int month;
        int year;
        int increment = 0;

        if (recievedDate.get(Calendar.DAY_OF_MONTH) > todayDate.get(Calendar.DAY_OF_MONTH)) {
            increment = recievedDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        // DAY CALCULATION
        if (increment != 0) {
            day = (todayDate.get(Calendar.DAY_OF_MONTH) + increment) - recievedDate.get(Calendar.DAY_OF_MONTH);
            increment = 1;
        } else {
            day = todayDate.get(Calendar.DAY_OF_MONTH) - recievedDate.get(Calendar.DAY_OF_MONTH);
        }

        // MONTH CALCULATION
        if ((recievedDate.get(Calendar.MONTH) + increment) > todayDate.get(Calendar.MONTH)) {
            month = (todayDate.get(Calendar.MONTH) + 12) - (recievedDate.get(Calendar.MONTH) + increment);
            increment = 1;
        } else {
            month = (todayDate.get(Calendar.MONTH)) - (recievedDate.get(Calendar.MONTH) + increment);
            increment = 0;
        }

        // YEAR CALCULATION
        year = todayDate.get(Calendar.YEAR) - (recievedDate.get(Calendar.YEAR) + increment);


        lastUpdated[0] = year;
        lastUpdated[1] = month;
        lastUpdated[2] = day;

        EmployeeSkillPlaceholderDomain temp = new EmployeeSkillPlaceholderDomain(numberofSkillRated, highestRatedSkill, highestRating, lastUpdated);


        return temp;
    }

    public List<EmployeeSkillDomain> getAll(String empId) throws MongoException {
        //retrieving all skills of a particular employee according to empId
        empId = empId.trim();
        List<EmployeeSkillDomain> empSkillDom = new LinkedList<>();

        List<EmployeeSkill> empSkills = empSkillRepository.findByEmpId(empId);


        //grouping together objects which are ratings of the same subskill over time
        Map<String, List<EmployeeSkill>> employeeSkillGrouped =
                empSkills.stream().collect(Collectors.groupingBy(EmployeeSkill::getSubSkillId));
        //a list to store employee skill objects grouped by subskillid
        List<EmployeeSkill> latestEmpSkills = new LinkedList<>();

        //iterating through all group of subskills of an employee
        for (Map.Entry<String, List<EmployeeSkill>> entrySet : employeeSkillGrouped.entrySet()) {

            //retrieving all objects of same subskill
            String key = entrySet.getKey();
            List<EmployeeSkill> empOne = employeeSkillGrouped.get(key);

            //sorting these objects by last modified date
            Collections.sort(empOne, (o1, o2) -> o1.getLastModifiedDate().compareTo(o2.getLastModifiedDate()));

            //adding the object with latest last modified date of a subskill
            latestEmpSkills.add(empOne.get((empOne.size()) - 1));
        }

        // conversion of latest employee skills to employeeSkillDomain type


        for (EmployeeSkill iterable : latestEmpSkills) {
            //retrieving subskill object from database according to subskillid
            SubSkill subskill = subSkillRepository.findById(iterable.getSubSkillId());

            SubSkillDomain subskilldomain = new SubSkillDomain(subskill.getId(), subskill.getSubSkill(), subskill.getModelSubSkillDesc(), subskill.getModelSkill(), subskill.getModelSkillGroup(), subskill.getModelPractice(), getSubSkillCount(subskill.getId()));

            EmployeeSkillDomain temp = new EmployeeSkillDomain(iterable.getEmpId(), subskilldomain, iterable.getRating(), iterable.getLastModifiedDate());

            empSkillDom.add(temp);
        }

        //return
        return empSkillDom;

    }

    /*
     *Service to delete rating of a subskill of an employee
     *
     */
    public void deleteSubSkill(String empId, String subSkillId)  throws MongoException {
        // retrieving all subskill ratings of a particular subskill of an employee

        List<EmployeeSkill> employeeSkills = this.empSkillRepository.findByEmpIdAndSubSkillId(empId, subSkillId);

        //deleting those subskills
        for (EmployeeSkill iterable : employeeSkills) {
            this.empSkillRepository.delete(iterable);
        }


    }


}
