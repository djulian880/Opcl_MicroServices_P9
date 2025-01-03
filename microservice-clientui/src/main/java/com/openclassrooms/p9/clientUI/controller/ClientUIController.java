package com.openclassrooms.p9.clientUI.controller;

import com.openclassrooms.p9.clientUI.beans.PatientBean;
import com.openclassrooms.p9.clientUI.proxies.MicroServicePatientProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @PostMapping("/details-patient/update/{id}")
    public String updatePatient(@PathVariable("id") Integer id, PatientBean patient, Model model) {
        Optional<PatientBean> patientUpdated =Optional.of(patientsProxy.mettreAJourUnPatient(id,patient));;
        if (patientUpdated.isPresent()) {

            return "redirect:/";
        } else {
            //log.error("Patient with id {} not found", id);
            return "redirect:/";
        }
    }

    @GetMapping("/details-patient/add")
    public String showAddPatientForm( PatientBean patient, Model model) {
        model.addAttribute("patient", new PatientBean());
            return "AjoutPatient";
    }

    @PostMapping("/details-patient/add")
    public String addPatient( PatientBean patient, Model model) {
        patientsProxy.AjouterUnPatient(patient);
        /*Optional<PatientBean> patientAdded =Optional.of(patientsProxy.AjouterUnPatient(patient));;
        if (patientAdded.isPresent()) {

            return "redirect:/";
        } else {
            //log.error("Patient with id {} not found", id);
            return "redirect:/";
        }*/
        return "redirect:/";
    }

    @GetMapping("/details-patient/remove/{id}")
    public String showAddPatientForm(@PathVariable("id") Integer id) {
        patientsProxy.supprimerUnPatient(id);
        return "redirect:/";
    }


}
