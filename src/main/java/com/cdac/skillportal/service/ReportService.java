package com.cdac.skillportal.service;

import com.cdac.skillportal.domain.*;
import com.cdac.skillportal.helper.ConfigurationStrings;
import com.cdac.skillportal.init.MongoConfigNew;
import com.cdac.skillportal.model.Certification;
import com.cdac.skillportal.model.EmployeeCertification;
import com.cdac.skillportal.model.EmployeeSkill;
import com.cdac.skillportal.model.SubSkill;
import com.mongodb.MongoException;
import com.cdac.skillportal.repository.CertificationRepository;
import com.cdac.skillportal.repository.EmployeeSkillRepository;
import com.cdac.skillportal.repository.SubSkillRepository;
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

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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

    public List<SubSkillDomain> topNSubSkills(int n) throws MongoException {
        Aggregation agg = newAggregation(
                group(ConfigurationStrings.SUBSKILLID, ConfigurationStrings.EMPID),
                group(ConfigurationStrings.SUBSKILLID).count().as(ConfigurationStrings.RATEDUSERS),
                sort(Sort.Direction.DESC, ConfigurationStrings.RATEDUSERS),
                limit(n));

        AggregationResults<SkillDomain> groupResults
                = mongoOperation.aggregate(agg, EmployeeSkill.class, SkillDomain.class);

        List<SkillDomain> result = groupResults.getMappedResults();
        List<SubSkillDomain> results = new LinkedList<>();

        for (SkillDomain i : result) {
            SubSkill temp = subSkillRepository.findOne(i.getId());
            SubSkillDomain temp2 = new SubSkillDomain(temp.getId(), temp.getSubSkill(), temp.getSubSkillDesc(), temp.getModelSkill(), temp.getSkillGroup(), temp.getPractice(), i.getRatedUsers());

            results.add(temp2);
        }

        return results;
    }
    public List<SkillDomain> topNSkills(int n){
        Aggregation agg = newAggregation(
                group(ConfigurationStrings.SUBSKILLID, ConfigurationStrings.EMPID),
                group(ConfigurationStrings.SUBSKILLID).count().as(ConfigurationStrings.RATEDUSERS),
                sort(Sort.Direction.DESC, ConfigurationStrings.RATEDUSERS),
                limit(n));

        AggregationResults<SkillDomain> groupResults
                = mongoOperation.aggregate(agg, EmployeeSkill.class, SkillDomain.class);

        List<SkillDomain> result = groupResults.getMappedResults();
        return result;
    }
    // get distinct skils rated by users.
    public Map<String,HashSet<EmployeeSkill>> distinctEmployees() {


        List<EmployeeSkill> employeeSkillList = empSkillRepository.findAll();

        Map<String,HashSet<EmployeeSkill>> distinctEmployeeSkillHashMap = new HashMap<>();

        for(EmployeeSkill temp: employeeSkillList){

            if(distinctEmployeeSkillHashMap.containsKey(temp.getSubSkillId())){
                HashSet<EmployeeSkill> tempHashSet = distinctEmployeeSkillHashMap.get(temp.getSubSkillId());
                tempHashSet.add(temp);
                distinctEmployeeSkillHashMap.put(temp.getSubSkillId(),tempHashSet);
            }
            else{
                HashSet<EmployeeSkill> tempEmployeeSkillHashSet = new HashSet<>();
                tempEmployeeSkillHashSet.add(temp);
                distinctEmployeeSkillHashMap.put(temp.getSubSkillId(),tempEmployeeSkillHashSet);

            }
        }
        // Map for storing the size and then will sort it..
        Map<String,Integer> sizeHashMap = new HashMap<>();
        //make a map with the size and the sort and fetch the value from the actual map before sending..
        for(Map.Entry<String,HashSet<EmployeeSkill>> toFetch: distinctEmployeeSkillHashMap.entrySet()){
            HashSet<EmployeeSkill > t =toFetch.getValue();
            for(EmployeeSkill e: t){
               e.getSubSkillId();
            }
        }


//        Collections.sort(list, new Comparator<Map.Entry<String, HashSet<EmployeeSkill>>>() {
//            @Override
//            public int compare(Map.Entry<String, HashSet<EmployeeSkill>> o1, Map.Entry<String, HashSet<EmployeeSkill>> o2) {
//                return o2.getValue().size().compareTo()
//            }
//        });

//        List<EmployeeSkill> toReturn = new ArrayList<>();
//        for(Map.Entry<String,HashSet<EmployeeSkill>> entry : distinctEmployeeSkillHashMap.entrySet() ){
//            HashSet<EmployeeSkill> temp = entry.getValue();
//            toReturn.add(new ArrayList<EmployeeSkill>(temp));
//        }

        return distinctEmployeeSkillHashMap;
    }


    public List<SubSkillDomain> topNSubSkillsinLastXMonths(int n, long x) throws MongoException {

        Date dt = new Date(x);

        Aggregation agg = newAggregation(match(Criteria.where(ConfigurationStrings.LASTMODIFIED).gt(dt)),
                group(ConfigurationStrings.SUBSKILLID, ConfigurationStrings.EMPID),
                group(ConfigurationStrings.SUBSKILLID).count().as(ConfigurationStrings.RATEDUSERS),
                sort(Sort.Direction.DESC, ConfigurationStrings.RATEDUSERS),
                limit(n));

        AggregationResults<SkillDomain> groupResults
                = mongoOperation.aggregate(agg, EmployeeSkill.class, SkillDomain.class);

        List<SkillDomain> result = groupResults.getMappedResults();
        List<SubSkillDomain> results = new LinkedList<>();

        for (SkillDomain i : result) {
            SubSkill temp = subSkillRepository.findOne(i.getId());
            SubSkillDomain temp2 = new SubSkillDomain(temp.getId(), temp.getSubSkill(), temp.getSubSkillDesc(), temp.getModelSkill(), temp.getSkillGroup(), temp.getPractice(), i.getRatedUsers());

            results.add(temp2);
        }

        return results;
    }

    public List<EmployeeSkillDomain> skillsOfEmployee(String empId) throws MongoException {

        Aggregation agg = newAggregation(match(Criteria.where(ConfigurationStrings.EMPID).is(empId)),
                group(ConfigurationStrings.SUBSKILLID).max(ConfigurationStrings.LASTMODIFIED).as(ConfigurationStrings.LASTMODIFIED).addToSet(ConfigurationStrings.RATING).as(ConfigurationStrings.RATING)
        );


        AggregationResults<EmployeeSkill> groupResults
                = mongoOperation.aggregate(agg, EmployeeSkill.class, EmployeeSkill.class);

        List<EmployeeSkill> result = groupResults.getMappedResults();
        List<EmployeeSkillDomain> subSkills = new LinkedList<>();
        for (EmployeeSkill i : result) {
            SubSkill temp = subSkillRepository.findOne(i.getId());
            SubSkillDomain subSkill = new SubSkillDomain(temp.getId(), temp.getSubSkill(), temp.getSubSkillDesc(), temp.getModelSkill(), temp.getSkillGroup(), temp.getPractice(), 0);
            EmployeeSkillDomain toAdd = new EmployeeSkillDomain(empId, subSkill, i.getRating(), i.getLastModifiedDate());
            subSkills.add(toAdd);
        }

        return subSkills;
    }

    public List<SkillReport> employeesWhoUpdatedSubSkillsInLastXMonths(long from, long to) throws MongoException {


        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(from);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(to);

        Aggregation agg = newAggregation(match(Criteria.where(ConfigurationStrings.LASTMODIFIED).gt(cal.getTime()).lt(cal2.getTime())),
                group(ConfigurationStrings.EMPID, ConfigurationStrings.SUBSKILLID).max(ConfigurationStrings.LASTMODIFIED).as("maxDate").last(ConfigurationStrings.RATING).as("lastRating").
                        min(ConfigurationStrings.LASTMODIFIED).as("minDate").first(ConfigurationStrings.RATING).as("firstRating"),
                sort(Sort.Direction.ASC, ConfigurationStrings.EMPID));


        AggregationResults<SkillReport> groupResults
                = mongoOperation.aggregate(agg, EmployeeSkill.class, SkillReport.class);

        return groupResults.getMappedResults();

    }

    public List<EmployeeCertificationDomain> certificatesExpiringInNextNmonths(long from, long to) throws MongoException {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(from);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(to);
        Query query = new Query(Criteria.where("certificationValidityDate").gt(cal.getTime()).lt(cal2.getTime()));
        List<EmployeeCertification> employeeCertifications = mongoOperation.find(query, EmployeeCertification.class);
        List<EmployeeCertificationDomain> employeeCertDomains = new LinkedList<>();
        for (EmployeeCertification i : employeeCertifications) {
            Certification cert = certificationRepository.findById(i.getCertificationId());
            CertificationDomain certDomain = new CertificationDomain(cert.getId(), cert.getSkillId(), cert.getCertificationName(), cert.getInstitution());
            employeeCertDomains.add(new EmployeeCertificationDomain(i.getEmployeeId(), certDomain, i.getCertificationDate(), i.getCertificationValidityDate(), i.getCertificationNumber(), i.getCertificationUrl()));
        }
        return employeeCertDomains;
    }

    public List<String> employeesWithASkill() throws MongoException {
        return mongoOperation.getCollection("employeeskill").distinct(ConfigurationStrings.EMPID);

    }


}
