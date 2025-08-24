package org.example.sql_tutorial.repository;

import org.example.sql_tutorial.dto.CountPatientForEachAge;
import org.example.sql_tutorial.model.Patient;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PlainJdbcRepository {
    void addPatient(Patient patient);

    List<CountPatientForEachAge> getCountPatientForEachAge();

    void changeDoctor(Long patientId, Long doctorId);
}
