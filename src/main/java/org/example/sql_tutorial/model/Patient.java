package org.example.sql_tutorial.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "hospital_ward_id")
    private Long hospitalWardId;

    @Column(name = "name")
    private String name;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "age")
    private Integer age;

    @Column(name = "date_of_receipt")
    private Date dateOfReceipt;

    public Patient() {

    }

    public Patient(Long doctorId,
                   Long hospitalWardId,
                   String name,
                   String diagnosis,
                   Integer age,
                   Date dateOfReceipt
    ) {
        this.doctorId = doctorId;
        this.hospitalWardId = hospitalWardId;
        this.name = name;
        this.diagnosis = diagnosis;
        this.age = age;
        this.dateOfReceipt = dateOfReceipt;
    }

    public Long getId() {
        return id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public Long getHospitalWardId() {
        return hospitalWardId;
    }

    public String getName() {
        return name;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Integer getAge() {
        return age;
    }

    public Date getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setHospitalWardId(Long hospitalWardId) {
        this.hospitalWardId = hospitalWardId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDateOfReceipt(Date dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", hospitalWardId=" + hospitalWardId +
                ", name='" + name + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", age=" + age +
                ", dateOfReceipt=" + dateOfReceipt +
                '}';
    }
}