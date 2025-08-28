package org.example.sql_tutorial.repository.impl;

import org.example.sql_tutorial.model.Nurse;
import org.example.sql_tutorial.repository.JooqRepository;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Repository
public class JooqRepositoryImpl implements JooqRepository {

    String url = "jdbc:postgresql://localhost:5432/MyHospital";
    String username = "postgres";
    String password = "postgres";

    Connection conn = DriverManager.getConnection(url, username, password);

    DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);

    Table<Record> nurses = table("nurse");
    Field<Long> doctorId = field("doctor_id", Long.class);
    Field<String> name = field("name", String.class);
    Field<Integer> yearsOfExperience = field("years_of_experience", Integer.class);
    Field<Date> graduationDate = field("graduation_date", Date.class);
    Field<Date> employmentDate = field("employment_date", Date.class);

    public JooqRepositoryImpl() throws SQLException {
    }

    @Override
    public void addNurse(Nurse nurse) {
        ctx.insertInto(nurses)
                .columns(
                        doctorId,
                        name,
                        yearsOfExperience,
                        graduationDate,
                        employmentDate)
                .values(
                        nurse.getDoctorId(),
                        nurse.getName(),
                        nurse.getYearsOfExperience(),
                        nurse.getGraduationDate(),
                        nurse.getEmploymentDate())
                .execute();
    }

    @Override
    public List<Nurse> allNursesMoreThanYearsOfExperience(Integer years) {
        return ctx.select()
                .from(nurses)
                .where(yearsOfExperience.gt(years))
                .fetchInto(Nurse.class);
    }

    @Override
    public boolean changeDoctor(Long nurseId, Long docId) {
        int affectedRows = ctx.update(nurses)
                .set(doctorId, docId)
                .where(field("id").eq(nurseId))
                .execute();
        return affectedRows > 0;
    }
}
