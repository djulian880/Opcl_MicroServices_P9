package com.openclassrooms.p9.microservice_notes.service;

import com.openclassrooms.p9.microservice_notes.model.NotePatient;
import com.openclassrooms.p9.microservice_notes.repository.NotePatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotePatientService {
    @Autowired
    NotePatientRepository notePatientRepository;

    public List<NotePatient> getNotesByIdPatient(Integer id) {
        return notePatientRepository.findByIdPatient(id);
    }

    public Optional<NotePatient> getNotePatientById(String id) {
        return notePatientRepository.findById(id);
    }

    public NotePatient saveNotePatient(NotePatient notePatient) {
        return notePatientRepository.save(notePatient);
    }

    public void deleteNotePatientById(String id) {
        notePatientRepository.deleteById(id);
    }

}
