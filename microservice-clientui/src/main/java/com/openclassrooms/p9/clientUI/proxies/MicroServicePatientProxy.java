package com.openclassrooms.p9.clientUI.proxies;

import com.openclassrooms.p9.clientUI.beans.PatientBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "microservice-gateway", url = "localhost:8080")
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



}
