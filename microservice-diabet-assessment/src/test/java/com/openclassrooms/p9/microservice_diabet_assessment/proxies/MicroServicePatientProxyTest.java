package com.openclassrooms.p9.microservice_diabet_assessment.proxies;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;


import com.openclassrooms.p9.microservice_diabet_assessment.beans.PatientBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import java.util.List;

@SpringJUnitConfig
@WebMvcTest(MicroServicePatientProxy.class) // Charge uniquement le proxy
@EnableFeignClients(clients = MicroServicePatientProxy.class) // Active le client Feign

class MicroServicePatientProxyTest {

    private WireMockServer wireMockServer;

    @Autowired
    private MicroServicePatientProxy patientProxy;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8081));
        wireMockServer.start();
    }

    @Test
    void testRecupererUnPatient() {
        // Simuler une réponse JSON pour un patient
        wireMockServer.stubFor(get(urlEqualTo("/Patients/1"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": 1, \"nom\": \"Doe\", \"prenom\": \"John\" }")
                        .withStatus(200)));

        // Appeler le proxy
        PatientBean patient = patientProxy.recupererUnPatient(1);

        // Vérifier la réponse
        assertNotNull(patient);
        assertEquals(1, patient.getId());
        assertEquals("Doe", patient.getNom());
        assertEquals("John", patient.getPrenom());
    }

    @Test
    void testListeDesPatients() {
        wireMockServer.stubFor(get(urlEqualTo("/Patients"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{ \"id\": 1, \"nom\": \"Doe\", \"prenom\": \"John\" }, " +
                                "{ \"id\": 2, \"nom\": \"Smith\", \"prenom\": \"Alice\" }]")
                        .withStatus(200)));

        List<PatientBean> patients = patientProxy.listeDesPatients();

        assertNotNull(patients);
        assertEquals(2, patients.size());
        assertEquals("Doe", patients.get(0).getNom());
    }
}

