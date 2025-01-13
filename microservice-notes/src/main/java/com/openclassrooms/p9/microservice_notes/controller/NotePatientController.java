package com.openclassrooms.p9.microservice_notes.controller;

import com.openclassrooms.p9.microservice_notes.model.NotePatient;
import com.openclassrooms.p9.microservice_notes.repository.NotePatientRepository;
import com.openclassrooms.p9.microservice_notes.service.NotePatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
public class NotePatientController {

    @Autowired
    NotePatientService notePatientService;

    @Autowired
    NotePatientRepository notePatientRepository;

    @GetMapping("/NotesPatients")
    public List<NotePatient> listAll()
    {
        return notePatientRepository.findAll();
    }

    /*
    @GetMapping(value = "/NotesPatients/{id}")
    public List<NotePatient> showNotePatient(@PathVariable int id) {


            List<NotePatient> notes = notePatientService.getNotesByIdPatient(id);
        //notes.forEach(log::INFO);
        notes.stream().forEach((NotePatient) -> log.info(NotePatient.getContenu()));
        return notes;

    }*/

    @GetMapping(value = "/NotesPatients/{idPatient}")
    public ResponseEntity<List<NotePatient>> getNotesByIdPatient(@PathVariable Integer idPatient) {

        List<NotePatient> notes = notePatientService.getNotesByIdPatient(idPatient);
        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();  // Aucun résultat
        }
        return ResponseEntity.ok(notes);  // Retourne les notes dans la réponse
    }

    /*
    @PutMapping(value = "/NotesPatients/{idPatient}")
    public ResponseEntity<NotePatient> updatePatient(@Valid @RequestBody NotePatient notePatient, @PathVariable int idPatient) {
        Optional<Patient> patientFound = patientService.getPatientById(id);
        if (patientFound.isPresent()) {
            patient.setId(id);
            Patient patientUpdated = patientService.savePatient(patient);

            if (Objects.isNull(patientUpdated)) {
                return ResponseEntity.noContent().build();
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(patientUpdated.getId())
                    .toUri();
            return ResponseEntity.ok(patientUpdated);
        }
        else{
            throw new PatientNotFoundException();
        }
    }*/

    @PostMapping(value = "/NotesPatients/{idPatient}")
    public ResponseEntity<NotePatient> addNotePatient(@RequestBody NotePatient notePatient,@PathVariable Integer idPatient) {
        //log.info(idPatient.toString());

        notePatient.setIdPatient(idPatient);
        NotePatient notePatientAdded = notePatientService.saveNotePatient(notePatient);
        //log.info("Requete envoyée");
        if (Objects.isNull(notePatientAdded)) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(notePatientAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

/*
    @DeleteMapping ("/Patients/{id}")
    public void deletePatient(@PathVariable("id") Integer id) {
        Optional<Patient> patientFound = patientService.getPatientById(id);
        if (patientFound.isPresent()) {
            patientService.deletePatientById(id);
        }
        else {
            throw new PatientNotFoundException();
        }
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class PatientNotFoundException extends RuntimeException {
        public PatientNotFoundException() {
            super("Patient not found");
        }
    }
    */

}
