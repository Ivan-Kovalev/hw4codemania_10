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

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class JooqRepositoryImplTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection conn = createTestConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS nurse");

            stmt.execute("CREATE TABLE nurse (" +
                    "id SERIAL PRIMARY KEY, " +
                    "doctor_id INTEGER NOT NULL, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "years_of_experience INTEGER NOT NULL, " +
                    "graduation_date DATE, " +
                    "employment_date DATE)");
        }
    }

    @Test
    void testAddNurse() throws SQLException {
        try (Connection conn = createTestConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO nurse " +
                    "(doctor_id, name, years_of_experience, graduation_date, employment_date) " +
                    "VALUES (858, 'Fabio Primmer', 4, '2010-05-02', '2002-11-12')");

            var rs = stmt.executeQuery("SELECT COUNT(*) FROM nurse");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    void testGetNurses() throws SQLException {
        try (Connection conn = createTestConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO nurse " +
                    "(doctor_id, name, years_of_experience, graduation_date, employment_date) " +
                    "VALUES (858, 'Fabio Primmer', 4, '2010-05-02', '2002-11-12')");

            var rs = stmt.executeQuery("SELECT COUNT(*) FROM nurse WHERE years_of_experience > 3");
            rs.next();
            assertEquals(1, rs.getInt(1));
        }
    }

    @Test
    void testUpdateNurse() throws SQLException {
        try (Connection conn = createTestConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO nurse " +
                    "(doctor_id, name, years_of_experience, graduation_date, employment_date) " +
                    "VALUES (858, 'Fabio Primmer', 4, '2010-05-02', '2002-11-12')");

            int updated = stmt.executeUpdate("UPDATE nurse SET doctor_id = 999 WHERE id = 1");
            assertEquals(1, updated);

            var rs = stmt.executeQuery("SELECT doctor_id FROM nurse WHERE id = 1");
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