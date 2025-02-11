package com.openclassrooms.p9.microservice_diabet_assessment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.openclassrooms.p9.microservice_diabet_assessment.beans.NotePatientBean;
import com.openclassrooms.p9.microservice_diabet_assessment.beans.PatientBean;
import com.openclassrooms.p9.microservice_diabet_assessment.proxies.MicroServicePatientProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class DiabetAssessmentServiceTest {

    @Mock
    private MicroServicePatientProxy patientsProxy;

    @InjectMocks
    private DiabetAssessmentService diabetAssessmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReturnAssessment_Borderline() {
        assertEquals("BorderLine", diabetAssessmentService.returnAssessment(35, "M", 3));
    }

    @Test
    void testReturnAssessment_InDanger_YoungMale() {
        assertEquals("InDanger", diabetAssessmentService.returnAssessment(25, "M", 3));
    }

    @Test
    void testReturnAssessment_EarlyOnset_Female() {
        assertEquals("EarlyOnSet", diabetAssessmentService.returnAssessment(27, "F", 7));
    }

    @Test
    void testReturnAssessment_None() {
        assertEquals("None", diabetAssessmentService.returnAssessment(40, "F", 0));
    }

    @Test
    void testGetAssessmentReport() {
        int idPatient = 1;
        PatientBean mockPatient = new PatientBean();
        mockPatient.setId(idPatient);
        mockPatient.setNom("Doe");
        mockPatient.setPrenom("Jane");
        mockPatient.setGenre("F");
        mockPatient.setDateDeNaissance(LocalDate.of(1990, 5, 15).toString());

        NotePatientBean note1 = new NotePatientBean();
        note1.setContenu("Patient has high glucose level");
        NotePatientBean note2 = new NotePatientBean();
        note2.setContenu("Frequent urination and thirst detected");

        when(patientsProxy.recupererUnPatient(idPatient)).thenReturn(mockPatient);
        when(patientsProxy.recupererNotesPatient(idPatient)).thenReturn(Optional.of(List.of(note1, note2)));

        String assessment = diabetAssessmentService.getAssessmentReport(idPatient);

        assertNotNull(assessment);
        System.out.println("Assessment: " + assessment);
    }

    @Test
    void testValidAssessmentReport() {

        // Premier patient
        PatientBean mockPatient1 = new PatientBean();
        mockPatient1.setId(1);
        mockPatient1.setNom("TestNone");
        mockPatient1.setPrenom("Test");
        mockPatient1.setGenre("F");
        mockPatient1.setDateDeNaissance(LocalDate.of(1966, 12, 31).toString());
        NotePatientBean note1 = new NotePatientBean();
        note1.setContenu("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur à la recommandation");
        when(patientsProxy.recupererUnPatient(1)).thenReturn(mockPatient1);
        when(patientsProxy.recupererNotesPatient(1)).thenReturn(Optional.of(List.of(note1)));

        // Deuxième patient
        PatientBean mockPatient2 = new PatientBean();
        mockPatient2.setId(2);
        mockPatient2.setNom("TestBorderLine");
        mockPatient2.setPrenom("Test");
        mockPatient2.setGenre("M");
        mockPatient2.setDateDeNaissance(LocalDate.of(1945, 06, 24).toString());
        NotePatientBean note21 = new NotePatientBean();
        note21.setContenu("Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement");
        NotePatientBean note22 = new NotePatientBean();
        note22.setContenu("Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale");
        when(patientsProxy.recupererUnPatient(2)).thenReturn(mockPatient2);
        when(patientsProxy.recupererNotesPatient(2)).thenReturn(Optional.of(List.of(note21,note22)));

        // Troisième patient
        PatientBean mockPatient3 = new PatientBean();
        mockPatient3.setId(3);
        mockPatient3.setNom("TestInDanger");
        mockPatient3.setPrenom("Test");
        mockPatient3.setGenre("M");
        mockPatient3.setDateDeNaissance(LocalDate.of(2004, 06, 18).toString());
        NotePatientBean note31 = new NotePatientBean();
        note31.setContenu("Le patient déclare qu'il fume depuis peu");
        NotePatientBean note32 = new NotePatientBean();
        note32.setContenu("Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        when(patientsProxy.recupererUnPatient(3)).thenReturn(mockPatient3);
        when(patientsProxy.recupererNotesPatient(3)).thenReturn(Optional.of(List.of(note31,note32)));

        // Quatrième patient
        PatientBean mockPatient4 = new PatientBean();
        mockPatient4.setId(4);
        mockPatient4.setNom("TestInDanger");
        mockPatient4.setPrenom("Test");
        mockPatient4.setGenre("M");
        mockPatient4.setDateDeNaissance(LocalDate.of(2004, 06, 18).toString());
        NotePatientBean note41 = new NotePatientBean();
        note41.setContenu("Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments");
        NotePatientBean note42 = new NotePatientBean();
        note42.setContenu("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps");
        NotePatientBean note43 = new NotePatientBean();
        note43.setContenu("Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé");
        NotePatientBean note44 = new NotePatientBean();
        note44.setContenu("Taille, Poids, Cholestérol, Vertige et Réaction");
        when(patientsProxy.recupererUnPatient(4)).thenReturn(mockPatient4);
        when(patientsProxy.recupererNotesPatient(4)).thenReturn(Optional.of(List.of(note41,note42,note43,note44)));

        assertEquals("None",diabetAssessmentService.getAssessmentReport(1));
        assertEquals("BorderLine",diabetAssessmentService.getAssessmentReport(2));
        assertEquals("InDanger",diabetAssessmentService.getAssessmentReport(3));
        assertEquals("EarlyOnSet",diabetAssessmentService.getAssessmentReport(4));
    }
}
