package com.openclassrooms.p9.microservice_notes.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "notespatients")
public class NotePatient {

    @Id
    private String id;

    private Integer idPatient;
    private String nomPatient;
    private String contenu;

}
