package com.openclassrooms.p9.clientUI.proxies;

import com.openclassrooms.p9.clientUI.beans.PatientBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "microservice-patient", url = "localhost:8100")
public interface MicroServicePatientProxy {

    @GetMapping(value = "/Patients")
    public List<PatientBean> listeDesPatients();

    @GetMapping(value = "/Patients/{id}")
    public PatientBean recupererUnPatient(@PathVariable("id") int id) ;


}
