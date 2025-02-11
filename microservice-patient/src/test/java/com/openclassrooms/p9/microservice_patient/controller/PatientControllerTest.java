package com.openclassrooms.p9.microservice_patient.controller;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.p9.microservice_patient.model.Patient;
import com.openclassrooms.p9.microservice_patient.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());  // Supporte LocalDate


    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private Patient patient;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        patient = new Patient();
        patient.setId(1);
        patient.setPrenom("Jean");
        patient.setNom("Dupont");
        patient.setDateDeNaissance(LocalDate.of(1990, 5, 15));
        patient.setGenre("M");
        patient.setAdressePostale("10 rue de Paris");
        patient.setNumeroDeTelephone("0123456789");
    }

    @Test
    void testListAllPatients() throws Exception {
        when(patientService.getAll()).thenReturn(Arrays.asList(patient));

        mockMvc.perform(get("/Patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].prenom").value("Jean"));
    }

    @Test
    void testShowPatient_Found() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/Patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Dupont"));
    }

    @Test
    void testShowPatient_NotFound() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/Patients/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddPatient() throws Exception {
        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/Patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdatePatient_Found() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(Optional.of(patient));
        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(put("/Patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenom").value("Jean"));
    }

    @Test
    void testUpdatePatient_NotFound() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(Optional.empty());

        mockMvc.perform(put("/Patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePatient_Found() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(Optional.of(patient));
        doNothing().when(patientService).deletePatientById(1);

        mockMvc.perform(delete("/Patients/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePatient_NotFound() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/Patients/1"))
                .andExpect(status().isNotFound());
    }
}
