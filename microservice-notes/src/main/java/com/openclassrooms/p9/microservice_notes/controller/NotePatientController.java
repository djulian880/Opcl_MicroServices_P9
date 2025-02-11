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

    // Show a note by NoteId
    @GetMapping(value = "/NotesPatients/{id}")
    public ResponseEntity<NotePatient> showNotePatient(@PathVariable String id) {
        Optional<NotePatient> note = notePatientService.getNotePatientById(id);
        if(note.isPresent()){
            System.out.println("Note trouvée");
            NotePatient noteFound = note.get();
            return ResponseEntity.ok(noteFound);
        }
        return ResponseEntity.noContent().build();

    }

    // Show a note by id of Patient
    @GetMapping(value = "/NotesPatients/Patient/{idPatient}")
    public ResponseEntity<List<NotePatient>> getNotesByIdPatient(@PathVariable Integer idPatient) {

        List<NotePatient> notes = notePatientService.getNotesByIdPatient(idPatient);
        if (notes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notes);
    }

    // Update a note by id
    @PutMapping(value = "/NotesPatients/{id}")
    public ResponseEntity<NotePatient> updateNotePatient(@RequestBody NotePatient notePatient, @PathVariable String id) {
        Optional<NotePatient> notePatientFound = notePatientService.getNotePatientById(id);
        if (notePatientFound.isPresent()) {

            NotePatient notePatientUpdated = notePatientService.saveNotePatient(notePatient);

            if (Objects.isNull(notePatientUpdated)) {
                return ResponseEntity.noContent().build();
            }

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(notePatientUpdated.getId())
                    .toUri();
            return ResponseEntity.ok(notePatientUpdated);
        }
        else{
            throw new NotePatientNotFoundException();
        }
    }

    @PostMapping(value = "/NotesPatients/Patient/{idPatient}")
    public ResponseEntity<NotePatient> addNotePatient(@RequestBody NotePatient notePatient,@PathVariable Integer idPatient) {
        notePatient.setIdPatient(idPatient);
        NotePatient notePatientAdded = notePatientService.saveNotePatient(notePatient);
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


    @DeleteMapping ("/NotesPatients/{id}")
    public ResponseEntity<Void> deleteNotePatient(@PathVariable("id") String id) {
        Optional<NotePatient> notePatientFound = notePatientService.getNotePatientById(id);
        if (notePatientFound.isPresent()) {
            notePatientService.deleteNotePatientById(id);
            return ResponseEntity.ok().build();
        }
        else {
            throw new NotePatientNotFoundException();
        }
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotePatientNotFoundException extends RuntimeException {
        public NotePatientNotFoundException() {
            super("NotePatient not found");
        }
    }


}
