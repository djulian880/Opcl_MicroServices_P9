package com.openclassrooms.p9.microservice_patient.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidPatient() {
        Patient patient = new Patient();
        patient.setPrenom("Jean");
        patient.setNom("Dupont");
        patient.setDateDeNaissance(LocalDate.of(1990, 5, 15));
        patient.setGenre("M");
        patient.setAdressePostale("10 rue de Paris");
        patient.setNumeroDeTelephone("0123456789");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);
        assertThat(violations).isEmpty();
    }

    @Test
    void testInvalidPatient_MissingFields() {
        Patient patient = new Patient(); // Aucun champ renseigné

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

        // Vérifie que toutes les contraintes sont violées
        assertThat(violations).hasSize(4);

        // Vérifie que les messages d'erreur sont bien renvoyés
        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains(
                        "Le prénom est obligatoire",
                        "Le nom est obligatoire",
                        "La date de naissance est obligatoire",
                        "Le genre est obligatoire"
                );
    }

    @Test
    void testInvalidPatient_PastDate() {
        Patient patient = new Patient();
        patient.setPrenom("Alice");
        patient.setNom("Martin");
        patient.setDateDeNaissance(LocalDate.of(2027, 1, 1)); // Date future (invalide)
        patient.setGenre("F");

        Set<ConstraintViolation<Patient>> violations = validator.validate(patient);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("doit être une date dans le passé");
    }
}