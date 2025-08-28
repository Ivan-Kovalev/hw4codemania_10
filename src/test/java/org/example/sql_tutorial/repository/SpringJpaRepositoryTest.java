package org.example.sql_tutorial.repository;

import jakarta.transaction.Transactional;
import org.example.sql_tutorial.model.Doctor;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringJpaRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private static Flyway flyway;

    @Autowired
    private SpringJpaRepository repository;

    @Autowired
    private DataSource dataSource;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeAll
    static void setUpFlyway() {
        flyway = Flyway.configure()
                .dataSource(
                        postgres.getJdbcUrl(),
                        postgres.getUsername(),
                        postgres.getPassword()
                )
                .locations("classpath:db/migration")
                .cleanDisabled(false)
                .load();
    }

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void testAddDoctor() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO doctor " +
                    "(department_id, name, years_of_experience, speciality, highest_category, graduation_date, employment_date) " +
                    "VALUES (1, 'John Smith', 10, 'Cardiology', true, '2010-05-02', '2011-06-15')");

            List<Doctor> doctors = repository.findAll();
            assertEquals(1, doctors.size());

            Doctor doctor = doctors.get(0);
            assertEquals("John Smith", doctor.getName());
            assertEquals(10, doctor.getYearsOfExperience());
            assertEquals("Cardiology", doctor.getSpeciality());
            assertTrue(doctor.isHighestCategory());
        }
    }

    @Test
    void testGetDoctorsByNameStartsWith() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO doctor " +
                    "(department_id, name, years_of_experience, speciality, highest_category, graduation_date, employment_date) " +
                    "VALUES (1, 'John Smith', 10, 'Cardiology', true, '2010-05-02', '2011-06-15')");

            stmt.execute("INSERT INTO doctor " +
                    "(department_id, name, years_of_experience, speciality, highest_category, graduation_date, employment_date) " +
                    "VALUES (2, 'John Doe', 5, 'Neurology', false, '2015-03-10', '2016-04-20')");

            stmt.execute("INSERT INTO doctor " +
                    "(department_id, name, years_of_experience, speciality, highest_category, graduation_date, employment_date) " +
                    "VALUES (3, 'Jane Smith', 8, 'Surgery', true, '2012-07-15', '2013-08-25')");

            List<Doctor> doctors = repository.getAllByNameStartsWith("John");

            assertEquals(2, doctors.size());
            assertTrue(doctors.stream().allMatch(d -> d.getName().startsWith("John")));

            Doctor johnSmith = doctors.stream()
                    .filter(d -> d.getName().equals("John Smith"))
                    .findFirst()
                    .orElseThrow();
            assertEquals("Cardiology", johnSmith.getSpeciality());
            assertEquals(10, johnSmith.getYearsOfExperience());
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    void testUpdateDoctorDepartment() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT INTO doctor " +
                    "(department_id, name, years_of_experience, speciality, highest_category, graduation_date, employment_date) " +
                    "VALUES (1, 'John Smith', 10, 'Cardiology', true, '2010-05-02', '2011-06-15')");

            repository.updateByDepartmentId(1L, 999L);

            Doctor updatedDoctor = repository.findById(1L).orElseThrow();
            assertEquals(999L, updatedDoctor.getDepartmentId());
        }
    }
}