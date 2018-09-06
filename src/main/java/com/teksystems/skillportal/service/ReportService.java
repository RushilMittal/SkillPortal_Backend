package com.teksystems.skillportal.service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.teksystems.skillportal.helper.ConfigurationStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import com.mongodb.BasicDBObject;
import com.teksystems.skillportal.domain.CertificationDomain;
import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.SkillDomain;
import com.teksystems.skillportal.domain.SkillReport;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.init.MongoConfigNew;
import com.teksystems.skillportal.model.Certification;
import com.teksystems.skillportal.model.EmployeeCertification;
import com.teksystems.skillportal.model.EmployeeSkill;
import com.teksystems.skillportal.model.SubSkill;
import com.teksystems.skillportal.repository.CertificationRepository;
import com.teksystems.skillportal.repository.EmployeeSkillRepository;
import com.teksystems.skillportal.repository.SubSkillRepository;

@Service
public class ReportService {
  
	static ApplicationContext ctx =
            new AnnotationConfigApplicationContext(MongoConfigNew.class);
	static MongoOperations mongoOperation =
            (MongoOperations) ctx.getBean("mongoTemplate");
	
	@Autowired
	 EmployeeSkillRepository empSkillRepository;
	
	@Autowired
	 SubSkillRepository subSkillRepository;
	
	@Autowired
	 CertificationRepository certificationRepository;
	
	public List<SubSkillDomain> topNSubSkills(int n)
	{
		Aggregation agg = newAggregation(group(ConfigurationStrings.SUBSKILLID,ConfigurationStrings.EMPID),
				                         group(ConfigurationStrings.SUBSKILLID).count().as(ConfigurationStrings.RATEDUSERS),
				                         sort(Sort.Direction.DESC, ConfigurationStrings.RATEDUSERS),
				                         limit(n));
		
		AggregationResults<SkillDomain> groupResults 
		= mongoOperation.aggregate(agg, EmployeeSkill.class, SkillDomain.class);
		
		List<SkillDomain> result = groupResults.getMappedResults();
		List<SubSkillDomain> results = new LinkedList<>();
		
		for(SkillDomain i:result)
		{
			SubSkill temp = subSkillRepository.findOne(i.getId());
		    SubSkillDomain temp2 = new SubSkillDomain(temp.getId(),temp.getSubSkill(),temp.getSubSkillDesc(),temp.getSkill(),temp.getSkillGroup(),temp.getPractice(),i.getRatedUsers());

			results.add(temp2);
		}
		
		return results;
	}
	
	public List<SubSkillDomain> topNSubSkillsinLastXMonths(int n,long x)
	{
//		Calendar cal = Calendar.getInstance();  //Get current date/month i.e 27 Feb, 2012
//		cal.add(Calendar.MILLISECOND, -x);   //Go to date, 6 months ago 27 July, 2011
//		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		Date dt =new Date(x);
		
		Aggregation agg = newAggregation(match(Criteria.where(ConfigurationStrings.LASTMODIFIED).gt(dt)),
				                         group(ConfigurationStrings.SUBSKILLID,ConfigurationStrings.EMPID),
				                         group(ConfigurationStrings.SUBSKILLID).count().as(ConfigurationStrings.RATEDUSERS),
				                         sort(Sort.Direction.DESC, ConfigurationStrings.RATEDUSERS),
				                         limit(n));
		
		AggregationResults<SkillDomain> groupResults 
		= mongoOperation.aggregate(agg, EmployeeSkill.class, SkillDomain.class);
		
		List<SkillDomain> result = groupResults.getMappedResults();
		List<SubSkillDomain> results = new LinkedList<>();
		
		for(SkillDomain i:result)
		{
		    SubSkill temp = subSkillRepository.findOne(i.getId());
		    SubSkillDomain temp2 = new SubSkillDomain(temp.getId(),temp.getSubSkill(),temp.getSubSkillDesc(),temp.getSkill(),temp.getSkillGroup(),temp.getPractice(),i.getRatedUsers());

			results.add(temp2);
		}
		
		return results;
	}
	
	public List<EmployeeSkillDomain> skillsOfEmployee(String empId)
	{
		List<String> skills = mongoOperation.getCollection("employeeskill").distinct(ConfigurationStrings.SUBSKILLID,new BasicDBObject(ConfigurationStrings.EMPID,empId));
		Aggregation agg = newAggregation(match(Criteria.where(ConfigurationStrings.EMPID).is(empId)),
				group(ConfigurationStrings.SUBSKILLID).max(ConfigurationStrings.LASTMODIFIED).as(ConfigurationStrings.LASTMODIFIED).addToSet(ConfigurationStrings.RATING).as(ConfigurationStrings.RATING)
                );
		

		
		AggregationResults<EmployeeSkill> groupResults 
		= mongoOperation.aggregate(agg, EmployeeSkill.class, EmployeeSkill.class);
		
		List<EmployeeSkill> result = groupResults.getMappedResults();
		List<EmployeeSkillDomain> subSkills = new LinkedList<>();
		for(EmployeeSkill i:result)
		{
			SubSkill temp = subSkillRepository.findOne(i.getId());
			SubSkillDomain subSkill = new SubSkillDomain(temp.getId(),temp.getSubSkill(),temp.getSubSkillDesc(),temp.getSkill(),temp.getSkillGroup(),temp.getPractice(),0);
			EmployeeSkillDomain toAdd= new EmployeeSkillDomain(empId,subSkill,i.getRating(),i.getLastModifiedDate());
			subSkills.add(toAdd);
		}
		
 		return subSkills;
	}
	
	public List<SkillReport> EmployeesWhoUpdatedSubSkillsinLastXMonths(long from,long to)
	{


        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(from);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(to);
        Query query = new Query(Criteria.where("certificationValidityDate").gt(cal.getTime()).lt(cal2.getTime()));
		Aggregation agg = newAggregation(match(Criteria.where(ConfigurationStrings.LASTMODIFIED).gt(cal.getTime()).lt(cal2.getTime())),
				                         group(ConfigurationStrings.EMPID,ConfigurationStrings.SUBSKILLID).max(ConfigurationStrings.LASTMODIFIED).as("maxDate").last(ConfigurationStrings.RATING).as("lastRating").
				                         min(ConfigurationStrings.LASTMODIFIED).as("minDate").first(ConfigurationStrings.RATING).as("firstRating"),
				                         sort(Sort.Direction.ASC, ConfigurationStrings.EMPID));
		

		
		AggregationResults<SkillReport> groupResults 
		= mongoOperation.aggregate(agg, EmployeeSkill.class, SkillReport.class);
		
		List<SkillReport> result = groupResults.getMappedResults();
		
		

		
		return result;
	}
	
	public List<EmployeeCertificationDomain> CertificatesExipringInNextNmonths(long from,long to)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(from);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(to);
		Query query = new Query(Criteria.where("certificationValidityDate").gt(cal.getTime()).lt(cal2.getTime()));
		List<EmployeeCertification> employeeCertifications = mongoOperation.find(query, EmployeeCertification.class);
		List<EmployeeCertificationDomain> employeeCertDomains = new LinkedList<>();
		for(EmployeeCertification i:employeeCertifications)
		{
			Certification cert = certificationRepository.findById(i.getCertificationId());
			CertificationDomain certDomain = new CertificationDomain(cert.getId(),cert.getSkillId(),cert.getCertificationName(),cert.getInstitution());
			employeeCertDomains.add(new EmployeeCertificationDomain(i.getEmployeeId(),certDomain,i.getCertificationDate(),i.getCertificationValidityDate(),i.getCertificationNumber(),i.getCertificationUrl()));
		}
		return employeeCertDomains;
	}

	public List<String> EmployeesWithASkill()
	{
		List<String> empIds = mongoOperation.getCollection("employeeskill").distinct(ConfigurationStrings.EMPID);
		return empIds;
	}

	
}
