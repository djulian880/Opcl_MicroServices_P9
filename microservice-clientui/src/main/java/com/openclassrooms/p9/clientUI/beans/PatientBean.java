package com.openclassrooms.p9.clientUI.beans;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientBean{

    private Integer id;
    private String prenom;
    private String nom;
    private String dateDeNaissance;
    private String genre;
    private String adressePostale;
    private String numeroDeTelephone;


}
