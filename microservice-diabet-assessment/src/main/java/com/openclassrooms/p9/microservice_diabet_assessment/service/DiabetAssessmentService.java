package com.openclassrooms.p9.microservice_diabet_assessment.service;

import com.openclassrooms.p9.microservice_diabet_assessment.beans.NotePatientBean;
import com.openclassrooms.p9.microservice_diabet_assessment.beans.PatientBean;
import com.openclassrooms.p9.microservice_diabet_assessment.model.Terms;
import com.openclassrooms.p9.microservice_diabet_assessment.proxies.MicroServicePatientProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class DiabetAssessmentService {

    private final MicroServicePatientProxy patientsProxy;

    public DiabetAssessmentService(MicroServicePatientProxy patientsProxy){
        this.patientsProxy = patientsProxy;
    }

    public String getAssessmentReport(Integer idPatient){

        PatientBean patient=patientsProxy.recupererUnPatient(idPatient);
        log.debug("**** "+patient.getNom()+" "+patient.getPrenom());

        long nbOccurencesTermes=0;
        Optional<List<NotePatientBean>> notesPatientlist=patientsProxy.recupererNotesPatient(idPatient);
        if(notesPatientlist.isPresent()) {
            List<NotePatientBean> notesPatient=notesPatientlist.get();
            log.debug("Nombre de notes:"+notesPatient.size());
            for (NotePatientBean notePatient : notesPatient) {
                log.debug("Contenu d'une note:" + notePatient.getContenu());
                long count = countOccurrencesOfTerm(notePatient.getContenu());
                nbOccurencesTermes += count;
                log.debug("Nb occurence:" + count);
            }
        }
        log.debug("Nombre d'occurences:"+nbOccurencesTermes);
        log.debug("Genre:"+patient.getGenre());

        return returnAssessment(calculerAge(patient.getDateDeNaissance()),patient.getGenre(),nbOccurencesTermes);
    }



    // aucun risque (None) : Le dossier du patient ne contient aucune note du médecin
    // contenant les déclencheurs (terminologie) ;

    // risque limité (Borderline) : Le dossier du patient contient entre deux et cinq
    // déclencheurs et le patient est âgé de plus de 30 ans ;

    // danger (In Danger) : Dépend de l'âge et du sexe du patient.
    // Si le patient est un homme de moins de 30 ans, alors trois termes déclencheurs doivent être présents.
    // Si le patient est une femme et a moins de 30 ans, il faudra quatre termes déclencheurs.
    // Si le patient a plus de 30 ans, alors il en faudra six ou sept ;

    // apparition précoce (Early onset) : Encore une fois, cela dépend de l'âge et du sexe.
    // Si le patient est un homme de moins de 30 ans, alors au moins cinq termes déclencheurs sont nécessaires.
    // Si le patient est une femme et a moins de 30 ans, il faudra au moins sept termes déclencheurs.
    // Si le patient a plus de 30 ans, alors il en faudra huit ou plus.

    public String returnAssessment(int age,String gender, long countOfOccurence){
        if (countOfOccurence==0){
            return "None";
        }
        else {
            if (age > 30) {
                if (countOfOccurence >= 2 && countOfOccurence <= 5) {
                    return "BorderLine";
                } else if (countOfOccurence >= 6 && countOfOccurence <= 7) {
                    return "InDanger";
                } else if (countOfOccurence >= 8) {
                    return "EarlyOnSet";
                }
            } else {
                if (gender.contentEquals("F")) {
                    if (countOfOccurence >= 4 && countOfOccurence <= 6) {
                        return "InDanger";
                    } else if (countOfOccurence >= 7) {
                        return "EarlyOnSet";
                    }
                } else if (gender.contentEquals("M")) {
                    if (countOfOccurence >= 3 && countOfOccurence <= 4) {
                        return "InDanger";
                    } else if (countOfOccurence >= 5) {
                        return "EarlyOnSet";
                    }
                }

            }
        }
        return "None";
    }

    public static long countOccurrencesOfTerm(String content){
        long count = 0;
        for (String terme : Terms.liste) {
            count += countOccurrencesWithPattern(content,terme);
        }
        return count;
    }

    public static long countOccurrencesWithPattern(String content, String item) {
        Pattern pattern = Pattern.compile(Pattern.quote(item), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);

        long count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
    }

    public static int calculerAge(String dateNaissanceStr) {
        // Convertir la String en LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateNaissance = LocalDate.parse(dateNaissanceStr, formatter);
        LocalDate today = LocalDate.now();
        Period period = Period.between(dateNaissance, today);
        log.debug("Date de naissance:"+dateNaissanceStr+" Age:"+period.getYears());
        return period.getYears();
    }

}
