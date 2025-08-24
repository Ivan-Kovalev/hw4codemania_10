package org.example.sql_tutorial.controller;

import org.example.sql_tutorial.dto.CountPatientForEachAge;
import org.example.sql_tutorial.model.Doctor;
import org.example.sql_tutorial.model.Patient;
import org.example.sql_tutorial.repository.JooqRepository;
import org.example.sql_tutorial.repository.PlainJdbcRepository;
import org.example.sql_tutorial.repository.SpringJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    private final PlainJdbcRepository plainJdbcRepository;
    private final JooqRepository jooqRepository;
    private final SpringJpaRepository springJpaRepository;

    public Controller(PlainJdbcRepository plainJdbcRepository, JooqRepository jooqRepository, SpringJpaRepository springJpaRepository) {
        this.plainJdbcRepository = plainJdbcRepository;
        this.jooqRepository = jooqRepository;
        this.springJpaRepository = springJpaRepository;
    }

    @GetMapping(path = "/plain/1")
    public List<CountPatientForEachAge> getCountPatientForEachAge() {
        return plainJdbcRepository.getCountPatientForEachAge();
    }

    @PostMapping(path = "/plain/2")
    public ResponseEntity<HttpStatus> addPatient(@RequestBody Patient patient) {
        plainJdbcRepository.addPatient(patient);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping(path = "/plain/3/{patientId}/{doctorId}")
    public ResponseEntity<HttpStatus> changeDoctor(@PathVariable Long patientId, @PathVariable Long doctorId) {
        plainJdbcRepository.changeDoctor(patientId, doctorId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(path = "/spring/1/{start}")
    public List<Doctor> getAllDoctorWhoNameStartWith(@PathVariable String start) {
        return springJpaRepository.getAllByNameStartsWith(start);
    }

    @PostMapping(path = "/spring/2")
    public ResponseEntity<HttpStatus> addDoctor(@RequestBody Doctor doctor) {
        springJpaRepository.save(doctor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping(path = "/spring/3/{docId}/{depId}")
    public ResponseEntity<HttpStatus> updateDepIdForDoctor(@PathVariable Long docId, @PathVariable Long depId) {
        springJpaRepository.updateByDepartmentId(docId, depId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
