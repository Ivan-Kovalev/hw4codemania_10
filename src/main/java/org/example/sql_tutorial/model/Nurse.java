package org.example.sql_tutorial.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "nurse")
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "name")
    private String name;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "graduation_date")
    private Date graduationDate;

    @Column(name = "employment_date")
    private Date employmentDate;

    public Nurse() {

    }

    public Nurse(Long doctorId, String name, Integer yearsOfExperience, Date graduationDate, Date employmentDate) {
        this.doctorId = doctorId;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.graduationDate = graduationDate;
        this.employmentDate = employmentDate;
    }

    public Long getId() {
        return id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nurse nurse = (Nurse) o;
        return Objects.equals(id, nurse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Nurse{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", name='" + name + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", graduationDate=" + graduationDate +
                ", employmentDate=" + employmentDate +
                '}';
    }
}
