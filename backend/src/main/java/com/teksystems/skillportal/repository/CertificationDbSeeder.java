package com.teksystems.skillportal.repository;

import com.teksystems.skillportal.model.Certification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class CertificationDbSeeder implements CommandLineRunner {

    @Autowired
    private CertificationRepository certificationRepository;

    @Override
    public void run(String... args) throws Exception {

        Certification c1 = new Certification (
        		"101",
        		"1",
                "Amazon Web Services",
                "Amazon"
        );

        Certification c2 = new Certification(
        		"102",
        		"2",
                "AutoCAD",
                "Autodesk"
        );

        Certification c3 = new Certification(
        		"103",
        		"1",
                "Linux",
                "RedHat"
        );

        Certification c4 = new Certification(
        		"104",
        		"6",
                "Android",
                "Google"
        );

        Certification c5 = new Certification(
        		"105",
        		"7",
                 "C#",
                "Microsoft"
        );

        Certification c6 = new Certification(
        		"106",
        		"1",
                "Java",
                "Oracle"
        );
        
        Certification c7 = new Certification(
        		"107",
        		"10",
                "Matlab",
                "Mathworks"
        );
        
        Certification c8 = new Certification(
        		"108",
        		"3",
                "Photoshop",
                "Adobe"
        );
        
        Certification c9 = new Certification(
        		"109",
        		"9",
                "Neural Networks",
                "OpenAI"
        );
        
        Certification c10 = new Certification(
        		"110",
        		"8",
                "Objective-C",
                "Apple"
        );
        
        this.certificationRepository.deleteAll();

        List<Certification> certifications = Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
        certificationRepository.save(certifications);

    }

}
