package org.example.sql_tutorial.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class PlainJdbcRepositoryImplTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection conn = createTestConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS patient");

            stmt.execute("CREATE TABLE patient (" +
                    "id BIGINT PRIMARY KEY, " +
                    "doctor_id BIGINT NOT NULL, " +
                    "hospital_ward_id BIGINT NOT NULL, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "diagnosis VARCHAR(255) NOT NULL, " +
                    "age INTEGER NOT NULL, " +
                    "date_of_receipt DATE NOT NULL)");
        }
    }

    @Test
    void testAddPatient() throws SQLException {
        try (Connection conn = createTestConnection();
             Statement stmt = conn.createStatement()) {

            var rs = stmt.executeQuery("SELECT COALESCE(MAX(id), 0) + 1 FROM patient");
            rs.next();
            Long nextId = rs.getLong(1);

            stmt.execute("INSERT INTO patient (id, doctor_id, hospital_ward_id, name, diagnosis, age, date_of_receipt) " +
                    "VALUES (" + nextId + ", 1, 2, 'John Doe', 'Flu', 30, '2023-01-01')");

            var countRs = stmt.executeQuery("SELECT COUNT(*) FROM patient");
            countRs.next();
            assertEquals(1, countRs.getInt(1));
        }
    }

    @Test
    void testGetCountPatientForEachAge() throws SQLException {
        try (Connection conn = createTestConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO patient (id, doctor_id, hospital_ward_id, name, diagnosis, age, date_of_receipt) " +
                    "VALUES (1, 1, 1, 'Patient1', 'Diagnosis1', 25, '2023-01-01'), " +
                    "(2, 1, 1, 'Patient2', 'Diagnosis2', 25, '2023-01-01'), " +
                    "(3, 1, 1, 'Patient3', 'Diagnosis3', 30, '2023-01-01'), " +
                    "(4, 1, 1, 'Patient4', 'Diagnosis4', 30, '2023-01-01'), " +
                    "(5, 1, 1, 'Patient5', 'Diagnosis5', 30, '2023-01-01')");

            var rs = stmt.executeQuery("SELECT age, COUNT(*) as count FROM patient WHERE age BETWEEN 1 AND 100 GROUP BY age");

            Map<Integer, Integer> ageCounts = new HashMap<>();
            while (rs.next()) {
                ageCounts.put(rs.getInt("age"), rs.getInt("count"));
            }

            assertEquals(2, ageCounts.size());
            assertEquals(2, ageCounts.get(25));
            assertEquals(3, ageCounts.get(30));
        }
    }

    @Test
    void testChangeDoctor() throws SQLException {
        try (Connection conn = createTestConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO patient (id, doctor_id, hospital_ward_id, name, diagnosis, age, date_of_receipt) " +
                    "VALUES (1, 1, 1, 'Test Patient', 'Test Diagnosis', 25, '2023-01-01')");

            int updated = stmt.executeUpdate("UPDATE patient SET doctor_id = 999 WHERE id = 1");
            assertEquals(1, updated);

            var rs = stmt.executeQuery("SELECT doctor_id FROM patient WHERE id = 1");
            rs.next();
            assertEquals(999, rs.getInt("doctor_id"));
        }
    }

    private Connection createTestConnection() throws SQLException {
        return DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
    }
}