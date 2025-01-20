package com.openclassrooms.p9.microservice_diabet_assessment.proxies;

import com.openclassrooms.p9.microservice_diabet_assessment.beans.NotePatientBean;
import com.openclassrooms.p9.microservice_diabet_assessment.beans.PatientBean;
import com.openclassrooms.p9.microservice_diabet_assessment.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservice-gateway", url = "localhost:8080",  configuration = FeignClientConfig.class)
public interface MicroServicePatientProxy {

    @GetMapping(value = "/Patients")
    public List<PatientBean> listeDesPatients();

    @GetMapping(value = "/Patients/{id}")
    public PatientBean recupererUnPatient(@PathVariable("id") int id) ;

    @PutMapping(value = "/Patients/{id}")
    public PatientBean mettreAJourUnPatient(@PathVariable("id") int id,@RequestBody PatientBean patient) ;

    @PostMapping(value = "/Patients")
    public void AjouterUnPatient(@RequestBody PatientBean patient) ;

    @DeleteMapping(value = "/Patients/{id}")
    public void supprimerUnPatient(@PathVariable("id") int id) ;

    @GetMapping(value = "/NotesPatients/Patient/{id}")
    public List<NotePatientBean> recupererNotesPatient(@PathVariable("id") int id) ;

    @PostMapping(value = "/NotesPatients/Patient/{id}")
    public void AjouterNoteAUnPatient(@RequestBody NotePatientBean notePatient,@PathVariable("id") int id) ;

    @DeleteMapping(value = "/NotesPatients/{id}")
    public void SupprimerNotePatient(@PathVariable("id") String id) ;

    @GetMapping(value = "/NotesPatients/{id}")
    public NotePatientBean recupererNotePatient(@PathVariable("id") String id) ;
}
