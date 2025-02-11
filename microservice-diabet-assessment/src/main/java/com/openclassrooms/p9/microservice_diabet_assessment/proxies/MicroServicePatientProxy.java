package com.openclassrooms.p9.microservice_diabet_assessment.proxies;

import com.openclassrooms.p9.microservice_diabet_assessment.beans.NotePatientBean;
import com.openclassrooms.p9.microservice_diabet_assessment.beans.PatientBean;
import com.openclassrooms.p9.microservice_diabet_assessment.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "microservice-gateway", url = "${feign.client.url.microservice-gateway}",  configuration = FeignClientConfig.class)
public interface MicroServicePatientProxy {

    @GetMapping(value = "/Patients/{id}")
    public PatientBean recupererUnPatient(@PathVariable("id") int id) ;

    @GetMapping(value = "/NotesPatients/Patient/{id}")
    public Optional<List<NotePatientBean>> recupererNotesPatient(@PathVariable("id") int id) ;


}
