package com.openclassrooms.p9.microservice_notes.repository;

import com.openclassrooms.p9.microservice_notes.model.NotePatient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotePatientRepository extends MongoRepository<NotePatient, String> {

    @Query(value="{'idPatient': ?0}")
    List<NotePatient> findByIdPatient(@Param("idPatient") Integer id);


}
