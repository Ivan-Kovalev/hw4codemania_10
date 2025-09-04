package org.example.sql_tutorial.repository.impl;

import org.example.sql_tutorial.model.Nurse;
import org.example.sql_tutorial.repository.JooqRepository;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.example.sql_tutorial.generated.jooq.Tables.*;

@Repository
public class JooqRepositoryImpl implements JooqRepository {

    @Autowired
    DSLContext ctx;

    @Override
    public void addNurse(Nurse nurse) {
        ctx.insertInto(NURSE)
                .set(NURSE.DOCTOR_ID, nurse.getDoctorId())
                .set(NURSE.NAME, nurse.getName())
                .set(NURSE.YEARS_OF_EXPERIENCE, nurse.getYearsOfExperience())
                .set(NURSE.GRADUATION_DATE, nurse.getGraduationDate())
                .set(NURSE.EMPLOYMENT_DATE, nurse.getEmploymentDate())
                .execute();
    }

    @Override
    public List<Nurse> allNursesMoreThanYearsOfExperience(Long years) {
        return ctx.select()
                .from(NURSE)
                .where(NURSE.YEARS_OF_EXPERIENCE.gt(years))
                .fetchInto(Nurse.class);
    }

    @Override
    public boolean changeDoctor(Long nurseId, Long docId) {
        int affectedRows = ctx.update(NURSE)
                .set(NURSE.DOCTOR_ID, docId)
                .where(field("id").eq(nurseId))
                .execute();
        return affectedRows > 0;
    }
}
