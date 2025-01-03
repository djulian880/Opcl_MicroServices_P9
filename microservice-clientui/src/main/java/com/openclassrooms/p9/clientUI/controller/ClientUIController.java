package com.openclassrooms.p9.clientUI.controller;

import com.openclassrooms.p9.clientUI.beans.PatientBean;
import com.openclassrooms.p9.clientUI.proxies.MicroServicePatientProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientUIController {

    private final MicroServicePatientProxy patientsProxy;

    public ClientUIController(MicroServicePatientProxy patientsProxy){
        this.patientsProxy = patientsProxy;
    }

    @RequestMapping("/")
    public String accueil(Model model){
        List<PatientBean> patients =  patientsProxy.listeDesPatients();
        model.addAttribute("patients", patients);
        return "Accueil";
    }

    /*
    @RequestMapping("/details-patient/{id}")
    public String fichePatient(@PathVariable int id, Model model){
        PatientBean patient = patientsProxy.recupererUnPatient(id);
        model.addAttribute("patient", patient);
        return "FichePatient";
    }*/

    @GetMapping("/details-patient/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<PatientBean> patient =Optional.of(patientsProxy.recupererUnPatient(id));;
        if (patient.isPresent()) {
            PatientBean patientFound = patient.get();
            model.addAttribute("patient", patientFound);
            return "FichePatient";
        } else {
            //log.error("Patient with id {} not found", id);
            return "FichePatient";
        }
    }

}
