package com.openclassrooms.p9.clientUI.controller;

import com.openclassrooms.p9.clientUI.DTO.PatientDTO;
import com.openclassrooms.p9.clientUI.beans.NotePatientBean;
import com.openclassrooms.p9.clientUI.beans.PatientBean;
import com.openclassrooms.p9.clientUI.proxies.MicroServicePatientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class ClientUIController {

    private final MicroServicePatientProxy patientsProxy;

    public ClientUIController(MicroServicePatientProxy patientsProxy){
        this.patientsProxy = patientsProxy;
    }

    @RequestMapping("/")
    public String accueil(Model model){
        List<PatientBean> patients =  patientsProxy.listeDesPatients();

        List<PatientDTO> patientsDTO= new ArrayList<>();
        for(PatientBean patient: patients){
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(patient.getId());
            patientDTO.setNom(patient.getNom());
            patientDTO.setPrenom(patient.getPrenom());
            patientDTO.setGenre(patient.getGenre());
            patientDTO.setAdressePostale(patient.getAdressePostale());
            patientDTO.setDateDeNaissance(patient.getDateDeNaissance());
            patientDTO.setNumeroDeTelephone(patient.getNumeroDeTelephone());
            patientDTO.setRapportDiabete(patientsProxy.recupererRapportDiabete(patient.getId()));
            patientsDTO.add(patientDTO);
        }

        model.addAttribute("patients", patientsDTO);
        return "Accueil";
    }

    @GetMapping("/details-patient/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<PatientBean> patient =Optional.of(patientsProxy.recupererUnPatient(id));
        if (patient.isPresent()) {
            PatientBean patientFound = patient.get();
            model.addAttribute("patient", patientFound);
            return "FichePatient";
        } else {
            log.error("Patient with id {} not found", id);
            return "FichePatient";
        }
    }

    @PostMapping("/details-patient/update/{id}")
    public String updatePatient(@PathVariable("id") Integer id, PatientBean patient) {
        Optional<PatientBean> patientUpdated =Optional.of(patientsProxy.mettreAJourUnPatient(id,patient));
        if (patientUpdated.isPresent()) {

            return "redirect:http://localhost:8080";
        } else {
            log.error("Patient with id {} not found", id);
            return "redirect:http://localhost:8080";
        }
    }

    @GetMapping("/details-patient/add")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new PatientBean());
            return "AjoutPatient";
    }

    @PostMapping("/details-patient/add")
    public String addPatient( PatientBean patient) {
        patientsProxy.AjouterUnPatient(patient);
        return "redirect:http://localhost:8080";
    }

    @GetMapping("/details-patient/remove/{id}")
    public String showAddPatientForm(@PathVariable("id") Integer id) {
        patientsProxy.supprimerUnPatient(id);
        Optional<List<NotePatientBean>> listeNotesRecuperees=patientsProxy.recupererNotesPatient(id);
        if(listeNotesRecuperees.isPresent()){
            List<NotePatientBean> listeNotes=listeNotesRecuperees.get();
            for (NotePatientBean notePatient : listeNotes) {
                patientsProxy.SupprimerNotePatient(notePatient.getId());
            }
        }

        return "redirect:http://localhost:8080";
    }

    @GetMapping("/notes-patient/{id}")
    public String showNotesForm(@PathVariable("id") Integer id, Model model) {
        Optional<PatientBean> patient =Optional.of(patientsProxy.recupererUnPatient(id));
        if (patient.isPresent()) {
            PatientBean patientFound = patient.get();

            Optional<List<NotePatientBean>> listeNotesRecuperees=patientsProxy.recupererNotesPatient(patientFound.getId());
            if(listeNotesRecuperees.isPresent()){
                List<NotePatientBean> listeNotes=listeNotesRecuperees.get();
                model.addAttribute("listeNotes", listeNotes);
            }
            model.addAttribute("patient", patientFound);
            model.addAttribute("notePatient", new NotePatientBean());
            return "NotePatient";
        } else {
            log.error("Patient with id {} not found", id);
            return "NotePatient";
        }
    }

    @PostMapping("/notes-patient/{id}")
    public String addNotePatient( @PathVariable("id") Integer id,NotePatientBean notePatient) {
        notePatient.setIdPatient(id);
        Optional<PatientBean> patient =Optional.of(patientsProxy.recupererUnPatient(id));
        if (patient.isPresent()) {
            notePatient.setNomPatient(patient.get().getNom());
            notePatient.setId(null);
        }
        patientsProxy.AjouterNoteAUnPatient(notePatient,id);
        return "redirect:http://localhost:8080/notes-patient/"+id;
    }

    @GetMapping("/notes-patient/remove/{id}")
    public String removeNotePatient(@PathVariable("id") String id) {
        NotePatientBean notePatient=patientsProxy.recupererNotePatient(id);
        patientsProxy.SupprimerNotePatient(id);
        return "redirect:http://localhost:8080/notes-patient/"+notePatient.getIdPatient();
    }

}
