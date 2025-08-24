package org.example.sql_tutorial.repository.impl;

import org.example.sql_tutorial.dto.CountPatientForEachAge;
import org.example.sql_tutorial.model.Patient;
import org.example.sql_tutorial.repository.PlainJdbcRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlainJdbcRepositoryImpl implements PlainJdbcRepository {

    String url = "jdbc:postgresql://localhost:5432/MyHospital";
    String username = "postgres";
    String password = "postgres";

    @Override
    public void addPatient(Patient patient) {
        String maxIdSql = "SELECT COALESCE(MAX(id), 0) + 1 FROM patient";
        String insertSql = "INSERT INTO patient (id, doctor_id, hospital_ward_id, name, diagnosis, age, date_of_receipt) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

            ResultSet rs = stmt.executeQuery(maxIdSql);
            Long nextId = 1L;
            if (rs.next()) {
                nextId = rs.getLong(1);
            }

            pstmt.setLong(1, nextId);
            pstmt.setLong(2, patient.getDoctorId());
            pstmt.setLong(3, patient.getHospitalWardId());
            pstmt.setString(4, patient.getName());
            pstmt.setString(5, patient.getDiagnosis());
            pstmt.setInt(6, patient.getAge());
            pstmt.setDate(7, new java.sql.Date(patient.getDateOfReceipt().getTime()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Creating patient failed, no rows affected.");
            }

            patient.setId(nextId);

        } catch (SQLException e) {
            throw new RuntimeException("SQL Exception on addPatient() method: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CountPatientForEachAge> getCountPatientForEachAge() {
        String sql = "SELECT age, COUNT(*) as count FROM patient WHERE age BETWEEN 1 AND 100 GROUP BY age";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            List<CountPatientForEachAge> result = new ArrayList<>();

            while (rs.next()) {
                int age = rs.getInt("age");
                int count = rs.getInt("count");
                result.add(new CountPatientForEachAge(age, count));
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get patient counts by age", e);
        }
    }

    @Override
    public void changeDoctor(Long patientId, Long doctorId) {
        String sql = "UPDATE patient SET doctor_id = " + doctorId + " WHERE id = " + patientId;

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Change doctor for patient failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL Exception on changeDoctor() method: " + e.getMessage(), e);
        }
    }
}
