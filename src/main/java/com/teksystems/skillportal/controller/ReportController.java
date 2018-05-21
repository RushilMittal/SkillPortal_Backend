package com.teksystems.skillportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teksystems.skillportal.domain.EmployeeCertificationDomain;
import com.teksystems.skillportal.domain.EmployeeSkillDomain;
import com.teksystems.skillportal.domain.SkillReport;
import com.teksystems.skillportal.domain.SubSkillDomain;
import com.teksystems.skillportal.service.ReportService;

@RestController
@RequestMapping(value="/report",method= {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin("*")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@GetMapping("/reportskill")
	public List<SubSkillDomain> topNSubSkill(@RequestParam int n)
	{
		return reportService.topNSubSkills(n);
	}
	
	@GetMapping("/reportskilltrend")
	public List<SubSkillDomain> topNSubSkillinLastXMonths(@RequestParam int n,@RequestParam int x)
	{
		return reportService.topNSubSkillsinLastXMonths(n,x);
	}
	
	@GetMapping("/reportskillofemployee")
	public List<EmployeeSkillDomain> topNSubSkillinLastXMonths(@RequestParam String empId)
	{
		return reportService.skillsOfEmployee(empId);
	}
	
	@GetMapping("/reportemployeeupdation")
	public List<SkillReport> UpdatedByEmpSubSkillinLastXMonths(@RequestParam int x)
	{
		return reportService.EmployeesWhoUpdatedSubSkillsinLastXMonths(x);
	}
	
	@GetMapping("/reportcertificateexpiry")
	public List<EmployeeCertificationDomain> certificatesExipringInNextNmonths(@RequestParam int n)
	{
		return reportService.CertificatesExipringInNextNmonths(n);
	}
	@GetMapping("/getemployees")
	public List<String> EmployeesWithASkill()
	{
		return reportService.EmployeesWithASkill();
	}

}
