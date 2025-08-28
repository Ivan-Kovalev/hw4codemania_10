package org.example.sql_tutorial.repository;

import org.example.sql_tutorial.model.Nurse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JooqRepository {
    void addNurse(Nurse nurse);

    List<Nurse> allNursesMoreThanYearsOfExperience(Integer years);

    boolean changeDoctor(Long nurseId, Long doctorId);
}
