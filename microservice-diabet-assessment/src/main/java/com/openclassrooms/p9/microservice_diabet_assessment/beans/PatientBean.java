package com.openclassrooms.p9.microservice_diabet_assessment.beans;

import lombok.Getter;
import lombok.Setter;

//@Data
@Getter
@Setter
public class PatientBean {

    private Integer id;
    private String prenom;
    private String nom;
    private String dateDeNaissance;
    private String genre;
    private String adressePostale;
    private String numeroDeTelephone;

}
