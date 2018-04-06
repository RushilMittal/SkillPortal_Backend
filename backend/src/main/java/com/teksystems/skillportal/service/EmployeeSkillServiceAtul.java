package com.teksystems.skillportal.service;


import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.teksystems.skillportal.init.MongoConfigNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.google.common.cache.LoadingCache;
import com.mongodb.BasicDBObject;
import com.teksystems.skillportal.domain.*;
import com.teksystems.skillportal.init.GuavaCacheInit;

import com.teksystems.skillportal.model.*;
import com.teksystems.skillportal.repository.*;

@Service
public class EmployeeSkillServiceAtul {
	
	static ApplicationContext ctx =
            new AnnotationConfigApplicationContext(MongoConfigNew.class);
	static MongoOperations mongoOperation =
            (MongoOperations) ctx.getBean("mongoTemplate");
	
	@Autowired
	 EmployeeSkillRepository empSkillRepository;
	
	@Autowired
	 SubSkillRepository subSkillRepository;
//
///* Service to get an employee skill by object id generated by mongo
// *  
//*/
//public EmployeeSkillDomain getEmployeeSkillByObjectId(String id)
//	{
//	 //retrieving employee skill using object id
//	  EmployeeSkill empSkill = empSkillRepository.findById(id);
//
//
//
//	//retrieving subskill object from database according to subskillid
//		SubSkill subSkill = subSkillRepository.findOne(empSkill.getSubSkillId());
//
//		//making a domain object to return to front end
//		return  new EmployeeSkillDomain(empSkill.getEmpId(),subSkill,empSkill.getRating(),empSkill.getLastModifiedDate());
//
//  }
//
//
///*
// *Service to delete rating of a subskill of an employee 
// *
// */
public void deleteSubSkill(String empId,String subSkillId)
	{
        // retrieving all subskill ratings of a particular subskill of an employee 
		List<EmployeeSkill> employeeSkills= this.empSkillRepository.findByEmpIdAndSubSkillId(empId,subSkillId);
		
		//deleting those subskills
		for(EmployeeSkill iterable: employeeSkills)
		{
			this.empSkillRepository.delete(iterable);
		}

	}
//
//
///* Service to add new rating of an employee
// * 
// */
//public void addNew(String empId,String subSkillId,int rating)
//   {
//	   System.out.println(empId);
//	   System.out.println(rating);
//	   System.out.println(subSkillId);
//	//created a new object to insert into database
//	EmployeeSkill newSkill = new EmployeeSkill();
//	
//	//setting properties of objected as received from front end
//	//TODO: Use builder pattern to build models
//	newSkill.setEmpId(empId);
//	newSkill.setSubSkillId(subSkillId);
//	newSkill.setRating(rating);
//	newSkill.setLastModifiedDate(new Date());
//	
//	//saving the object into employee skill database
//	empSkillRepository.save(newSkill);
//	
//   }
//
//
//
//	
///* Service to update rating of an employee
// * 
// */
//public EmployeeSkillDomain update(String empId,String subSkillId,int rating)
//   {   empId = empId.trim();
//   		subSkillId = subSkillId.trim();
//	   System.out.println(empId);
//	   System.out.println(rating);
//	   System.out.println(subSkillId);
//	//created a new object to insert into database
//	EmployeeSkill updatedSkill = new EmployeeSkill();
//
//	//setting properties of objected as received from front end
//	updatedSkill.setEmpId(empId);
//	updatedSkill.setSubSkillId(subSkillId);
//	updatedSkill.setRating(rating);
//	updatedSkill.setLastModifiedDate(new Date());
//
//	   System.out.println(updatedSkill.getLastModifiedDate());
//	//saving the object into employee skill database
//	empSkillRepository.save(updatedSkill);
//	   System.out.println("I am at this checkpoint");
//	
//	//retrieving subskill object from database according to subskillid
//	SubSkill subSkill = subSkillRepository.findById(subSkillId);
////	   System.out.println(subSkill.getName());
//	
//	// creating an object of subskilldomain type and copying attribute values from subskill object
//	SubSkillDomain subSkillDomain = new SubSkillDomain(subSkill.getId(),subSkill.getName(),subSkill.getSkillId(),getSubSkillCount(subSkill.getId()));
//
//	   System.out.println(subSkillDomain.getName());
//	
//	//making a domain object to return to front end
//	EmployeeSkillDomain emp1 = new EmployeeSkillDomain(updatedSkill.getEmpId(), subSkillDomain,updatedSkill.getRating(),updatedSkill.getLastModifiedDate());
//	
//	System.out.println(emp1.getSubSkill().getId());
//	System.out.println(emp1.getRating());
//
//
//	return emp1;
//   }
//
//
///* Service to get all rated skills of an employee
// * 
// */
//public List<EmployeeSkillDomain> getAll(String empId)
//   {
//	return getAllEmployeeSkillData(empId);
//   }
//
//
//
//private List<EmployeeSkillDomain> getAllEmployeeSkillData(String empId){
//	 //retrieving all skills of a particular employee according to empId
//	 List<EmployeeSkill> empSkills = empSkillRepository.findByEmpId(empId);
//	 
//	 //grouping together objects which are ratings of the same subskill over time
//	 Map<String, List<EmployeeSkill>> employeeSkillGrouped =
//			    empSkills.stream().collect(Collectors.groupingBy(EmployeeSkill::getSubSkillId));
//	 
//	 //a list to store employee skill objects grouped by subskillid
//	 List<EmployeeSkill> latestEmpSkills = new LinkedList<>();
//	 
//	 //iterating through all group of subskills of an employee
//	 for(String key: employeeSkillGrouped.keySet()){
//		    
//		    //retrieving all objects of same subskill
//			List<EmployeeSkill> empOne = employeeSkillGrouped.get(key);
//			
//			//sorting these objects by last modified date
//			Collections.sort(empOne,new Comparator<EmployeeSkill>() {
//			    @Override
//			    public int compare(EmployeeSkill o1, EmployeeSkill o2) {
//			        return o1.getLastModifiedDate().compareTo(o2.getLastModifiedDate());
//			    }
//			});
//		  
//		  //adding the object with latest last modified date of a subskill
//		  latestEmpSkills.add(empOne.get((empOne.size())-1));
//		}
//	
//	// conversion of latest employee skills to employeeSkillDomain type
//	List<EmployeeSkillDomain> empSkillDom =  new LinkedList<>();
//	
//	for(EmployeeSkill iterable: latestEmpSkills)
//	  {
//		//retrieving subskill object from database according to subskillid
//		SubSkill subskill = subSkillRepository.findById(iterable.getSubSkillId());
//		
//		SubSkillDomain subskilldomain = new SubSkillDomain(subskill.getId(),subskill.getName(),subskill.getSkillId(),getSubSkillCount(subskill.getId()));
//		
//		EmployeeSkillDomain temp = new EmployeeSkillDomain(iterable.getEmpId(), subskilldomain,iterable.getRating(),iterable.getLastModifiedDate());
//		
//		empSkillDom.add(temp);
//	  }
//	
//	//return
//	return empSkillDom;
//   }
//
///* Service to get all subskills by skill id,
// *  to get all subskills that belong to a particular skill  
// *  except the ones user has already rated
//	*/	
// 
	
 public List<SubSkill> getAllUnassignedSubSkills(String empId, String skill) throws ExecutionException
 
   {
//
//	 LoadingCache<String, List<SubSkill>> skillCache = GuavaCacheInit.getSkillLoadingCache();
//     List<SubSkill> allSkills = skillCache.get(skill);
//
//     List<String> assignedSkillIds = mongoOperation.getCollection("employeeskill").distinct("subSkillId",new BasicDBObject("empId",empId));
//
//     List<SubSkill> unassignedSkills = new LinkedList<>();
//
//     for(Iterator<SubSkill> iter = allSkills.iterator(); iter.hasNext();)
//     {
//     	SubSkill i = iter.next();
//     	boolean flag = false;
//     	for (String j: assignedSkillIds)
//     	{
//     		if(i.getId().equals(j))
//     		{
//     			//set flag to true if ID matches, that is employee has rated that skill
//     			flag=true;
//     		    break;
//     		}
//     	}
//
//        if(!flag)
//        {
//     	   unassignedSkills.add(i);
//        }
//     }
//
     return null;
 }
// 
//
//
// /* Service to get count of subskills rated by users by subskill id
//	 *  
//	*/
//public int getSubSkillCount(String subSkillId) {
//	   
//	   //finding all employee skills records for one subskill
// 	List<EmployeeSkill> empSkills = empSkillRepository.findDistinctEmployeeSkillBySubSkillId(subSkillId);
// 	
// 	//grouping this records by empid as one employee can have multiple records
// 	//for same subskill
// 	Map<String, List<EmployeeSkill>> empSkillGrouped =
//			    empSkills.stream().collect(Collectors.groupingBy(EmployeeSkill::getEmpId));
//     
// 	//returning the number of groups, i.e., 
// 	//the number of employees who have rated a particular subskill
// 	return empSkillGrouped.size();
// }
//
//
///* Service to get count of skills rated by users by skill id
//	 *  
//	*/
// public int getSkillCount(String skillId) {
//	   
//	   //fetching all subskills under a skill
//	   List<SubSkill> subSkills = subSkillRepository.findBySkillId(skillId);
//	   
//	   //initalising count
//	   int count = 0;
//	   
//	   for(SubSkill iterable : subSkills)
//	   {
//		   //adding to count the number of people rated under each subskill
//		   count = count + getSubSkillCount(iterable.getId());
//	   }
//	     
//	    return count; 
//   }
// 
// /*
//	 *  Written by Sahib
//	 *  Service to return the count of Skill Rated by the User.
//	 */
//	public int getTotalSkillRatedbyUser(String employeeId){
//		// Fetchs the list of all the rated user and return the count
//		List<EmployeeSkillDomain> forCount = getAll(employeeId);
//		return forCount.size();
//	}
//
//	/*
//	 * Written BY Sahib
//	 * Service to return the Name of the Highest Skill Rated by the User
//	 */
//	public String getHighestRatedSkillByUser(String employeeId){
//		//
//		List<EmployeeSkillDomain> empSkills = getAll(employeeId);
//		Collections.sort(empSkills, new Comparator<EmployeeSkillDomain>() {
//			@Override
//			public int compare(EmployeeSkillDomain o1, EmployeeSkillDomain o2) {
//
//				return Integer.compare(o2.getRating(),o1.getRating());
//			}
//		});
//		//comments for personal
//		for(EmployeeSkillDomain emp: empSkills){
//			System.out.println(emp.toString());
//		}
//		return empSkills.get(0).getSubSkill().getName();
//	}
//
//
//	/*
//	 * Written BY Sahib
//	 * Service to return the Highest Rated Skill
//	 */
//	public int getHighestRatingOfEmployee(String employeeId){
//		//
//		List<EmployeeSkillDomain> empSkills = getAll(employeeId);
//		Collections.sort(empSkills, new Comparator<EmployeeSkillDomain>() {
//			@Override
//			public int compare(EmployeeSkillDomain o1, EmployeeSkillDomain o2) {
//
//				return Integer.compare(o2.getRating(),o1.getRating());
//			}
//		});
//		//comments for personal
////		for(EmployeeSkillDomain emp: empSkills){
////			System.out.println(emp.toString());
////		}
//		return empSkills.get(0).getRating();
//	}
//
//
//	/*
//	 * Written by Sahib
//	 * Service(REST API) to return the days since last Update/Rating of Skill in years, months and days
//	 */
//
//	public int[] getLastUpdatedOfEnployee(String employeeId){
//		int[] lastUpdated = new int[3];
//
//     //Created the new Function in the Repository and found the last modified rating.
//     EmployeeSkill employeeSkill = empSkillRepository.findTopByEmpIdOrderByLastModifiedDateDesc(employeeId);
//
//		Date today = new Date();
//		System.out.println("Todays Date" + today);
//		Date recieved;
//
//
//      recieved = employeeSkill.getLastModifiedDate();
//      System.out.println(recieved);
//
//		//Calculating the diffrence in time and then will divide by respective to get in years and days
//		long diffrence =  today.getTime() - recieved.getTime();
//		System.out.println("Diffrence is: "+ diffrence);
//
//		Calendar recievedDate = Calendar.getInstance();
//		Calendar todayDate = Calendar.getInstance();
//
//		recievedDate.setTime(recieved);
//		todayDate.setTime(today);
//		int day,month,year,increment =0;
//
//		System.out.println(recievedDate.getActualMaximum(Calendar.DAY_OF_MONTH));
//		System.out.println("recieved " + recievedDate.get(Calendar.DAY_OF_MONTH) + " today: " + todayDate.get(Calendar.DAY_OF_MONTH) );
//		if (recievedDate.get(Calendar.DAY_OF_MONTH) > todayDate.get(Calendar.DAY_OF_MONTH)) {
//			increment =recievedDate.getActualMaximum(Calendar.DAY_OF_MONTH);
//		}
//		System.out.println("increment"+increment);
//		// DAY CALCULATION
//		if (increment != 0) {
//			day = (todayDate.get(Calendar.DAY_OF_MONTH) + increment) - recievedDate.get(Calendar.DAY_OF_MONTH);
//			increment = 1;
//		} else {
//			day = todayDate.get(Calendar.DAY_OF_MONTH) - recievedDate.get(Calendar.DAY_OF_MONTH);
//		}
//
//		// MONTH CALCULATION
//		if ((recievedDate.get(Calendar.MONTH) + increment) > todayDate.get(Calendar.MONTH)) {
//			month = (todayDate.get(Calendar.MONTH) + 12) - (recievedDate.get(Calendar.MONTH) + increment);
//			increment = 1;
//		} else {
//			month = (todayDate.get(Calendar.MONTH)) - (recievedDate.get(Calendar.MONTH) + increment);
//			increment = 0;
//		}
//
//		// YEAR CALCULATION
//		year = todayDate.get(Calendar.YEAR) - (recievedDate.get(Calendar.YEAR) + increment);
//		System.out.println(year+"\tYears\t\t"+month+"\tMonths\t\t"+day+"\tDays");
//
//		lastUpdated[0] = year;
//		lastUpdated[1] = month;
//		lastUpdated[2] = day;
//		return lastUpdated;
//
//	}



}
