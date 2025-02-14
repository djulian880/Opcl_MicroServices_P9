package com.openclassrooms.p9.microservice_diabet_assessment.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotePatientBean {
    private String id;
    private Integer idPatient;
    private String nomPatient;
    private String contenu;

}
