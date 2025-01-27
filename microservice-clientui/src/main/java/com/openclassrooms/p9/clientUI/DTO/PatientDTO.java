package com.openclassrooms.p9.clientUI.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    private Integer id;
    private String prenom;
    private String nom;
    private String dateDeNaissance;
    private String genre;
    private String adressePostale;
    private String numeroDeTelephone;
    private String rapportDiabete;
}
