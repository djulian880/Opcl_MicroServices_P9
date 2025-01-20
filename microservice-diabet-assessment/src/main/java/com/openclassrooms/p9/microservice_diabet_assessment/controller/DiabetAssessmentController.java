package com.openclassrooms.p9.microservice_diabet_assessment.controller;

import com.openclassrooms.p9.microservice_diabet_assessment.beans.PatientBean;
import com.openclassrooms.p9.microservice_diabet_assessment.proxies.MicroServicePatientProxy;
import com.openclassrooms.p9.microservice_diabet_assessment.service.DiabetAssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@Controller
public class DiabetAssessmentController {

    @Autowired
    DiabetAssessmentService diabetAssessmentService;

    @GetMapping(value = "/Patients/diabet/{id}")
    public ResponseEntity<String> assessDiabetRisk(@PathVariable Integer id) {
            return ResponseEntity.ok(diabetAssessmentService.getAssessmentReport(id));
    }

}
